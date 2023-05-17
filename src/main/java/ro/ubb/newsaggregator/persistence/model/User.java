package ro.ubb.newsaggregator.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Set;


@Entity(name = "users")
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String password;

    private String biography;

    private String picture;

    private String role;

    @OneToMany(mappedBy = "user",cascade = {CascadeType.ALL})
    private Set<NewsArticle> articles;
}
