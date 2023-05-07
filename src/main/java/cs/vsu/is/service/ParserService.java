package cs.vsu.is.service;

import cs.vsu.is.domain.*;
import cs.vsu.is.repository.*;
import cs.vsu.is.service.utils.WeekDays;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ParserService {
    private static final String[] timesArray = {"8:00 - 9:30",
        "9:45 - 11:20",
        "11:30 - 13:05",
        "13:25 - 15:00",
        "15:10 - 16:45",
        "16:55 - 18:30",
        "18:45 - 20:00"};

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

    private final ScheduleRepository scheduleRepository;

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

        for (int i = 0; i < WeekDays.values().length; i++) {
            if (WeekDays.values()[i].toString().equals(weekdayName.strip())) {
                emptySlot.setDayOfWeak(i);
            } else emptySlot.setDayOfWeak(-1);
        }

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

    private static void setBordersOnCell(Integer i, Integer j, Sheet sheet) {
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, new CellRangeAddress(i, i, j, j), sheet);
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM, new CellRangeAddress(i, i, j, j), sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, new CellRangeAddress(i, i, j, j), sheet);
        RegionUtil.setBorderTop(BorderStyle.MEDIUM, new CellRangeAddress(i, i, j, j), sheet);
    }

    private static Workbook createHFFSSchemaForTeacher(List<Lesson> lessons) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        String safeSheetName = WorkbookUtil.createSafeSheetName("");
        Sheet sheet = workbook.createSheet(safeSheetName);

        sheet.createRow(0).setHeight((short) 500);
        sheet.setColumnWidth(0, 5000);

        CreationHelper helper = workbook.getCreationHelper();

        for (int i = 1; i < 7; i++) {
            sheet.getRow(0).createCell(i).setCellValue(helper.createRichTextString(String.valueOf(WeekDays.values()[i])));
            setBordersOnCell(0, i, sheet);
        }

        for (int i = 1; i < 15; i++) {
            Row row = sheet.createRow(i);
            sheet.setColumnWidth(i, 5000);

            row.createCell(0);
            for (int j = 1; j < 7; j++) {
                setBordersOnCell(i, j, sheet);
                //todo: there goes lessons
                if (i % 2 == 0) {
                    sheet.addMergedRegion(new CellRangeAddress(
                        i - 1,
                        i,
                        j,
                        j
                    ));
                }
            }
        }

        int counter = 0;
        for (int i = 1; i < 15; i++) {
            setBordersOnCell(i, 0, sheet);
            if (i % 2 == 0) {
                sheet.addMergedRegion(new CellRangeAddress(
                    i - 1, //first row (0-based)
                    i, //last row  (0-based)
                    0, //first column (0-based)
                    0  //last column  (0-based)
                ));
            } else {
                if (counter < timesArray.length) {
                    sheet.getRow(i).getCell(0).setCellValue(helper.createRichTextString(timesArray[counter]));
                    ++counter;
                }
            }
        }
        return workbook;
    }

    public static String convertHSSFToHtmlSchema(HSSFWorkbook excelDoc) throws ParserConfigurationException, TransformerException, IOException {
        ExcelToHtmlConverter converter = new ExcelToHtmlConverter(
            DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument()
        );

        converter.processWorkbook(excelDoc);

        org.w3c.dom.Document htmlDoc = converter.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDoc);
        StreamResult streamResult = new StreamResult(out);
        TransformerFactory transfFactory = TransformerFactory.newInstance();
        Transformer serializer = transfFactory.newTransformer();

        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);

        out.close();

        return out.toString();
    }

    public Workbook filterTimetableByTeacher(String teacherName, Integer timetableIndex) {
        //todo: algorithm of sorting timetable
        // 1. search for teacher name + (don't forget to specify the timetable id)
        // 2. search for all lessons with id of previously found teacher +
        // 3. sort timetables by weekdays and by time +
        // 4. form a new Workbook + and fill it with content
        // 5. solve problem with parsing XSSFWorkbook to html +

        Employee employee = employeeRepository.findByUserLastName(teacherName); //todo: use an initials too and timetable id
        List<Lesson> lessons = lessonRepository.findAllByEmployeeId(employee.getId());

        lessons = lessons.stream()
            .filter(lesson -> lesson.getSchedule().getIsActual())
            .sorted(Comparator.comparing(lesson -> lesson.getEduSchedulePlace().getDayOfWeak()))
            .sorted(Comparator.comparing(lesson -> lesson.getEduSchedulePlace().getStartTime()))
            .collect(Collectors.toList());

        Workbook workbook = createHFFSSchemaForTeacher(lessons);

        UUID uuid = UUID.randomUUID();
        Path path = Path.of("files/" + uuid + teacherName + timetableIndex);
        logger.debug("path {}", path);

        try (OutputStream file = new FileOutputStream(path.toString())) {
            workbook.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public void parseTimetable(String path) throws IOException {
        Workbook workbook = new XSSFWorkbook("files" + path);

        this.parseXLSXToSlots(workbook, 0);
        this.parseXLSXToSlots(workbook, 1);

        Schedule previous = scheduleRepository.findByIsActual(true);
        previous.setIsActual(false);

        //todo: убрать костыль
        scheduleRepository.deleteById(previous.getId());
        scheduleRepository.save(previous);

        scheduleRepository.save(new Schedule(path, Instant.now(), true));
    }
}
