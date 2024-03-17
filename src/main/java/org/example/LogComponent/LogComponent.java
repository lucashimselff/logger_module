package org.example.LogComponent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LogComponent implements ILog {
    private BufferedWriter writer;
    private LocalDate lastDate;
    private boolean stopped;

    public LogComponent() {
        try {
            this.writer = new BufferedWriter(new FileWriter(getLogFileName()));
            this.lastDate = LocalDate.now();
            this.stopped = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void write(String message) {
        new Thread(() -> {
            try {
                synchronized (this) {
                    if (!stopped) {
                        if (LocalDate.now().isAfter(lastDate)) {
                            writer.close();
                            writer = new BufferedWriter(new FileWriter(getLogFileName()));
                            lastDate = LocalDate.now();
                        }
                        writer.write(message);
                        writer.newLine();
                        writer.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void stop(boolean waitForCompletion) {
        if(!waitForCompletion){
            try {
                writer.close(); // Close the file writer
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stopped = true;
        }
        try {
            Thread.sleep(100); // wait until write method finishes
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            synchronized (this) {
                writer.flush();
                writer.close(); // Close the file writer
                stopped = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogFileName() {
        return "log_" + LocalDate.now() + ".txt";
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public boolean isStopped() {
        return stopped;
    }
}
