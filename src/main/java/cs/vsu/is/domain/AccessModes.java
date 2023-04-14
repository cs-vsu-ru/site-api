package cs.vsu.is.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A AccessModes.
 */
@Entity
@Table(name = "access_modes")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccessModes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "accessModes")
    @JsonIgnoreProperties(value = { "employee", "accessModes" }, allowSetters = true)
    private Set<Articles> articles = new HashSet<>();

    @OneToMany(mappedBy = "accessModes")
    @JsonIgnoreProperties(value = { "employee", "accessModes", "employee" }, allowSetters = true)
    private Set<Pages> pages = new HashSet<>();

    @OneToMany(mappedBy = "accessModes")
    @JsonIgnoreProperties(value = { "employee", "accessModes" }, allowSetters = true)
    private Set<Events> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AccessModes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public AccessModes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Articles> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Articles> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setAccessModes(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setAccessModes(this));
        }
        this.articles = articles;
    }

    public AccessModes articles(Set<Articles> articles) {
        this.setArticles(articles);
        return this;
    }

    public AccessModes addArticles(Articles articles) {
        this.articles.add(articles);
        articles.setAccessModes(this);
        return this;
    }

    public AccessModes removeArticles(Articles articles) {
        this.articles.remove(articles);
        articles.setAccessModes(null);
        return this;
    }

    public Set<Pages> getPages() {
        return this.pages;
    }

    public void setPages(Set<Pages> pages) {
        if (this.pages != null) {
            this.pages.forEach(i -> i.setAccessModes(null));
        }
        if (pages != null) {
            pages.forEach(i -> i.setAccessModes(this));
        }
        this.pages = pages;
    }

    public AccessModes pages(Set<Pages> pages) {
        this.setPages(pages);
        return this;
    }

    public AccessModes addPages(Pages pages) {
        this.pages.add(pages);
        pages.setAccessModes(this);
        return this;
    }

    public AccessModes removePages(Pages pages) {
        this.pages.remove(pages);
        pages.setAccessModes(null);
        return this;
    }

    public Set<Events> getEvents() {
        return this.events;
    }

    public void setEvents(Set<Events> events) {
        if (this.events != null) {
            this.events.forEach(i -> i.setAccessModes(null));
        }
        if (events != null) {
            events.forEach(i -> i.setAccessModes(this));
        }
        this.events = events;
    }

    public AccessModes events(Set<Events> events) {
        this.setEvents(events);
        return this;
    }

    public AccessModes addEvents(Events events) {
        this.events.add(events);
        events.setAccessModes(this);
        return this;
    }

    public AccessModes removeEvents(Events events) {
        this.events.remove(events);
        events.setAccessModes(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccessModes)) {
            return false;
        }
        return id != null && id.equals(((AccessModes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccessModes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
