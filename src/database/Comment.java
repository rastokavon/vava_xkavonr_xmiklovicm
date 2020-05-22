package database;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import java.util.Date;


/**
 * trieda Comment predstavuje nejaky text, ktorym je mozne okomentovat prispevok - teda Comment vzdy
 * patri nejakemu (jednemmu) prispevku -> Post
 *
 * obsahuje vsetky potrebne atributy pre identifikaciu komentu, ako je cas pridania, prispevok a pouzivatela,
 * ktory prispevok komentoval (napisal koment)
 */
@Entity(name = "comment")
public class Comment {
    private int id;
    private String text;
    private Date date;
    private Post post;
    private Person person;

    public Comment() {
    }

    public Comment(String text, Date date, Post post, Person person) {
        setText(text);
        setDate(date);
        setPost(post);
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


    @Column(name = "text", length = 1000, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "post_id", nullable = false)
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "person_id", nullable = false)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}