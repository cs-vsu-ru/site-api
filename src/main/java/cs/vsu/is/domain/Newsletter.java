package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "newsletter")
public class Newsletter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "newsletter_date")
    private LocalDateTime newsletterDate;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "newsletter" , fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"newsletter"}, allowSetters = true)
    private List<Emails> emails = new ArrayList<>();
}
