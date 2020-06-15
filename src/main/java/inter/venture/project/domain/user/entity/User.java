package inter.venture.project.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inter.venture.project.core.validation.constraints.UniqueField;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = true)
    private String firstName;

    @Column(name = "last_name", nullable = true)
    private String lastName;

    @OneToMany(
            mappedBy = "creator",
            orphanRemoval = true,
            cascade = CascadeType.MERGE
    )
    @Transient
    @JsonIgnore
    List<Device> devices;

    public User() {}

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    public User(String username, String password, String firstName, String lastName) {
        this(username, password);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public User(String username, String password, String firstName, String lastName, List<Device> devices) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.devices = devices;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
