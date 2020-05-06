package database;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity(name = "company")
public class Company {
    private int id;
    private String name, street, city, country, postalCode, mail, phoneNumber;

    public Company() {}
    public Company(String name, String street, String city, String country,
                   String postalCode, String mail, String phoneNumber) {
        setName(name);
        setStreet(street);
        setCity(city);
        setCountry(country);
        setPostalCode(postalCode);
        setMail(mail);
        setPhoneNumber(phoneNumber);
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column (name = "name", length = 50, nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Column (name = "street", length = 40, nullable = false)
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @Column (name = "city", length = 40, nullable = false)
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Column (name = "country", length = 40, nullable = false)
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Column (name = "postal_code", length = 10, nullable = false)
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Column (name = "mail", length = 255, nullable = false)
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column (name = "phone_number", length = 20)
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}