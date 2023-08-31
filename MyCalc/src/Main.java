import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input:");
        String sOriginal = new Scanner(System.in).nextLine();

        int operatorIndex;
        String operator;
        if (sOriginal.contains("+")) {
            operatorIndex = sOriginal.indexOf("+");
            operator = "+";
        } else if (sOriginal.contains("-")) {
            operatorIndex = sOriginal.indexOf("-");
            operator = "-";
        } else if (sOriginal.contains("*")) {
            operatorIndex = sOriginal.indexOf("*");
            operator = "*";
        } else {
            operatorIndex = sOriginal.indexOf("/");
            operator = "/";
        }

        Pattern patternArabic = Pattern.compile("^\\d{1,2}\\s*[-+*/]\\s*\\d{1,2}$");
        Matcher matcherArabic = patternArabic.matcher(sOriginal);

        Pattern patternRoman = Pattern.compile("^I{0,3}V?I{0,3}X?\\s*[-+*/]\\s*I{0,3}V?I{0,3}X?$");
        Matcher matcherRoman = patternRoman.matcher(sOriginal);

        System.out.println("Output:");

        if (matcherArabic.find()) {
            int operand1 = Integer.parseInt(sOriginal.substring(0, operatorIndex).replace(" ", ""));
            int operand2 = Integer.parseInt(sOriginal.substring(operatorIndex + 1).replace(" ", ""));
            System.out.println(result(operand1, operand2, operator));
        } else if (matcherRoman.find()) {
            String operand1Str = sOriginal.substring(0, operatorIndex).replace(" ", "");
            String operand2Str = sOriginal.substring(operatorIndex + 1).replace(" ", "");
            int operand1 = RomanNumerals.valueOf(operand1Str).getArabicValue();
            int operand2 = RomanNumerals.valueOf(operand2Str).getArabicValue();
            int result = result(operand1, operand2, operator);
            System.out.println(toRoman(result));
        } else System.out.println("Введено не корректное выражение");
    }

    private static int result (int operand1, int operand2, String operator){
        int result = 0;
        if (operand1 <= 10 && operand2 <= 10) {
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    result = operand1 / operand2;
                    break;
            }
        } else System.out.println("Введено не корректное выражение");
        return result;
    }

    private static String toRoman (int arabic){
        String roman = "";
        if (arabic == 100){
            roman = "C";
        } else {
            if (arabic >= 50 && arabic < 100) {
                int countX = (arabic - 50) / 10;
                if (countX == 4) {
                    roman = "XC";
                } else {
                    roman = "L" + "X".repeat(countX);
                }
            } else {
                int countX = arabic / 10;
                if (countX == 4) {
                    roman = "XL";
                } else {
                    roman = "X".repeat(countX);
                }
            }
            int countI = arabic % 10;
            if (countI >= 5) {
                if (countI == 9) {
                    roman = roman + "IX";
                } else {
                    roman = roman + "V" + "I".repeat(countI - 5);
                }
            } else if (countI == 4) {
                roman = roman + "IV";
            } else {
                if (arabic <= 0) {
                    System.out.println("Введено не корректное выражение");
                } else {
                    roman = roman + "I".repeat(countI);
                }
            }
        }
        return roman;
    }
}