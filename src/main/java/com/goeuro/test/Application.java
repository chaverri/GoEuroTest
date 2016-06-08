package com.goeuro.test;

import com.goeuro.test.api.GoEuroAPICallException;
import com.goeuro.test.api.GoEuroAPIConsumer;
import com.goeuro.test.dto.GoEuroSuggestion;
import com.goeuro.test.utils.CSVFileGenerationException;
import com.goeuro.test.utils.CSVGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.util.List;


public class Application {

    public static void main(String[] args) {

        if(args.length == 0){
            System.out.println("The city name must be specified.");
            return;
        }

        String cityName = args[0];

        if(cityName == null || cityName == ""){
            System.out.println("The city name must be specified and valid.");
            return;
        }

        ApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        GoEuroAPIConsumer apiConsumer = context.getBean(GoEuroAPIConsumer.class);
        CSVGenerator csvGenerator = context.getBean(CSVGenerator.class);

        List<GoEuroSuggestion> suggestions = null;

        try {
            suggestions = apiConsumer.getSuggestions(cityName);

            if(suggestions.size() == 0){
                System.out.println( String.format("No suggestions for the specified city \"%s\" were found, so not creating a file.", cityName));
                return;
            }

            String fileName = String.format("%s_%d.csv", cityName, System.currentTimeMillis());

            File file = new File(fileName);

            csvGenerator.writeItemsToFile(GoEuroSuggestion.class, suggestions, ',', file);

            System.out.println(String.format("File created: %s", file.getAbsolutePath()));


        } catch (GoEuroAPICallException e) {
            System.out.println(e.getMessage());
        } catch (CSVFileGenerationException e) {
            System.out.println(e.getMessage());
        }
    }
}
