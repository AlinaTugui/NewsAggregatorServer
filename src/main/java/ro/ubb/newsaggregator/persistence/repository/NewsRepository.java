package ro.ubb.newsaggregator.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.newsaggregator.persistence.model.NewsArticle;

@Repository
public interface NewsRepository extends JpaRepository<NewsArticle, Long> {
}