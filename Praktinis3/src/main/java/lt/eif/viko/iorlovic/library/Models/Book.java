package lt.eif.viko.iorlovic.library.Models;

import jakarta.persistence.*;
import lt.eif.viko.iorlovic.library.Status;

import java.util.Objects;

/**
 * Entity class representing a book.
 */
@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	private String name;
	private String years;
	private Status status;

	/**
	 * Constructs a new Book object with the specified name, years, and status.
	 * @param name The name of the book.
	 * @param years The years the book was published.
	 * @param status The status of the book.
	 */
	public Book(String name, String years, Status status) {
		this.name = name;
		this.years = years;
		this.status = status;
	}

	/**
	 * Default constructor for Book.
	 */
	public Book() {}

	/**
	 * Retrieves the ID of the book.
	 * @return The ID of the book.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID of the book.
	 * @param id The ID to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Retrieves the name of the book.
	 * @return The name of the book.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the book.
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves the years the book was published.
	 * @return The years the book was published.
	 */
	public String getYears() {
		return years;
	}

	/**
	 * Sets the years the book was published.
	 * @param years The years to set.
	 */
	public void setYears(String years) {
		this.years = years;
	}

	/**
	 * Retrieves the status of the book.
	 * @return The status of the book.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status of the book.
	 * @param status The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (!(o instanceof Book))
			return false;
		Book book = (Book) o;
		return Objects.equals(this.id, book.id) && Objects.equals(this.name, book.name) && Objects.equals(this.years, book.years)
				&& this.status == book.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.name, this.years, this.status);
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", name='" + name + '\'' +
				", years=" + years +
				", status=" + status +
				'}';
	}
}
