package g60283.dev.ascii.controller;

import g60283.dev.ascii.model.AsciiPaint;
import g60283.dev.ascii.view.View;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsciiController {

    private AsciiPaint paint;
    private View view;
    private boolean running;

    public AsciiController() {
        this.paint = new AsciiPaint(40, 20); // Taille par défaut
        this.view = new View();
        this.running = true;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.println("Entrez une commande :");
            String input = scanner.nextLine();
            parseCommand(input);
        }

        scanner.close();
    }

    private void parseCommand(String input) {
        if (input.matches("add circle \\d+ \\d+ \\d+ [a-zA-Z]")) {
            handleAddCircle(input);
        } else if (input.matches("add rectangle \\d+ \\d+ \\d+ \\d+ [a-zA-Z]")) {
            handleAddRectangle(input);
        } else if (input.equals("show")) {
            handleShow();
        } else if (input.equals("list")) {
            handleList();
        } else if (input.matches("move \\d+ \\d+ \\d+")) {
            handleMoveShape(input);
        } else if (input.matches("color \\d+ [a-zA-Z]")) {
            handleSetColor(input);
        } else if (input.matches("delete \\d+")) {
            handleRemoveShape(input);
        } else if (input.equals("quit")) {
            running = false;
            System.out.println("Au revoir!");
        } else {
            System.out.println("Commande inconnue !");
        }
    }

    private void handleAddCircle(String input) {
        Pattern pattern = Pattern.compile("add circle (\\d+) (\\d+) (\\d+) ([a-zA-Z])");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            double x = Double.parseDouble(matcher.group(1));
            double y = Double.parseDouble(matcher.group(2));
            double radius = Double.parseDouble(matcher.group(3));
            char color = matcher.group(4).charAt(0);
            paint.addCircle(x, y, radius, color);
            System.out.println("Cercle ajouté.");
        }
    }

    private void handleAddRectangle(String input) {
        Pattern pattern = Pattern.compile("add rectangle (\\d+) (\\d+) (\\d+) (\\d+) ([a-zA-Z])");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            double x = Double.parseDouble(matcher.group(1));
            double y = Double.parseDouble(matcher.group(2));
            double width = Double.parseDouble(matcher.group(3));
            double height = Double.parseDouble(matcher.group(4));
            char color = matcher.group(5).charAt(0);
            paint.addRectangle(x, y, width, height, color);
            System.out.println("Rectangle ajouté.");
        }
    }

    private void handleShow() {
        view.display(paint);
    }

    private void handleList() {
        System.out.println("Liste des formes :");
        view.list(paint);
    }


    private void handleMoveShape(String input) {
        Pattern pattern = Pattern.compile("move (\\d+) (\\d+) (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            int index = Integer.parseInt(matcher.group(1));
            double dx = Double.parseDouble(matcher.group(2));
            double dy = Double.parseDouble(matcher.group(3));
            paint.moveShape(index, dx, dy);
            System.out.println("Forme déplacée.");
        }
    }

    private void handleSetColor(String input) {
        Pattern pattern = Pattern.compile("color (\\d+) ([a-zA-Z])");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            int index = Integer.parseInt(matcher.group(1));
            char color = matcher.group(2).charAt(0);
            paint.setColor(index, color);
            System.out.println("Couleur modifiée.");
        }
    }

    private void handleRemoveShape(String input) {
        Pattern pattern = Pattern.compile("delete (\\d+)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            int index = Integer.parseInt(matcher.group(1));
            paint.removeShape(index);
            System.out.println("Forme supprimée.");
        }
    }
}
