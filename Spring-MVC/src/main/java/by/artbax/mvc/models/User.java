package by.artbax.mvc.models;

import by.artbax.mvc.forms.UserForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "java_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @OneToMany(mappedBy = "owner")
    private List<Car> cars;

    public static User from(UserForm form) {
        return User.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .build();
    }
}