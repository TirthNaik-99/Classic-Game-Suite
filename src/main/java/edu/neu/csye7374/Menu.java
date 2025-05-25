package edu.neu.csye7374;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu extends MenuComponent {
    private final List<MenuComponent> menuComponents = new ArrayList<>();

    public Menu(String name) {
        super(name);
    }

    public void add(MenuComponent menuComponent) {
        menuComponents.add(menuComponent);
    }

    @Override
    public void display() {
        System.out.println("\n=== " + name + " ===");
        for (int i = 0; i < menuComponents.size(); i++) {
            System.out.print((i + 1) + ". ");
            menuComponents.get(i).display();
        }
    }

    @Override
    public void execute() {
        display();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter selection: ");
        int selection = scanner.nextInt();
        if (selection > 0 && selection <= menuComponents.size()) {
            menuComponents.get(selection - 1).execute();
        }
    }
}
