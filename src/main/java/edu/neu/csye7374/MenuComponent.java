package edu.neu.csye7374;

public abstract class MenuComponent {
    protected String name;

    public MenuComponent(String name) {
        this.name = name;
    }

    public abstract void display();
    public abstract void execute();
}
