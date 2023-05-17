package ro.ubb.newsaggregator.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ro.ubb.newsaggregator.persistence.model.NewsArticle;
import ro.ubb.newsaggregator.persistence.model.NewsSource;
import ro.ubb.newsaggregator.persistence.repository.NewsRepository;
import ro.ubb.newsaggregator.persistence.repository.NewsSourceRepository;
import ro.ubb.newsaggregator.service.NewsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    NewsRepository newsRepository;
    NewsSourceRepository newsSourceRepository;
    @Override
    public NewsArticle getNewsByUrl(String url) {
        Optional<NewsArticle> res = newsRepository.findByUrl(url);
        System.out.println(res.get());
        return res.orElse(null);

    }

    @Override
    public List<NewsArticle> getAllNews(Long userId) {

        return newsRepository.findAll().stream().filter(x-> {
            return x.getUser().getId().equals(userId);
        }).collect(Collectors.toList());
    }

    @Override
    public NewsArticle deleteNews(String url, Long userId) {
        Optional<NewsArticle> newsArticle = newsRepository.findByUrl(url);
        if(newsArticle.isEmpty()){
            return null;
        }
        NewsArticle toDelete = newsArticle.get();
        newsRepository.delete(toDelete);
        return toDelete;
    }

    @Override
    public NewsArticle saveArticle(NewsArticle newsArticle1) {
        if(newsRepository.findByUrl(newsArticle1.getUrl()).isPresent()){
            return null;
        }
        return newsRepository.save(newsArticle1);
    }

    @Override
    public void saveSource(NewsSource newsSource) {
        newsSourceRepository.save(newsSource);
    }
}
