package cs.vsu.is.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link cs.vsu.is.domain.EduSchedulePlace} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EduSchedulePlaceDTO implements Serializable {

    private Long id;

    private Boolean isDenominator;

    private Instant startTime;

    private Instant endTime;

    private Integer dayOfWeak;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsDenominator() {
        return isDenominator;
    }

    public void setIsDenominator(Boolean isDenominator) {
        this.isDenominator = isDenominator;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getDayOfWeak() {
        return dayOfWeak;
    }

    public void setDayOfWeak(Integer dayOfWeak) {
        this.dayOfWeak = dayOfWeak;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EduSchedulePlaceDTO)) {
            return false;
        }

        EduSchedulePlaceDTO eduSchedulePlaceDTO = (EduSchedulePlaceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eduSchedulePlaceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EduSchedulePlaceDTO{" +
            "id=" + getId() +
            ", isDenominator='" + getIsDenominator() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", dayOfWeak=" + getDayOfWeak() +
            "}";
    }
}
