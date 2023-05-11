package ro.ubb.newsaggregator.persistence.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomResponseEntity {
    public static Map<String, String> getMessage(String message){
        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return response;
    }

    public static Map<String, List<String>> getErrors(List<String> errors){
        Map<String, List<String>> response = new HashMap<>();
        response.put("error", errors);

        return response;
    }

}
