package cs.vsu.is.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "static_pages" )
public class StaticPages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "content_about")
    private String contentAbout;


    @Column(name = "content_education")
    private String contentEducation;


    @Column(name = "content_students")
    private String contentStudents;


    @Column(name = "content_partners")
    private String contentPartners;


    @Column(name = "content_confidential")
    private String contentConfidential;


    @Column(name = "content_contacts")
    private String contentContacts;
}
