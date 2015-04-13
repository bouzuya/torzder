package net.bouzuya.torzder.models;

public class Caption {
    public int startTime;
    public boolean startOfParagraph;
    public String content;
    public int duration;

    public String getStartTimeString() {
        int minutes = startTime / 1000 / 60; // minutes
        int seconds = startTime / 1000 % 60; // seconds
        return String.format("%02d:%02d", minutes, seconds);
    }
}
