package cs.vsu.is.service;

import cs.vsu.is.domain.EduSchedulePlace;
import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Lesson;
import cs.vsu.is.repository.EduSchedulePlaceRepository;
import cs.vsu.is.repository.EmployeeRepository;
import cs.vsu.is.repository.LessonRepository;
import cs.vsu.is.repository.ScheduleRepository;
import cs.vsu.is.service.dto.fullschedule.EmployeeScheduleDTO;
import cs.vsu.is.service.dto.fullschedule.FullLessonDTO;
import cs.vsu.is.service.dto.fullschedule.FullScheduleDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FullScheduleService {
    private final EmployeeRepository employeeRepository;
    private final LessonRepository lessonRepository;
    private final EduSchedulePlaceRepository eduSchedulePlaceRepository;

    private final HashMap<String, String> timeMap = new HashMap<String, String>() {{
        put("8:00", "8:00 - 9:30");
        put("9:45", "9:45 - 11:20");
        put("11:30", "11:30 - 13:05");
        put("13:25", "13:25 - 15:00");
        put("15:10", "15:10 - 16:45");
        put("16:55", "16:55 - 18:30");
        put("18:40", "18:40 - 20:00");
        put("20:10", "20:10 - 21:30");
    }};
    private final ScheduleRepository scheduleRepository;

    public FullScheduleService(EmployeeRepository employeeRepository, LessonRepository lessonRepository, EduSchedulePlaceRepository eduSchedulePlaceRepository,
                               ScheduleRepository scheduleRepository) {
        this.employeeRepository = employeeRepository;
        this.lessonRepository = lessonRepository;
        this.eduSchedulePlaceRepository = eduSchedulePlaceRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public List<EmployeeScheduleDTO> getFullSchedule() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeScheduleDTO> employeeScheduleDTOS = new LinkedList<>();
        for (Employee employee : employees) {
            Set<Lesson> employeeLessons = employee.getLessons();
            List<String> times = Arrays.asList("8:00", "9:45", "11:30", "13:25", "15:10", "16:55", "18:40", "20:10");
            List<FullScheduleDTO> fullScheduleDTOS = new LinkedList<>();
            for (String time : times) {
                List<FullLessonDTO> monday =
                    Arrays.asList(
                        getLessonForDayOfWeekAndTime(employeeLessons, time, 0, employee, true),
                        getLessonForDayOfWeekAndTime(employeeLessons, time, 0, employee, false)
                    );
                List<FullLessonDTO> tuesday =
                    Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 1, employee, true),
                        getLessonForDayOfWeekAndTime(employeeLessons, time, 1, employee, false)
                        );
                List<FullLessonDTO> wednesday =
                    Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 2, employee, true),
                        getLessonForDayOfWeekAndTime(employeeLessons, time, 2, employee, false)
                        );
                List<FullLessonDTO> thursday =
                    Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 3, employee, true),
                        getLessonForDayOfWeekAndTime(employeeLessons, time, 3, employee, false)
                        );
                List<FullLessonDTO> friday =
                    Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 4, employee, true),
                        getLessonForDayOfWeekAndTime(employeeLessons, time, 4, employee, false)
                        );
                List<FullLessonDTO> saturday =
                    Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 5, employee, true),
                        getLessonForDayOfWeekAndTime(employeeLessons, time, 5, employee, false)
                        );
                FullScheduleDTO fullScheduleDTO = new FullScheduleDTO();
                fullScheduleDTO.setTime(timeMap.get(time));
                fullScheduleDTO.setMonday(monday);
                fullScheduleDTO.setTuesday(tuesday);
                fullScheduleDTO.setWednesday(wednesday);
                fullScheduleDTO.setThursday(thursday);
                fullScheduleDTO.setFriday(friday);
                fullScheduleDTO.setSaturday(saturday);
                fullScheduleDTOS.add(fullScheduleDTO);
            }
            EmployeeScheduleDTO employeeScheduleDTO = new EmployeeScheduleDTO();
            employeeScheduleDTO.setSchedule(fullScheduleDTOS);
            employeeScheduleDTO.setId(employee.getUser().getId());
            employeeScheduleDTO.setShortName(employee.getUser().getLastName()
                + " " +
                Character.toUpperCase(employee.getUser().getFirstName().charAt(0))
                + ". " +
                Character.toUpperCase(employee.getPatronymic().charAt(0))
                + "."
            );
            employeeScheduleDTOS.add(employeeScheduleDTO);
        }
        return employeeScheduleDTOS;
    }

    public EmployeeScheduleDTO getFullScheduleForEmployee(long employeeId) {
        Optional<Employee> byId = employeeRepository.findById(employeeId);
        if (byId.isEmpty()) {
            return null;
        }
        Employee employee = byId.get();
        Set<Lesson> employeeLessons = employee.getLessons();
        List<String> times = Arrays.asList("8:00", "9:45", "11:30", "13:25", "15:10", "16:55", "18:40", "20:10");
        List<FullScheduleDTO> fullScheduleDTOS = new LinkedList<>();
        for (String time : times) {
            List<FullLessonDTO> monday =
                Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 0, employee, true),
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 0, employee, false)
                );
            List<FullLessonDTO> tuesday =
                Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 1, employee, true),
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 1, employee, false)
                );
            List<FullLessonDTO> wednesday =
                Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 2, employee, true),
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 2, employee, false)
                    );
            List<FullLessonDTO> thursday =
                Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 3, employee, true),
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 3, employee, false)
                    );
            List<FullLessonDTO> friday =
                Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 4, employee, true),
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 4, employee, false)
                    );
            List<FullLessonDTO> saturday =
                Arrays.asList(
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 5, employee, true),
                    getLessonForDayOfWeekAndTime(employeeLessons, time, 5, employee, false)
                    );
            FullScheduleDTO fullScheduleDTO = new FullScheduleDTO();
            fullScheduleDTO.setTime(timeMap.get(time));
            fullScheduleDTO.setMonday(monday);
            fullScheduleDTO.setTuesday(tuesday);
            fullScheduleDTO.setWednesday(wednesday);
            fullScheduleDTO.setThursday(thursday);
            fullScheduleDTO.setFriday(friday);
            fullScheduleDTO.setSaturday(saturday);
            fullScheduleDTOS.add(fullScheduleDTO);
        }
        EmployeeScheduleDTO employeeScheduleDTO = new EmployeeScheduleDTO();
        employeeScheduleDTO.setSchedule(fullScheduleDTOS);
        employeeScheduleDTO.setId(employee.getUser().getId());
        employeeScheduleDTO.setShortName(employee.getUser().getLastName()
            + " " +
            Character.toUpperCase(employee.getUser().getFirstName().charAt(0))
            + ". " +
            Character.toUpperCase(employee.getPatronymic().charAt(0))
            + "."
        );
        return employeeScheduleDTO;
    }

    private FullLessonDTO getLessonForDayOfWeekAndTime(Set<Lesson> lessons, String startTime, int weekNumber, Employee employee, boolean isDenominator) {
        List<Lesson> collect = new ArrayList<>();
        if (lessons != null) {
             collect = lessons
                .stream()
                .filter(lesson -> (
                    lesson.getEduSchedulePlace().getDayOfWeak() == weekNumber
                        && lesson.getEduSchedulePlace().getStartTime().equals(startTime)
                        && lesson.getEduSchedulePlace().getIsDenominator() != null
                        && lesson.getEduSchedulePlace().getIsDenominator() == isDenominator
                ))
                .collect(Collectors.toList());
        }
        if (collect.isEmpty()) {
            EduSchedulePlace eduSchedulePlace = new EduSchedulePlace();
            eduSchedulePlace.setDayOfWeak(weekNumber);
            eduSchedulePlace.setIsDenominator(isDenominator);
            eduSchedulePlace.setStartTime(startTime);
            eduSchedulePlace = eduSchedulePlaceRepository.save(eduSchedulePlace);
            Lesson lesson = new Lesson();
            lesson.setEduSchedulePlace(eduSchedulePlace);
            lesson.setEmployee(employee);
            lesson = lessonRepository.save(lesson);
            FullLessonDTO fullLessonDTO = new FullLessonDTO();
            fullLessonDTO.setLessonId(lesson.getId());
            fullLessonDTO.setIsDenominator(isDenominator);
            return fullLessonDTO;
        }
        FullLessonDTO fullLessonDTO = new FullLessonDTO();
        fullLessonDTO.setPlacement(collect.get(0).getClassroom());
        fullLessonDTO.setCourse(collect.get(0).getCourse());
        if (collect.get(0).getGroup() != null) {
            fullLessonDTO.setGroup(collect.get(0).getGroup().toString());
        } else {
            fullLessonDTO.setGroup(null);
        }
        fullLessonDTO.setLessonId(collect.get(0).getId());
        fullLessonDTO.setLesson(collect.get(0).getSubjectName());
        fullLessonDTO.setIsDenominator(collect.get(0).getEduSchedulePlace().getIsDenominator());
        if (collect.size() == 1) {
            return fullLessonDTO;
        }
        StringBuilder group = new StringBuilder();
        for (Lesson lesson : collect) {
            group.append(lesson.getGroup()).append(", ");
        }
        fullLessonDTO.setGroup(group.toString());
        for(int i =1;i<collect.size();i++) {
            scheduleRepository.deleteById(collect.get(i).getSchedule().getId());
        }
        return fullLessonDTO;
    }

}
