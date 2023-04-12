package cs.vsu.is.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ScientificWorkType.
 */
@Entity
@Table(name = "scientific_work_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ScientificWorkType implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @ManyToMany
  @JoinTable(name = "rel_scientific_work_type__scientific_leadeship", joinColumns = @JoinColumn(name = "scientific_work_type_id"), inverseJoinColumns = @JoinColumn(name = "scientific_leadeship_id"))
  // jhipster-needle-entity-add-field - JHipster will add fields here

  public Long getId() {
    return this.id;
  }

  public ScientificWorkType id(Long id) {
    this.setId(id);
    return this;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public ScientificWorkType name(String name) {
    this.setName(name);
    return this;
  }

  public void setName(String name) {
    this.name = name;
  }
  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
  // setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ScientificWorkType)) {
      return false;
    }
    return id != null && id.equals(((ScientificWorkType) o).id);
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
    return "ScientificWorkType{" +
        "id=" + getId() +
        ", name='" + getName() + "'" +
        "}";
  }
}
