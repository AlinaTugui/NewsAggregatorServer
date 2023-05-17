package ro.ubb.newsaggregator.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "news_sources")
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
public class NewsSource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String name;

    private String description;

    private String url;

    private String category;

    private String language;

    private String country;

    @OneToMany(mappedBy = "source",cascade = {CascadeType.ALL})
    private Set<NewsArticle> newsArticleSet;
}
