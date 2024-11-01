package filehandler;

import exceptions.SEPEmptyException;
import exceptions.SEPException;
import exceptions.SEPUnknownException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import studentlist.StudentList;
import ui.UI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileHandlerTest {
    private FileHandler fileHandler;
    private Parser parser;
    private StudentList studentList;
    private UI ui;

    private static final Path CSV_FILE_PATH = Paths.get("test.csv");
    private static final Path JSON_FILE_PATH = Paths.get("test.json");
    private static final Path TXT_FILE_PATH = Paths.get("test.txt");

    @BeforeEach
    void setUp() {
        ui = new UI();
        studentList = new StudentList(ui);
        parser = new Parser(studentList, ui);
    }

    @Test
    void hasProcessFileSuccessfully_CSVFile_ProcessedSuccessfully() throws IOException, SEPException {
        fileHandler = new FileHandler(CSV_FILE_PATH.toString(), parser);

        boolean result = fileHandler.hasProcessFileSuccessfully();

        assertTrue(result, "Expected CSV file to be processed successfully.");
    }

    @Test
    void hasProcessFileSuccessfully_JSONFile_ProcessedSuccessfully() throws IOException, SEPException {
        fileHandler = new FileHandler(JSON_FILE_PATH.toString(), parser);

        boolean result = fileHandler.hasProcessFileSuccessfully();

        assertTrue(result, "Expected JSON file to be processed successfully.");
    }

    @Test
    void hasProcessFileSuccessfully_TXTFile_ProcessedSuccessfully() throws IOException, SEPException {
        fileHandler = new FileHandler(TXT_FILE_PATH.toString(), parser);

        boolean result = fileHandler.hasProcessFileSuccessfully();

        assertTrue(result, "Expected TXT file to be processed successfully.");
    }

    @Test
    void hasProcessFileSuccessfully_missingFile_FileNotFound() throws IOException {
        fileHandler = new FileHandler("nonexistent.glb", parser);

        SEPException exception = assertThrows(SEPEmptyException.class, fileHandler::hasProcessFileSuccessfully);
        assertEquals("This file don't exist...Don't make me go through your folder for nothing leh :(.\n" +
                "Process Outcome: No data is loaded. You can continue using the program.", exception.getMessage());
    }

    @Test
    void hasProcessFileSuccessfully_blankFile_EmptyFile() throws IOException, SEPException {
        File emptyFile = Files.createTempFile("empty", ".csv").toFile();
        emptyFile.deleteOnExit();

        fileHandler = new FileHandler(emptyFile.getAbsolutePath(), parser);

        SEPException exception = assertThrows(SEPEmptyException.class, fileHandler::hasProcessFileSuccessfully);
        assertEquals("Nothing to read from the file. Please ensure there is content next time.", exception.getMessage());
    }

    @Test
    void hasProcessFileSuccessfully__UnknownFileType() throws IOException {
        fileHandler = new FileHandler("test.xml", parser);

        SEPException exception = assertThrows(SEPUnknownException.class, fileHandler::hasProcessFileSuccessfully);
        assertEquals("Unknown/Unsupported file type detected!", exception.getMessage());
    }
}
