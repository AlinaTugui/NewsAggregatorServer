package ro.ubb.newsaggregator.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubb.newsaggregator.dto.NewsArticleDto;
import ro.ubb.newsaggregator.persistence.model.CustomResponseEntity;
import ro.ubb.newsaggregator.persistence.model.NewsArticle;
import ro.ubb.newsaggregator.persistence.model.NewsSource;
import ro.ubb.newsaggregator.persistence.model.User;
import ro.ubb.newsaggregator.persistence.model.exception.NewsException;
import ro.ubb.newsaggregator.persistence.model.exception.UserException;
import ro.ubb.newsaggregator.service.impl.NewsServiceImpl;
import ro.ubb.newsaggregator.service.impl.UserServiceImpl;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/news")

public class NewsController {

    private final NewsServiceImpl newsServiceImpl;

    private final UserServiceImpl userServiceImpl;

    private final ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<NewsArticle> getNewsByUrl(@RequestParam("url") String url) throws NewsException {
        NewsArticle newsArticle = newsServiceImpl.getNewsByUrl(url);

        return new ResponseEntity<>(newsArticle,HttpStatus.OK);

    }

    @GetMapping("saved/{userId}")
    public ResponseEntity<List<NewsArticleDto>> getAllNews(@PathVariable Long userId) {
        List<NewsArticleDto> newsArticleList = newsServiceImpl.getAllNews(userId).stream().map(x->modelMapper.map(x,NewsArticleDto.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(newsArticleList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> saveNews(@RequestBody NewsArticleDto newsArticle) throws UserException {
        Long id = 1L;
        NewsSource newsSource = new NewsSource();
        newsSource.setName(newsArticle.getSourceName());
        newsSource.setUrl(newsArticle.getSourceUrl());
        User user = userServiceImpl.getUserById(newsArticle.getUserId());
        NewsArticle newsArticle1 = new NewsArticle(id,newsSource, user,newsArticle.getAuthor()
                ,newsArticle.getTitle(),newsArticle.getDescription(),newsArticle.getUrl(),newsArticle.getImage(),newsArticle.getPublishedAt(),newsArticle.getContent());
        newsServiceImpl.saveSource(newsSource);
        NewsArticle savedArticle = newsServiceImpl.saveArticle(newsArticle1);

        if (savedArticle == null) {
            return new ResponseEntity<>(CustomResponseEntity
                    .getErrors(List.of("An animal having the same name already exists.")), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(CustomResponseEntity.getMessage("Animal added successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteNews(@PathVariable Long userId,@RequestParam("url") String url) {
        NewsArticle deletedNews = newsServiceImpl.deleteNews(url,userId);

        if(deletedNews == null){
            return new ResponseEntity<>(CustomResponseEntity
                    .getErrors(List.of("Article with the provided url does not exist.")), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(CustomResponseEntity.getMessage("Article deleted successfully!"), HttpStatus.OK);
        }
    }

}
