import static org.junit.Assert.*;

import org.example.LogComponent.ILog;
import org.example.LogComponent.LogComponent;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class LogComponentTest {
    private String readLogFile(String fileName) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());
            }
        }
        return content.toString();
    }
    @Test
    public void testWritingToLog() throws IOException, InterruptedException {
        LogComponent logComponent = new LogComponent();
        String message = "Test log message";
        logComponent.write(message);

        // Wait for a short duration to ensure writing is completed
        Thread.sleep(1000);

        // Read the content of the log file
        String logFileName = logComponent.getLogFileName();
        String fileContent = readLogFile(logFileName);

        assertTrue(fileContent.contains(message));
    }

    @Test
    public void testNewFilesCreatedOnMidnightCross() throws IOException, InterruptedException {
        LocalDate currentDate = LocalDate.now();
        LogComponent logComponent = new LogComponent();
        logComponent.write("Test message before crossing midnight");
        while (LocalDate.now().isEqual(currentDate)){ //wait until midnight is crossed
            TimeUnit.SECONDS.sleep(1);
        }
        String newLogFileName = "log_" + LocalDate.now() + ".txt";
        String message = "Test message after crossing midnight";
        logComponent.write(message);

        BufferedReader reader = new BufferedReader(new FileReader(newLogFileName));
        String line = reader.readLine();
        reader.close();

        // Assert that the log message exists in the new log file
        assertNotNull(line);
        assertTrue(line.contains("Test message after crossing midnight"));
    }

    @Test
    public void testImmediateStopBehavior() throws IOException {

        LogComponent logComponent = new LogComponent();
        String message = "Test log message";
        logComponent.write(message);

        // Stop the component without waiting for completion
        logComponent.stop(false);
        assertTrue(logComponent.isStopped());
    }

    @Test
    public void testWaitForCompletionStopBehavior() throws IOException, InterruptedException {
        // Create LogComponent instance
        LogComponent logComponent = new LogComponent();

        // Write a long log message asynchronously
        String longMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                + "Nullam nec fermentum magna. Integer nec malesuada turpis. "
                + "Nunc fringilla lectus vitae dapibus vestibulum. "
                + "Sed luctus tempor tincidunt. Fusce congue viverra justo sit amet fringilla. "
                + "Sed ac tellus semper, volutpat metus non, fringilla libero. "
                + "Praesent non sapien nec dui vestibulum rhoncus. "
                + "Nam et risus at mauris tempor vehicula. "
                + "Donec laoreet eleifend convallis. Nam eget leo in dui auctor ullamcorper.";

        logComponent.write(longMessage);

        // Stop the component and wait for completion
        logComponent.stop(true);

        assertTrue(logComponent.isStopped());

        //check if the message is successfully written in the file
        String logFileName = logComponent.getLogFileName();
        String fileContent = readLogFile(logFileName);

        assertTrue(fileContent.contains(longMessage));

    }


}
