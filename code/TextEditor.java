import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class TextEditor {
    private final Deque<String> undoStack = new ArrayDeque<>();
    private final Deque<String> redoStack = new ArrayDeque<>();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new TextEditor().run();
    }

    private void run() {
        int choice;
        do {
            showMenu();
            choice = readInt();
            switch (choice) {
                case 1 -> addLine();
                case 2 -> undo();
                case 3 -> redo();
                case 4 -> showCurrent();
                case 5 -> exitMessage();
                default -> System.out.println("Opción inválida. Introduce un número entre 1 y 5.");
            }
            System.out.println();
        } while (choice != 5);
        scanner.close();
    }

    private void showMenu() {
        System.out.println("=== Editor de Texto (Undo/Redo) ===");
        System.out.println("1 - Añadir línea");
        System.out.println("2 - Deshacer (undo)");
        System.out.println("3 - Rehacer (redo)");
        System.out.println("4 - Mostrar línea actual (peek)");
        System.out.println("5 - Salir");
        System.out.print("Selecciona una opción: ");
    }

    private int readInt() {
        if (scanner.hasNextInt()) {
            int val = scanner.nextInt();
            scanner.nextLine(); // limpiar
            return val;
        } else {
            scanner.nextLine();
            return -1;
        }
    }

    private void addLine() {
        System.out.print("Escribe la línea a añadir: ");
        String line = scanner.nextLine();
        undoStack.push(line);
        redoStack.clear(); // nueva acción invalida el redo previo
        System.out.println("Línea añadida.");
    }

    private void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("Nada que deshacer.");
            return;
        }
        String removed = undoStack.pop();
        redoStack.push(removed);
        System.out.println("Deshecho: \"" + removed + "\"");
    }

    private void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nada que rehacer.");
            return;
        }
        String restored = redoStack.pop();
        undoStack.push(restored);
        System.out.println("Rehecho: \"" + restored + "\"");
    }

    private void showCurrent() {
        if (undoStack.isEmpty()) {
            System.out.println("Documento vacío.");
        } else {
            System.out.println("Línea en la cima (última añadida): \"" + undoStack.peek() + "\"");
        }
    }

    private void exitMessage() {
        System.out.println("Cerrando editor. Gracias por usarlo.");
    }
}
