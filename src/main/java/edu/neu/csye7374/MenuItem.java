package edu.neu.csye7374;

public class MenuItem extends MenuComponent {
    private final Runnable action;

    public MenuItem(String name, Runnable action) {
        super(name);
        this.action = action;
    }

    @Override
    public void display() {
        System.out.println("- " + name);
    }

    @Override
    public void execute() {
        action.run();
    }
}
