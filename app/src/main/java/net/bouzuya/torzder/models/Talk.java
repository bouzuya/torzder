package net.bouzuya.torzder.models;

public class Talk {
    public int id;
    public String title;
    public String speaker;
    public String key;
    public String file;
    public int offset; // introDuration + adDuration

    @Override
    public String toString() {
        return "Talk{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", speaker='" + speaker + '\'' +
                '}';
    }
}
