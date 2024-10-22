package storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import exceptions.SEPException;
import exceptions.SEPNotFoundException;
import exceptions.SEPUnknownException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastDotIndex = name.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : name.substring(lastDotIndex + 1);
    }

    public ArrayList<String> processFile() throws SEPException {
        Path path = Paths.get(this.filePath);
        ArrayList<String> result;

        if (Files.exists(path)) {
            String extension = getFileExtension(path.toFile());

            switch (extension.toLowerCase()) {
            case "csv":
                result = processCsvFile(path.toFile());
                break;
            case "json":
                result = processJsonFile(path.toFile());
                break;
            case "txt":
                result = processTxtFile(path);
                break;
            default:
                throw SEPUnknownException.rejectUnknownFile();
            }
        } else {
            throw SEPNotFoundException.rejectFileNotFound();
        }

        return result;
    }

    // Process CSV file
    private ArrayList<String> processCsvFile(File file) throws SEPException {
        ArrayList<String> result = new ArrayList<>();
        try {
            // Use default CSVParser to split on commas
            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .build();

            // Initialize CSVReader with the parser
            CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                    .withCSVParser(parser)
                    .build();

            String[] line;
            reader.readNext();  // Skip the header
            while ((line = reader.readNext()) != null) {
                String id = "id/" + line[0];
                String gpa = "gpa/" + line[1];
                String preferences = "p/" + line[2];  // Preferences will be read as a single field due to quotes

                result.add(String.format("%s %s %s", id, gpa, preferences));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Process JSON file
    private ArrayList<String> processJsonFile(File file) throws SEPException {
        ArrayList<String> result = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode students = rootNode.get("students");

            for (JsonNode student : students) {
                String id = "id/" + student.get("ID").asText();
                String gpa = "gpa/" + student.get("GPA").asText();
                String preferences = "p/" + student.get("PREFERENCES").asText();
                result.add(String.format("%s %s %s", id, gpa, preferences));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Process Text file
    private ArrayList<String> processTxtFile(Path path) throws SEPException {
        ArrayList<String> result = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(path);
            result.addAll(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
