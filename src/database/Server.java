package database;

import javax.persistence.*;

@Entity
@Table(name = "database.Server")
public class Server {
    private static final long serialVersionUID = -1798070786993154676L;

    public Server() {}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;


    @Column(name="name", length = 50)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}