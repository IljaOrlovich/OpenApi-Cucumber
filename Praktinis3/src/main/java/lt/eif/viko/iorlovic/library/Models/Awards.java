package lt.eif.viko.iorlovic.library.Models;

import jakarta.persistence.*;
import lt.eif.viko.iorlovic.library.Status;

import java.util.Objects;

/**
 * Entity class representing an award.
 */
@Entity
@Table(name = "awards")
public class Awards {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String title;
    private String years;
    private Status status;

    /**
     * Constructs a new Awards object with the specified title, years, and status.
     * @param title The title of the award.
     * @param years The years the award was received.
     * @param status The status of the award.
     */
    public Awards(String title, String years, Status status) {
        this.title = title;
        this.years = years;
        this.status = status;
    }

    /**
     * Default constructor for Awards.
     */
    public Awards() {}

    /**
     * Retrieves the ID of the award.
     * @return The ID of the award.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the award.
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retrieves the title of the award.
     * @return The title of the award.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the award.
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the years the award was received.
     * @return The years the award was received.
     */
    public String getYears() {
        return years;
    }

    /**
     * Sets the years the award was received.
     * @param years The years to set.
     */
    public void setYears(String years) {
        this.years = years;
    }

    /**
     * Retrieves the status of the award.
     * @return The status of the award.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the award.
     * @param status The status to set.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Awards))
            return false;
        Awards awards = (Awards) o;
        return Objects.equals(this.id, awards.id) && Objects.equals(this.title, awards.title) && Objects.equals(this.years, awards.years)
                && this.status == awards.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.years, this.status);
    }

    @Override
    public String toString() {
        return "Awards{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", years=" + years +
                ", status=" + status +
                '}';
    }
}
