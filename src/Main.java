import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
  public static double taylorSinus(double x, int n) {
    double result = 0;
    for (int i = 0; i < n; i++) {
      result += Math.pow(-1, i) * Math.pow(x, 2 * i + 1) / factorial(2 * i + 1);
    }
    return result;
  }

  public static int factorial(int n) {
    if (n == 0 || n == 1) return 1;
    return n * factorial(n - 1);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Gib die Genauigkeit (n) der Berechnung an: ");
    int n = scanner.nextInt();

    List<String[]> data = new ArrayList<>();

    try (PrintWriter writer = new PrintWriter(new File("Taylor.csv"))) {
      writer.write("Wert von x,Ergebnis mit eigener Methode,Ergebnis mit Java-Methode,Differenz\n");

      for (double x = 0; x <= Math.PI * 2; x += Math.PI / 10) {
        double taylorResult = taylorSinus(x, n);
        double javaResult = Math.sin(x);
        double differenz = Math.abs(taylorResult - javaResult);

        String[] row = {String.valueOf(x), String.valueOf(taylorResult), String.valueOf(javaResult), String.valueOf(differenz)};
        data.add(row);

        writer.write(String.join(",", row) + "\n");
      }

      System.out.println("CSV-Datei erstellt: Taylor.csv");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    double maxDifferenz = data.stream().mapToDouble(row -> Double.parseDouble(row[3])).max().orElse(0);
    double minDifferenz = data.stream().mapToDouble(row -> Double.parseDouble(row[3])).min().orElse(0);
    double durchschnittlicheDifferenz = data.stream().mapToDouble(row -> Double.parseDouble(row[3])).average().orElse(0);

    System.out.println("Maximale Abweichung: " + maxDifferenz);
    System.out.println("Minimale Abweichung: " + minDifferenz);
    System.out.println("Durchschnittliche Abweichung: " + durchschnittlicheDifferenz);
  }
}
