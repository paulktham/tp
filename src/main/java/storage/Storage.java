package storage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import exceptions.SEPException;
import exceptions.SEPIOException;
import parser.Parser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


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

    public boolean processFile() {
        Path path = Paths.get(this.filePath);
        boolean result;

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
                return false;
            }
        } else {
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
                    throw SEPIOException.rejectCSVDataFormat(line);
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

    public void printExampleJson() {
        try {
            // Create ObjectMapper to build the JSON structure
            ObjectMapper objectMapper = new ObjectMapper();

            // Create root node "students"
            ObjectNode rootNode = objectMapper.createObjectNode();
            ArrayNode studentsArray = objectMapper.createArrayNode();

            // Example student 1
            ObjectNode student1 = objectMapper.createObjectNode();
            student1.put("ID", "A1234567J");
            student1.put("GPA", "4.5");
            student1.put("PREFERENCES", "{1,2,3}");

            // Example student 2
            ObjectNode student2 = objectMapper.createObjectNode();
            student2.put("ID", "A7654321K");
            student2.put("GPA", "3.8");
            student2.put("PREFERENCES", "{2,3,1}");

            // Example student 3
            ObjectNode student3 = objectMapper.createObjectNode();
            student3.put("ID", "A1357913L");
            student3.put("GPA", "4.0");
            student3.put("PREFERENCES", "{3,1,2}");

            // Add students to the array
            studentsArray.add(student1);
            studentsArray.add(student2);
            studentsArray.add(student3);

            // Add the array to the root node
            rootNode.set("students", studentsArray);

            // Convert root node to pretty-printed JSON string
            String exampleJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);

            // Print the example JSON
            System.out.println("Example of a correct JSON file:");
            System.out.println(exampleJson);

        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
    }

    // Process JSON file
    private boolean processJsonFile(File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        // Save the current System.out
        PrintStream originalOut = System.out;

        // Suppress output
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode students = rootNode.get("students");

            // Check if "students" node exists and is an array
            if (students == null || !students.isArray()) {
                throw SEPIOException.missingStudentsJSONArray();
            }

            for (JsonNode student : students) {
                // Check if required fields are present
                if (!student.has("ID") || !student.has("GPA") || !student.has("PREFERENCES")) {
                    throw SEPIOException.missingRequiredJSONFields();
                }

                // Validate the data format
                if (!this.parser.isValidData(student.get("ID").asText(),
                        student.get("GPA").asText(),
                        student.get("PREFERENCES").asText())) {
                    throw SEPIOException.invalidDataJSONFormat();
                }

                // Extract and format the command
                String id = "id/" + student.get("ID").asText();
                String gpa = "gpa/" + student.get("GPA").asText();
                String preferences = "p/" + student.get("PREFERENCES").asText();
                String command = String.format("%s %s %s", id, gpa, preferences);

                this.parser.parseInput("add " + command);
            }
            System.setOut(originalOut);
        } catch (IOException | SEPException e) {
            System.setOut(originalOut);
            System.out.println(e.getMessage());
            printExampleJson();
            return false;
        }
        return true;
    }

    // Process Text file
    private boolean processTxtFile(Path path) {
        // Save the current System.out to restore later
        PrintStream originalOut = System.out;

        // Suppress output
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            // Read all lines from the file
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                // Split the line by commas and spaces
                String[] parts = line.split(", ");

                // Ensure the line has 3 parts: id, gpa, p
                if (parts.length != 3) {
                    throw SEPIOException.missingTXTRequiredFields();
                }

                String idPart = parts[0];
                String gpaPart = parts[1];
                String preferencesPart = parts[2];

                // Validate the format of each part
                if (!idPart.startsWith("id/") || !gpaPart.startsWith("gpa/") || !preferencesPart.startsWith("p/")) {
                    throw SEPIOException.missingTXTRequiredFields();
                }

                String id = idPart.substring(3);  // Extract the actual ID value
                String gpa = gpaPart.substring(4);  // Extract the actual GPA value
                String preferences = preferencesPart.substring(2);  // Extract the actual Preferences value

                // Validate the data using your existing parser
                if (!this.parser.isValidData(id, gpa, preferences)) {
                    throw SEPIOException.invalidTXTDataFormat();
                }
                this.parser.parseInput("add " + line);
            }
            System.setOut(originalOut);
        } catch (IOException | SEPException e) {
            System.setOut(originalOut);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
