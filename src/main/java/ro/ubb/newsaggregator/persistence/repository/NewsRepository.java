package ro.ubb.newsaggregator.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.newsaggregator.persistence.model.NewsArticle;

import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<NewsArticle, Long> {
    Optional<NewsArticle> findByUrl(String url);
}
