package org.example.LogComponent;

public interface ILog {
    void write(String message);
    void stop(boolean waitForCompletion);
}
