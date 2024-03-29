package cs.vsu.is.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A Schedule.
 */
@Entity
@Table(name = "schedule")
@SuppressWarnings("common-java:DuplicatedBlocks")

public class Schedule implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "uploading_time")
  private Instant uploadingTime;

  @Column(name = "is_actual")
  private Boolean isActual;

  // jhipster-needle-entity-add-field - JHipster will add fields here
    public Schedule(Long id, String name, Instant uploadingTime, Boolean isActual) {
        this.id = id;
        this.name = name;
        this.uploadingTime = uploadingTime;
        this.isActual = isActual;
    }
    public Schedule(String name, Instant uploadingTime, Boolean isActual) {
        this.name = name;
        this.uploadingTime = uploadingTime;
        this.isActual = isActual;
    }

    public Schedule() {

    }

    public Long getId() {
    return this.id;
  }

  public Schedule id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public Schedule name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Instant getUploadingTime() {
    return this.uploadingTime;
  }

  public Schedule uploadingTime(Instant uploadingTime) {
    this.setUploadingTime(uploadingTime);
    return this;
  }

  public void setUploadingTime(Instant uploadingTime) {
    this.uploadingTime = uploadingTime;
  }

  public Boolean getIsActual() {
    return this.isActual;
  }

  public Schedule isActual(Boolean isActual) {
    this.setIsActual(isActual);
    return this;
  }

  public void setIsActual(Boolean isActual) {
    this.isActual = isActual;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Schedule)) {
      return false;
    }
    return id != null && id.equals(((Schedule) o).id);
  }

  @Override
  public int hashCode() {
    // see
    // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "Schedule{" +
        "id=" + getId() +
        ", name='" + getName() + "'" +
        ", uploadingTime='" + getUploadingTime() + "'" +
        ", isActual='" + getIsActual() + "'" +
        "}";
  }
}
