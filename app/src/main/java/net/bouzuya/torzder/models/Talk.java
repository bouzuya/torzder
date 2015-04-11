package net.bouzuya.torzder.models;

public class Talk {
    public int id;
    public String title;
    public String speaker;

    @Override
    public String toString() {
        return "Talk{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", speaker='" + speaker + '\'' +
                '}';
    }
}
