package cs.vsu.is.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A EduSchedulePlace.
 */
@Entity
@Table(name = "edu_schedule_place")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EduSchedulePlace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_denominator")
    private Boolean isDenominator;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "day_of_weak")
    private Integer dayOfWeak;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EduSchedulePlace id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsDenominator() {
        return this.isDenominator;
    }

    public EduSchedulePlace isDenominator(Boolean isDenominator) {
        this.setIsDenominator(isDenominator);
        return this;
    }

    public void setIsDenominator(Boolean isDenominator) {
        this.isDenominator = isDenominator;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public EduSchedulePlace startTime(String startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public EduSchedulePlace endTime(String endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getDayOfWeak() {
        return this.dayOfWeak;
    }

    public EduSchedulePlace dayOfWeak(Integer dayOfWeak) {
        this.setDayOfWeak(dayOfWeak);
        return this;
    }

    public void setDayOfWeak(Integer dayOfWeak) {
        this.dayOfWeak = dayOfWeak;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EduSchedulePlace)) {
            return false;
        }
        return id != null && id.equals(((EduSchedulePlace) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EduSchedulePlace{" +
            "id=" + getId() +
            ", isDenominator='" + getIsDenominator() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", dayOfWeak=" + getDayOfWeak() +
            "}";
    }
}
