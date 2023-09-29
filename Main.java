import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Select language: \n 1. English \n 2. suomi");
        Scanner s = new Scanner(System.in);
        int lang = s.nextInt();
        int numRows = 10, numCols = 10;
        if (lang == 1) {
            System.out.print("Enter number of rows: ");
            numRows = s.nextInt();
            System.out.print("Enter number of cols: ");
            numCols = s.nextInt();
        } else if (lang == 2) {
            System.out.print("Valitse rivien määrä: ");
            numRows = s.nextInt();
            System.out.print("Valitse sarakkeiden määrä: ");
            numCols = s.nextInt();
        }
        Grid g = new Grid(numRows, numCols, lang);
    }
}