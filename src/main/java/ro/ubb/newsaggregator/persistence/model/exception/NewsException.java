package ro.ubb.newsaggregator.persistence.model.exception;

public class NewsException extends Exception {

    public NewsException(String errorMessage) {
        super(errorMessage);
    }
}
