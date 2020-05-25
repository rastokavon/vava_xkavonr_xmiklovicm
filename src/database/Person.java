package database;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.List;


/**
 * trieda Person predstavuje objekt, ktory je napampovany na databazu a obsahuje vsetky potrebne atributy
 * pre pouzivatela - kazda registracia vytvori triedu Person s prislusnymi zadanymi udajmi
 *
 * kazdy uzivatel patri nejakej Company - trieda ktora reprezentuje miestnost, do ktorej Person patri (kde
 * sa registroval)
 *
 * Person moze vytvarat jednotlive prispevky -> Post a tie potom patria do danej miestnosti, mozu ich vidiet
 * ostatni registrovani pouzivatelia
 *
 * rovnako Person moze komentovat prispevky, ktore patria do jeho miestnosti
 */
@Entity(name = "person")
public class Person {
    private int id;
    private String firstName, lastName, username, password, mail, phoneNumber;
    private Company company;
    private List<Post> posts;
    private List<Comment> comments;

    public Person() {
    }

    public Person(String firstName, String lastName, String username,
                  String password, String mail, String phoneNumber, Company company) {
        setFirstName(firstName);
        setLastName(lastName);
        setUsername(username);
        setPassword(password);
        setMail(mail);
        setPhoneNumber(phoneNumber);
        setCompany(company);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "first_name", length = 25, nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", length = 25, nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "user_name", length = 25, nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", length = 25, nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "mail", length = 25)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Column(name = "phone_number", length = 25)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "room_id", nullable = false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "person")
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "person")
    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


}