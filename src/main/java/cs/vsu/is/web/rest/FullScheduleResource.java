package cs.vsu.is.web.rest;

import cs.vsu.is.service.FullScheduleService;
import cs.vsu.is.service.dto.LessonDTO;
import cs.vsu.is.service.dto.fullschedule.EmployeeScheduleDTO;
import cs.vsu.is.service.dto.fullschedule.FullLessonDTO;
import cs.vsu.is.service.dto.fullschedule.FullScheduleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
public class FullScheduleResource {
    private final Logger log = LoggerFactory.getLogger(FullScheduleResource.class);

    private final FullScheduleService fullScheduleService;

    public FullScheduleResource(FullScheduleService fullScheduleService) {
        this.fullScheduleService = fullScheduleService;
    }

    @GetMapping("/full-schedule/{employeeId}")
    public ResponseEntity<EmployeeScheduleDTO> getFullScheduleForEmployee(@PathVariable Long employeeId) {
        log.debug("REST request to get Full Schedule For Employee");
        return ResponseEntity.ok(fullScheduleService.getFullScheduleForEmployee(employeeId));
    }

    @GetMapping("/full-schedule")
    public ResponseEntity<List<EmployeeScheduleDTO>> getFullSchedules() {
        log.debug("REST request to get Full Schedules");
        return ResponseEntity.ok(fullScheduleService.getFullSchedule());
    }
}
