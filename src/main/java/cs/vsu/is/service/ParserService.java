package cs.vsu.is.service;

import cs.vsu.is.domain.EduSchedulePlace;
import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Lesson;
import cs.vsu.is.domain.Subject;
import cs.vsu.is.repository.*;
import cs.vsu.is.service.convertor.TeachingConverter;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.Workbook;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ParserService {
    private final Logger logger = LoggerFactory.getLogger(ParserService.class);

    private final EmployeeRepository employeeRepository;
    private final EduSchedulePlaceRepository emptySlotRepository;
    private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;


    private Map<String, List<Integer[]>> coursesIndexRange;
    private Map<String, List<Integer[]>> groupsIndexRange;
    private Map<String, List<Integer[]>> weekdaysIndexRange;
    private Map<String, List<Integer[]>> timesIndexRange;
    private List<List<CellRangeAddress>> mergedRegions;

    private static Map<String, List<Integer[]>> countRowIndexRange(Row row) {
        Map<String, List<Integer[]>> indexMap = new HashMap<>();

        String prevValue = row.getCell(0).getStringCellValue();
        int prevIndex = 0;

        for (Cell cell : row) {
            String currentValue = cell.getStringCellValue();
            List<Integer[]> buff;

            if (!prevValue.equals(currentValue) && !currentValue.equals("")) {
                if (indexMap.containsKey(prevValue)) {
                    buff = indexMap.get(prevValue);
                    buff.add(new Integer[]{prevIndex, cell.getColumnIndex() - 1});
                    indexMap.put(prevValue, buff);
                } else {
                    buff = new ArrayList<>();
                }

                buff.add(new Integer[]{prevIndex, cell.getColumnIndex() - 1});
                indexMap.put(prevValue, buff)
                ;
                prevValue = currentValue;
                prevIndex = cell.getColumnIndex();
            }
        }

        return indexMap;
    }

    private static List<List<CellRangeAddress>> processMergedRegions(Sheet sheet) {
        var sortedByRow = sheet.getMergedRegions().stream()
            .sorted(Comparator.comparing(CellRangeAddress::getFirstRow))
            .collect(Collectors.toList());

        List<List<CellRangeAddress>> sortedByCell = new LinkedList<>();

        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            int finalI = i;
            var line = sortedByRow.stream()
                .filter(el -> el.getFirstRow() == finalI)
                .sorted(Comparator.comparing(CellRangeAddress::getFirstColumn))
                .collect(Collectors.toList());
            sortedByCell.add(line);
        }

        sortedByCell.removeAll(sortedByCell.stream()
            .filter(List::isEmpty)
            .collect(Collectors.toList()));

        return sortedByCell;
    }

    private static Map<String, List<Integer[]>> countCellIndexRange(Sheet sheet, Integer column, Integer offset) {
        Map<String, List<Integer[]>> indexMap = new HashMap<>();

        String prevValue = sheet.getRow(offset).getCell(column).getStringCellValue();
        int prevIndex = offset;

        for (int i = offset + 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            String currentValue = sheet.getRow(i).getCell(column).getStringCellValue();

            if (!prevValue.equals(currentValue) && !currentValue.equals("")) {
                if (indexMap.containsKey(prevValue)) {
                    List<Integer[]> buff = indexMap.get(prevValue);
                    buff.add(new Integer[]{prevIndex, i - 1});
                    indexMap.put(prevValue, buff);
                } else {
                    List<Integer[]> buff = new ArrayList<>();
                    buff.add(new Integer[]{prevIndex, i - 1});
                    indexMap.put(prevValue, buff);
                }
                prevValue = currentValue;
                prevIndex = i;
            }
        }

        return indexMap;
    }

    private static CellRangeAddress includedInMergedRegion(Cell slot, List<List<CellRangeAddress>> regions) {
        int cell = slot.getColumnIndex();
        int row = slot.getRowIndex();

        for (var rowLine : regions) {
            if (rowLine.get(0).getFirstRow() <= row &&
                rowLine.get(0).getLastRow() >= row) {
                for (var region : rowLine) {
                    if (region.getFirstColumn() <= cell &&
                        region.getLastColumn() >= cell) {
                        return region;
                    }
                }
            }
        }
        return null;
    }

    private static Integer findCourse(Map<String, List<Integer[]>> coursesIndexRange, Integer columnIndex) {
        int course = -1;
        for (var entry : coursesIndexRange.entrySet()) {
            for (var pair : entry.getValue()) {
                if (columnIndex <= pair[1] &&
                    columnIndex >= pair[0]) {
                    course = Integer.parseInt(entry.getKey().split(" ")[0]);
                    return course;
                }
            }
        }
        return course;
    }

    private static Integer[] findGroupAndSubgroup(Map<String, List<Integer[]>> groupsIndexRange, Integer columnIndex) {
        for (var entry : groupsIndexRange.entrySet()) {
            for (var pair : entry.getValue()) {
                if (columnIndex <= pair[1] &&
                    columnIndex >= pair[0]) {
                    if (entry.getKey().length() >= 6) {
                        int group = Integer.parseInt(entry.getKey().split(" ")[0]);
                        int subgroup = columnIndex % 2 + 1;
                        return new Integer[]{group, subgroup};
                    } else {
                        return new Integer[]{0, 0};
                    }
                }
            }
        }
        return new Integer[]{-1, -1};
    }

    private static String[] findTimes(Map<String, List<Integer[]>> timesIndexRange, Integer rowIndex) {
        for (var timesList : timesIndexRange.entrySet()) {
            for (var pair : timesList.getValue()) {
                if (rowIndex <= pair[1] && rowIndex >= pair[0]) {
                    String startTime = timesList.getKey().split("-")[0].trim();
                    String endTime = timesList.getKey().split("-")[1].trim();
                    return new String[]{startTime, endTime};
                }
            }
        }
        return new String[]{"", ""};
    }

    private static String findWeekdayNum(Map<String, List<Integer[]>> weekdaysIndexRange, Integer rowIndex) {
        for (var weekdaysList : weekdaysIndexRange.entrySet()) {
            for (var pair : weekdaysList.getValue()) {
                if (rowIndex <= pair[1] && rowIndex >= pair[0]) {
                    return weekdaysList.getKey();
                }
            }
        }
        return "";
    }

    private Lesson parseCompletedSlot(Cell currentCell,
                                      Map<String, List<Integer[]>> timesIndexRange,
                                      Map<String, List<Integer[]>> weekdaysIndexRange,
                                      Map<String, List<Integer[]>> coursesIndexRange,
                                      Map<String, List<Integer[]>> groupsIndexRange) {

        boolean denominator = currentCell.getRowIndex() % 2 != 0;

        int rowIndex = currentCell.getRowIndex();
        int columnIndex = currentCell.getColumnIndex();

        String[] startEndTimes = findTimes(timesIndexRange, rowIndex);
        String weekdayName = findWeekdayNum(weekdaysIndexRange, rowIndex);

        EduSchedulePlace emptySlot = new EduSchedulePlace();
        emptySlot.setStartTime(startEndTimes[0]);
        emptySlot.setEndTime(startEndTimes[1]);
        //todo: maybe enum to convert names to numbers
        emptySlot.setDayOfWeak(-1);
        emptySlot.setIsDenominator(denominator);

        emptySlot = emptySlotRepository.save(emptySlot);

        Integer course = findCourse(coursesIndexRange, columnIndex);
        Integer[] groupAndSubgroup = findGroupAndSubgroup(groupsIndexRange, columnIndex);


        Lesson completedSlot = new Lesson();
        completedSlot.setCourse(course);
        completedSlot.setGroup(groupAndSubgroup[0]);
        completedSlot.setSubgroup(groupAndSubgroup[1]);
        completedSlot.setEduSchedulePlace(emptySlot);
//      todo: completedSlot.setSchedule();

        if (!currentCell.getStringCellValue().equals("")) {
            String[] params = currentCell.getStringCellValue().split(" ");

            String classNum = params[params.length - 1];
            //todo: don't forget about classname

            String teacherName;
            if (params.length > 2) {
                //todo: make query for initials and surname
                Employee employee = employeeRepository.findByUserLastName(params[params.length - 3]);
                completedSlot.setEmployee(employee);

                StringBuilder subjectName = new StringBuilder();
                for (int k = 0; k < params.length - 4; k++) {
                    subjectName.append(params[k]).append(" ");
                }
                Subject subject = subjectRepository.findByName(String.valueOf(subjectName));
                completedSlot.setSubject(subject);
            }
        }

        completedSlot = lessonRepository.save(completedSlot);

        return completedSlot;
    }


    public ResponseEntity<String> parseXLSXToSlots(Workbook workbook, Integer sheetNumber) {
        logger.info("Request to parse XLSX document: {}, sheet: {}", workbook.getSheetName(sheetNumber), sheetNumber);

        Sheet currentSheet = workbook.getSheetAt(sheetNumber);
        List<Lesson> result = new LinkedList<>();

        try {
            this.coursesIndexRange = countRowIndexRange(currentSheet.getRow(0));
            this.groupsIndexRange = countRowIndexRange(currentSheet.getRow(1));
            this.weekdaysIndexRange = countCellIndexRange(currentSheet, 0, 4);
            this.timesIndexRange = countCellIndexRange(currentSheet, 1, 4);

            this.mergedRegions = processMergedRegions(currentSheet);
        } catch (Exception e) {
            e.printStackTrace();
//            return ResponseEntity.badRequest().body("Parsing failed on the stage of parsing courses, groups, weekdays and times cell ranges");
        }

        for (int i = 4; i < currentSheet.getPhysicalNumberOfRows(); i++) {
            try {
                int currRowLen = currentSheet.getRow(i).getLastCellNum();

                for (int j = 2; j < currRowLen; j++) {
                    Cell currentCell = currentSheet.getRow(i).getCell(j);
                    CellRangeAddress addressInvolved;
                    try {
                        addressInvolved = includedInMergedRegion(currentCell, mergedRegions);
                    } catch (Exception e) {
                        e.printStackTrace();
                        addressInvolved = null;
                    }

                    if (addressInvolved != null) {
                        if (currentCell.getColumnIndex() == addressInvolved.getFirstColumn() &&
                            currentCell.getRowIndex() == addressInvolved.getFirstRow()) {
                            for (int cellIndex = addressInvolved.getFirstColumn(); cellIndex <= addressInvolved.getLastColumn(); cellIndex++) {
                                for (int rowInde = addressInvolved.getFirstRow(); rowInde <= addressInvolved.getLastRow(); rowInde++) {
                                    Lesson duplicatedSlot = parseCompletedSlot(currentSheet.getRow(rowInde).getCell(cellIndex),
                                        timesIndexRange,
                                        weekdaysIndexRange,
                                        coursesIndexRange,
                                        groupsIndexRange);
                                    result.add(duplicatedSlot);
                                }
                            }
                        }
                    } else {
                        try {
                            if (!currentCell.getStringCellValue().equals("")) {
                                Lesson slot = this.parseCompletedSlot(currentCell, timesIndexRange, weekdaysIndexRange, coursesIndexRange, groupsIndexRange);
                                result.add(slot);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
//                return ResponseEntity.badRequest().body("Parsing failed on the stage of parsing nullable cell");
            }
        }
        return ResponseEntity.ok().body("Parsing succeeded");
    }
}
