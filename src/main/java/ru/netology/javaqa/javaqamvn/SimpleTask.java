package ru.netology.javaqa.javaqamvn;

public class SimpleTask extends Task {

    protected String title;

    public SimpleTask(int id, String title) {
        super(id);
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean matches(String query) {
        return title.contains(query);
    }
}
