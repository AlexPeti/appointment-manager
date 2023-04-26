package gr.aueb.cf.appointmentmanager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME" ,nullable = false)
    private String username;

    @Column(name = "PASSWORD" ,nullable = false)
    private String password;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
