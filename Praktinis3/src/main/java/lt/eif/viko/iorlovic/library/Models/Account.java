package lt.eif.viko.iorlovic.library.Models;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Represents an account in the library system.
 */
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String userName;
    private String password;

    /**
     * Default constructor for Account.
     */
    public Account() {

    }

    /**
     * Constructs an Account with the specified username and password.
     * @param userName The username of the account.
     * @param password The password of the account.
     */
    public Account(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * Gets the ID of the account.
     * @return The ID of the account.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the ID of the account.
     * @param id The ID of the account.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username of the account.
     * @return The username of the account.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username of the account.
     * @param userName The username of the account.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the password of the account.
     * @return The password of the account.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the account.
     * @param password The password of the account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @param o The object to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Account))
            return false;
        Account account = (Account) o;
        return Objects.equals(this.id, account.id) && Objects.equals(this.userName, account.userName)
                && Objects.equals(this.userName, account.password);
    }

    /**
     * Returns a hash code value for the object.
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.userName, this.password);
    }

    /**
     * Returns a string representation of the object.
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
