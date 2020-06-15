package inter.venture.project.domain.user.entity;


import javax.persistence.*;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "secret", nullable = true)
    private String secret;

    @Column(name = "properties")
    private String properties;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "creator", referencedColumnName = "id")
    private User creator;

    public Device() {
    }

    public Device(String name, String description, String secret) {
        setName(name);
        setDescription(description);
        setSecret(secret);
    }

    public Device(String name, String description, String secret, User creator) {
        setName(name);
        setDescription(description);
        setSecret(secret);
        setCreator(creator);
    }

    public Device(String name, String description, String secret, String properties, User creator) {
        this.name = name;
        this.description = description;
        this.secret = secret;
        this.properties = properties;
        this.creator = creator;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
