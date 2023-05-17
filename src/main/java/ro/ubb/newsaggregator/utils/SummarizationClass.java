package ro.ubb.newsaggregator.utils;

import org.python.util.PythonInterpreter;

import java.io.IOException;
import java.io.StringWriter;


public class SummarizationClass {
    public static void runPythonSummatizationScript(String URL) {

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("\"C:\\Users\\Alina\\AppData\\Local\\Microsoft\\WindowsApps\\python3.exe\"","D:\\faculta\\Anul 3\\licenta\\NewsAggregatorServer\\src\\main\\java\\ro\\ubb\\newsaggregator\\utils\\summarization.py");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            String result = new String(process.getInputStream().readAllBytes()).strip();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String url = "";
        runPythonSummatizationScript(url);
    }

}
