package ro.ubb.newsaggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NewsArticleDto {
    private String sourceName;

    private String sourceUrl;

    private String author;

    private String title;

    private String description;

    private String url;

    private String image;

    private String publishedAt;

    private String content;

    private Long userId;
}
