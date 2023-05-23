package cs.vsu.is.service;

import cs.vsu.is.domain.Employee;
import cs.vsu.is.domain.Lesson;
import cs.vsu.is.repository.EmployeeRepository;
import cs.vsu.is.service.dto.fullschedule.EmployeeScheduleDTO;
import cs.vsu.is.service.dto.fullschedule.FullLessonDTO;
import cs.vsu.is.service.dto.fullschedule.FullScheduleDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FullScheduleService {
    private final EmployeeRepository employeeRepository;

    public FullScheduleService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeScheduleDTO> getFullSchedule() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeScheduleDTO> employeeScheduleDTOS = new LinkedList<>();
        for(Employee employee: employees) {
            Set<Lesson> employeeLessons = employee.getLessons();
            List<String> times = Arrays.asList("8:00", "9:45", "11:30", "13:25", "15:10", "16:55", "18:40", "20:10");
            List<FullScheduleDTO> fullScheduleDTOS = new LinkedList<>();
            for (String time : times) {
                FullLessonDTO monday = getLessonForDayOfWeekAndTime(employeeLessons, time, 0);
                FullLessonDTO tuesday = getLessonForDayOfWeekAndTime(employeeLessons, time, 1);
                FullLessonDTO wednesday = getLessonForDayOfWeekAndTime(employeeLessons, time, 2);
                FullLessonDTO thursday = getLessonForDayOfWeekAndTime(employeeLessons, time, 3);
                FullLessonDTO friday = getLessonForDayOfWeekAndTime(employeeLessons, time, 4);
                FullLessonDTO saturday = getLessonForDayOfWeekAndTime(employeeLessons, time, 5);
                FullScheduleDTO fullScheduleDTO = new FullScheduleDTO();
                fullScheduleDTO.setTime(time);
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
                +". "+
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
        List<EmployeeScheduleDTO> employeeScheduleDTOS = new LinkedList<>();
        Set<Lesson> employeeLessons = employee.getLessons();
        List<String> times = Arrays.asList("8:00", "9:45", "11:30", "13:25", "15:10", "16:55", "18:40", "20:10");
        List<FullScheduleDTO> fullScheduleDTOS = new LinkedList<>();
        for (String time : times) {
            FullLessonDTO monday = getLessonForDayOfWeekAndTime(employeeLessons, time, 0);
            FullLessonDTO tuesday = getLessonForDayOfWeekAndTime(employeeLessons, time, 1);
            FullLessonDTO wednesday = getLessonForDayOfWeekAndTime(employeeLessons, time, 2);
            FullLessonDTO thursday = getLessonForDayOfWeekAndTime(employeeLessons, time, 3);
            FullLessonDTO friday = getLessonForDayOfWeekAndTime(employeeLessons, time, 4);
            FullLessonDTO saturday = getLessonForDayOfWeekAndTime(employeeLessons, time, 5);
            FullScheduleDTO fullScheduleDTO = new FullScheduleDTO();
            fullScheduleDTO.setTime(time);
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

    private FullLessonDTO getLessonForDayOfWeekAndTime(Set<Lesson> lessons, String startTime, int weekNumber) {
        if (lessons == null || lessons.isEmpty()) {
            return new FullLessonDTO();
        }
        List<Lesson> collect = lessons
            .stream()
            .filter(lesson -> (lesson.getEduSchedulePlace().getDayOfWeak() == weekNumber && lesson.getEduSchedulePlace().getStartTime().equals(startTime)))
            .collect(Collectors.toList());
        if(collect.isEmpty()) {
            return new FullLessonDTO();
        }
        FullLessonDTO fullLessonDTO = new FullLessonDTO();
        fullLessonDTO.setPlacement(collect.get(0).getClassroom());
        fullLessonDTO.setCourse(collect.get(0).getCourse());
        fullLessonDTO.setGroup(collect.get(0).getGroup().toString());
        fullLessonDTO.setLessonId(collect.get(0).getId());
        fullLessonDTO.setLesson(collect.get(0).getSubjectName());
        fullLessonDTO.setIsDenominator(collect.get(0).getEduSchedulePlace().getIsDenominator());
        if(collect.size() == 1) {
            return fullLessonDTO;
        }
        StringBuilder group = new StringBuilder();
        for(Lesson lesson:collect) {
            group.append(lesson.getGroup()).append(", ");
        }
        fullLessonDTO.setGroup(group.toString());
        return fullLessonDTO;
    }

}
