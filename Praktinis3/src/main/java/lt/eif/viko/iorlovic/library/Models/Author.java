package lt.eif.viko.iorlovic.library.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

/**
 * Represents an author in the library system.
 */
@Entity
@Table(name = "author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	private String firstName;
	private String lastName;

	@OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
	private Account book;

	@OneToMany(targetEntity = Book.class, cascade = CascadeType.ALL)
	private List<Book> books = new ArrayList<>();

	@OneToMany(targetEntity = Awards.class, cascade = CascadeType.ALL)
	private List<Awards> awards = new ArrayList<>();

	/**
	 * Default constructor for Author.
	 */
	public Author() {

	}

	/**
	 * Constructs an Author with the specified first name and last name.
	 * @param firstName The first name of the author.
	 * @param lastName The last name of the author.
	 */
	public Author(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Gets the full name of the author.
	 * @return The full name of the author.
	 */
	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	/**
	 * Sets the full name of the author.
	 * @param name The full name of the author.
	 */
	public void setName(String name) {
		String[] parts = name.split(" ");
		this.firstName = parts[0];
		this.lastName = parts[1];
	}

	/**
	 * Gets the ID of the author.
	 * @return The ID of the author.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID of the author.
	 * @param id The ID of the author.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the first name of the author.
	 * @return The first name of the author.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the author.
	 * @param firstName The first name of the author.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of the author.
	 * @return The last name of the author.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the author.
	 * @param lastName The last name of the author.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the account associated with the author.
	 * @return The account associated with the author.
	 */
	public Account getAccount() {
		return book;
	}

	/**
	 * Sets the account associated with the author.
	 * @param account The account to set.
	 */
	public void setAccount(Account account) {
		this.book = account;
	}

	/**
	 * Gets the list of books written by the author.
	 * @return The list of books written by the author.
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Sets the list of books written by the author.
	 * @param books The list of books to set.
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	/**
	 * Gets the list of awards received by the author.
	 * @return The list of awards received by the author.
	 */
	public List<Awards> getAwards() {
		return awards;
	}

	/**
	 * Sets the list of awards received by the author.
	 * @param awards The list of awards to set.
	 */
	public void setAwards(List<Awards> awards) {
		this.awards = awards;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * @param o The object to compare.
	 * @return true if this object is the same as the obj argument; false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Author)) return false;
		Author author = (Author) o;
		return Objects.equals(this.id, author.id) &&
				Objects.equals(this.firstName, author.firstName) &&
				Objects.equals(this.lastName, author.lastName);
	}

	/**
	 * Returns a hash code value for the object.
	 * @return A hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.firstName, this.lastName);
	}

	/**
	 * Returns a string representation of the object.
	 * @return A string representation of the object.
	 */
	@Override
	public String toString() {
		return "Author{" +
				"id=" + this.id +
				", firstName='" + this.firstName + '\'' +
				", lastName='" + this.lastName + '\'' +
				'}';
	}
}
