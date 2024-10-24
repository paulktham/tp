package storage;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import exceptions.*;

import parser.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Storage {
    private String filePath;
    private Parser parser;

    public Storage(String filePath, Parser parser) {
        this.filePath = filePath;
        this.parser = parser;
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDotIndex = name.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : name.substring(lastDotIndex + 1);
    }

    /*
    private boolean hasLoadSuccessful(String student) {
        try {
            this.parser.parseInput("add " + student);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    */

    public boolean processFile() throws SEPException {
        Path path = Paths.get(this.filePath);
        boolean result;

        if (Files.exists(path)) {
            String extension = getFileExtension(path.toFile());

            switch (extension.toLowerCase()) {
            case "csv":
                result = processCsvFile(path.toFile());
                break;
            default:
                result = false;
                break;
//            case "json":
//                result = processJsonFile(path.toFile());
//                break;
//            case "txt":
//                result = processTxtFile(path);
//                break;
//            default:
//                throw SEPUnknownException.rejectUnknownFile();
            }
        } else {
            System.out.println("No file found.");
            return false;
        }
        return result;
    }


    // Process CSV file
    private boolean processCsvFile(File file) {
        // Save the current System.out
        PrintStream originalOut = System.out;

        // Suppress output
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            // Use default CSVParser to split on commas
            CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

            // Initialize CSVReader with the parser
            CSVReader reader = new CSVReaderBuilder(new FileReader(file)).withCSVParser(parser).build();

            String[] line;
            reader.readNext();  // Skip the header
            while ((line = reader.readNext()) != null) {
                if (line.length != 3) {
                    throw SEPFormatException.rejectDataFormat(line);
                }
                assert line.length == 3;
                if (!this.parser.isValidData(line[0].trim(),line[1].trim(),line[2].trim())) {
                    throw SEPIOException.rejectCSVFile();
                }

                String id = "id/" + line[0].trim();
                String gpa = "gpa/" + line[1].trim();
                String preferences = "p/" + line[2].trim();

                String student = String.format("%s %s %s", id, gpa, preferences);

                this.parser.parseInput("add " + student);
            }
            System.setOut(originalOut);
        } catch (IOException | CsvValidationException | SEPException e) {
            System.setOut(originalOut);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


}
