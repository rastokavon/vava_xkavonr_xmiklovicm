package database;

import javax.persistence.*;
import java.util.List;


/**
 * trieda Company predstavuje samotnu miestnost, do ktorej sa prihlasuju pouzivatelia (Person)
 *
 * firma sa po zaplateni poplatku zaregistruje, zada vsetky kontaktne udaje
 * vytvori sa teda nova miestnost (id Company), do ktorej sa prihlasuju pouzivatelia a mozu pridavat a sledovat
 * prispevky
 */
@Entity(name = "company")
public class Company {
    private int id;
    private String name, street, city, country, postalCode, mail, phoneNumber, password;
    private List<Person> users;

    public Company() {
    }

    public Company(String name, String street, String city, String country,
                   String postalCode, String mail, String phoneNumber, String password) {
        setName(name);
        setStreet(street);
        setCity(city);
        setCountry(country);
        setPostalCode(postalCode);
        setMail(mail);
        setPhoneNumber(phoneNumber);
        setPassword(password);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name", length = 50, nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password", length = 30, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "street", length = 40, nullable = false)
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Column(name = "city", length = 40, nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "country", length = 40, nullable = false)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "postal_code", length = 10, nullable = false)
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column(name = "mail", length = 255, nullable = false)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column(name = "phone_number", length = 20)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "company")
    public List<Person> getUsers() {
        return users;
    }

    public void setUsers(List<Person> users) {
        this.users = users;
    }
}