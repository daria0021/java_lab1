package matrix.app;

import java.util.Scanner;

public class MatrixApp {
    private static boolean running = true;
    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("Matrix size: ");
        int n = in.nextInt();

        ComplexMatrix m1 = new ComplexMatrix(n);
        ComplexMatrix m2 = new ComplexMatrix(n);

        pause();
        wipe();

        while (running) {
            try {
                menu(m1, m2);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
            pause();
            wipe();
        }
    }

    private static void pause() {
        in.nextLine();
        System.out.println("Press Enter...");
        in.nextLine();
    }

    private static void wipe() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    private static ComplexNumber readComplex() {
        System.out.print("real: ");
        double re = in.nextDouble();
        System.out.print("imag: ");
        double im = in.nextDouble();
        return new ComplexNumber(re, im);
    }

    private static void fillMatrix(ComplexMatrix m) {
        int n = m.getSize();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("cell [" + i + "][" + j + "]");
                m.set(i, j, readComplex());
            }
        }
    }

    private static void editCell(ComplexMatrix m) {
        int n = m.getSize();
        System.out.print("row (0.." + (n - 1) + "): ");
        int i = in.nextInt();
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("Bad row");
        }
        System.out.print("col (0.." + (n - 1) + "): ");
        int j = in.nextInt();
        if (j < 0 || j >= n) {
            throw new IllegalArgumentException("Bad column");
        }
        m.set(i, j, readComplex());
    }

    private static ComplexMatrix pick(ComplexMatrix m1, ComplexMatrix m2) {
        System.out.print("Pick matrix (1 or 2): ");
        int c = in.nextInt();
        if (c == 1) return m1;
        if (c == 2) return m2;
        throw new IllegalArgumentException("No such matrix: " + c);
    }

    private static void menu(ComplexMatrix m1, ComplexMatrix m2) {
        System.out.println("""
                
                === Complex Matrix Tool ===
                1. Fill matrix
                2. Edit cell
                3. Add
                4. Subtract
                5. Multiply
                6. Divide
                7. Transpose
                8. Determinant
                9. Show
                0. Quit
                """);
        System.out.print("Your choice: ");
        int c = in.nextInt();

        switch (c) {
            case 1 -> fillMatrix(pick(m1, m2));
            case 2 -> editCell(pick(m1, m2));
            case 3 -> {
                System.out.println(m1);
                System.out.println("+");
                System.out.println(m2);
                System.out.println("=");
                System.out.println(m1.plus(m2));
            }
            case 4 -> {
                System.out.println(m1);
                System.out.println("-");
                System.out.println(m2);
                System.out.println("=");
                System.out.println(m1.minus(m2));
            }
            case 5 -> {
                System.out.println(m1);
                System.out.println("*");
                System.out.println(m2);
                System.out.println("=");
                System.out.println(m1.times(m2));
            }
            case 6 -> {
                System.out.println(m1);
                System.out.println("/");
                System.out.println(m2);
                System.out.println("=");
                System.out.println(m1.over(m2));
            }
            case 7 -> {
                ComplexMatrix m = pick(m1, m2);
                System.out.println("Transposed:");
                System.out.println(m.transpose());
            }
            case 8 -> {
                ComplexMatrix m = pick(m1, m2);
                System.out.println("Det = " + m.determinant());
            }
            case 9 -> System.out.println(pick(m1, m2));
            case 0 -> running = false;
            default -> System.out.println("Unknown option");
        }
    }
}