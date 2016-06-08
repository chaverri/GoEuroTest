package com.goeuro.test.utils;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

@Component
public class CSVGenerator {

    private final static CsvMapper mapper = new CsvMapper();

    public void writeItemsToFile(Class<?> clazz, Collection<?> items, char separator, File file) throws CSVFileGenerationException {

        try {
            CsvSchema schema = mapper.schemaFor(clazz).withColumnSeparator(separator).withHeader();

            try (FileOutputStream fileStream = new FileOutputStream(file.getAbsoluteFile())) {
                mapper.writer(schema).writeValue(fileStream, items);
                fileStream.flush();
            }
        }catch (IOException e){
            throw new CSVFileGenerationException("Error generating the csv file.");
        }

    }
}
