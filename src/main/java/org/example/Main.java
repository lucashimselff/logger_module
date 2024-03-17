package org.example;
import org.example.LogComponent.ILog;
import org.example.LogComponent.LogComponent;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Instantiate the LogComponent
        ILog logComponent = new LogComponent();

        // Write some log messages
        logComponent.write("Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
                + "Nullam nec fermentum magna. Integer nec malesuada turpis. "
                + "Nunc fringilla lectus vitae dapibus vestibulum. "
                + "Sed luctus tempor tincidunt. Fusce congue viverra justo sit amet fringilla. "
                + "Sed ac tellus semper, volutpat metus non, fringilla libero. "
                + "Praesent non sapien nec dui vestibulum rhoncus. "
                + "Nam et risus at mauris tempor vehicula. "
                + "Donec laoreet eleifend convallis. Nam eget leo in dui auctor ullamcorper.");
//        logComponent.write("Another log message.");

        // Stop the LogComponent immediately (or set true to wait for completion)
        logComponent.stop(true);

        System.out.println("Log component stopped successfully.");
    }
}