package ro.ubb.newsaggregator.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ubb.newsaggregator.persistence.model.NewsArticle;
import ro.ubb.newsaggregator.persistence.model.NewsSource;

public interface NewsSourceRepository extends JpaRepository<NewsSource, Long> {

}
