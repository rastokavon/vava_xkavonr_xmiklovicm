package database;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.Date;


@Entity(name = "post")
public class Post {
    private int id;
    private String title;
    private String text;
    private Date date;
    private Person person;

    public Post() {}
    public Post(String title, String text, Date date, Person person) {
        setTitle(title);
        setText(text);
        setDate(date);
        setPerson(person);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Column (name = "title", length = 40, nullable = false)
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Column (name = "text", length = 1000, nullable = false)
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    @Column (name = "date", nullable = false)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn (name = "person_id", nullable = false)
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
}