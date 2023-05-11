package ro.ubb.newsaggregator.utils;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NewsAggregator {
    public static void main(String[] args) {
        String[] apiUrls = {
                "https://newsapi.org/v2/top-headlines?country=us&apiKey=60c46199e428488da389f3d134bb1576",
                /*"https://api.nytimes.com/svc/topstories/v2/home.json?api-key=YOUR_API_KEY",
                "https://api.theguardian.com/search?api-key=YOUR_API_KEY&q=politics&show-fields=headline"*/
        };

        for (String apiUrl : apiUrls) {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                int responseCode = conn.getResponseCode();

                if (responseCode == 200) {
                    Scanner sc = new Scanner(url.openStream());
                    StringBuilder sb = new StringBuilder();
                    while (sc.hasNext()) {
                        sb.append(sc.nextLine());
                    }
                    String json = sb.toString();
                    System.out.println(json);
                    sc.close();
                } else {
                    System.out.println("Error: " + responseCode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
