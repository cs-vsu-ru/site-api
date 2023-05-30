package cs.vsu.is.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A Students.
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "students")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Students implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "student_pers_num")
		private String studentPersonalNumber;
		
		@ManyToMany
		@JoinTable(name = "rel_students__scientific_leadership", joinColumns = @JoinColumn(name = "students_id"), inverseJoinColumns = @JoinColumn(name = "scientific_leadership_id"))
		private Set<ScientificLeaderships> scientificLeaderships = new HashSet<>();

}
