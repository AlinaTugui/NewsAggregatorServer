package ro.ubb.newsaggregator.service;

import ro.ubb.newsaggregator.persistence.model.NewsArticle;
import ro.ubb.newsaggregator.persistence.model.NewsSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface NewsService {
    NewsArticle getNewsByUrl(String url);

    List<NewsArticle> getAllNews(Long userId);

    NewsArticle deleteNews(String url, Long userId);

    NewsArticle saveArticle(NewsArticle newsArticle1);

    void saveSource(NewsSource newsSource);
}
