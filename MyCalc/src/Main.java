import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        while(true) {
            System.out.println("Введите выражение:");
            String sOriginal = new Scanner(System.in).nextLine();
            if (Objects.equals(sOriginal, "стоп")) {
                System.out.println("программа завершена");
                break;
            }

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

            System.out.println("Результат:");

            if (matcherArabic.find()) {
                int operand1 = Integer.parseInt(sOriginal.substring(0, operatorIndex).replace(" ", ""));
                int operand2 = Integer.parseInt(sOriginal.substring(operatorIndex + 1).replace(" ", ""));
                try {
                    System.out.println(result(operand1, operand2, operator));
                    System.out.println();
                } catch (IOException e) {
                    System.out.println("Введено не корректное выражение");
                    System.out.println();
                }
            } else if (matcherRoman.find()) {
                String operand1Str = sOriginal.substring(0, operatorIndex).replace(" ", "");
                String operand2Str = sOriginal.substring(operatorIndex + 1).replace(" ", "");
                int operand1 = RomanNumerals.valueOf(operand1Str).getArabicValue();
                int operand2 = RomanNumerals.valueOf(operand2Str).getArabicValue();
                int result = 0;
                try {
                    result = result(operand1, operand2, operator);
                } catch (IOException e) {
                    System.out.println("Введено не корректное выражение");
                    System.out.println();
                }
                try {
                    System.out.println(toRoman(result));
                    System.out.println();
                } catch (IOException e) {
                    System.out.println("Введено не корректное выражение");
                    System.out.println();
                }
            } else try {
                throw new IOException();
            } catch (IOException e) {
                System.out.println("Введено не корректное выражение");
                System.out.println();
            }
        }
    }

    private static int result (int operand1, int operand2, String operator) throws IOException {
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
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                        break;
                    } else throw new IOException();
            }
        } else throw new IOException();
        return result;
    }

    private static String toRoman (int arabic) throws IOException {
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
                    throw new IOException();
                } else {
                    roman = roman + "I".repeat(countI);
                }
            }
        }
        return roman;
    }
}