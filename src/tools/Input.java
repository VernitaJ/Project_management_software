package tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    public static final String GREEN = "\u001B[32m";
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
            System.out.print(str);
            try {
                userInput = scanner.nextInt();
                complianceCheck = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input provided. Please type a number.");
            }
            scanner.nextLine();
        }
        return userInput;
    }

    public float getFloat(String str)
    {
        float userInput = 0;
        try {
            System.out.print(str);
            userInput = scanner.nextFloat();
        } catch (InputMismatchException e){
            scanner.nextLine();
            return getFloat("Wrong format, please try again: ");
        }

        return userInput;
    }

    public double getDouble(String str)
    {
        double userInput = 0.0;
        try {
            System.out.print(str);
            userInput = scanner.nextDouble();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return getDouble("Wrong format, please try again: ");
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
            } catch (InputMismatchException | DateTimeParseException e) {
                System.out.println("Wrong date format, please try again: ");
            }
        }
        return date;
    }

    public boolean abort(String input) {
        if(input.equals("0")) {
            return true;
        }
        return false;
    }

    public void checkToContinue() {
        String check = ">> Press" + RED + " 'Enter' " + RESET + "to continue: ";
        System.out.print(check);
        scanner.nextLine();
        scanner.reset();
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void spacer(){
        System.out.println("______________\n");
    }
}
