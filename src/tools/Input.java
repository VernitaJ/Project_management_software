package tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Input
{
    private static final Scanner scanner = new Scanner(System.in);
    private static Input instance = null;

    // Change the color of terminal stuff
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private Input(){}

    public static Input getInstance()
    {
        if (instance == null)
        {
            return new Input();
        }
        else
        {
            return instance;
        }
    }

    // close scanner & set singleton to null for memory purposes.
    public void teardown()
    {
        instance = null;
        scanner.close();
    }

    public String getStr(String str)
    {
        String userInput = "";
        boolean complianceCheck = false;
        while (!complianceCheck) {
            try {
                System.out.print(str);
                userInput = scanner.nextLine();
                complianceCheck = true;
            } catch (InputMismatchException e) {
                e.printStackTrace();
            }
        }
        return userInput;
    }

    public int getInt(String str)
    {
        int userInput = 0;
        boolean complianceCheck = false;
        while (!complianceCheck) {
            try {
                System.out.print(str);
                userInput = scanner.nextInt();
                scanner.nextLine();
                complianceCheck = true;
            } catch (InputMismatchException e){
                e.printStackTrace();
            }
        }
        return userInput;
    }

    public double getDouble(String str)
    {
        double userInput = 0.0;
        boolean complianceCheck = false;
        while (!complianceCheck) {
            try {
                System.out.print(str);
                userInput = scanner.nextDouble();
                complianceCheck = true;
            } catch (InputMismatchException e) {
                e.printStackTrace();
            }
        }
        return userInput;
    }
    
    public LocalDate getDate(String str)
    {
        String userInput = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.now();
        boolean complianceCheck = false;
        while (!complianceCheck) {
            try {
                System.out.println(str);
                userInput = scanner.nextLine();
                date = LocalDate.parse(userInput, formatter);
                complianceCheck = true;
            } catch (InputMismatchException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public void checkToContinue() {
        String check = ">> Press" + RED + " 'Enter' " + RESET + "to continue: ";
        System.out.print(check);
        scanner.nextLine();
        scanner.reset();
    }

}
