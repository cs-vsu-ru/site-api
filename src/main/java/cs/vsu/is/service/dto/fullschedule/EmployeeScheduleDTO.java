package cs.vsu.is.service.dto.fullschedule;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EmployeeScheduleDTO {
    private Long id;
    private String shortName;
    private List<FullScheduleDTO> schedule;
}
