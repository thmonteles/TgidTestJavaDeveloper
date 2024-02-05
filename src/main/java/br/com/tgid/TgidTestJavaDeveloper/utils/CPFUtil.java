package br.com.tgid.TgidTestJavaDeveloper.utils;

public class CPFUtil {
    public static String cleaning(String raw) {
        return raw.replaceAll("[^0-9]", "");
    }

    public static boolean validate(String cpfWithoutInvalidCharacters) {
        var invalidCpfSize = cpfWithoutInvalidCharacters.length() != 11;
        if (invalidCpfSize) {
            return false;
        }

        var checkIfAllDigitsIsEquals = cpfWithoutInvalidCharacters.matches("(\\d)\\1{10}");
        if (checkIfAllDigitsIsEquals) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpfWithoutInvalidCharacters.charAt(i)) * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) {
            firstDigit = 0;
        }

        if (Character.getNumericValue(cpfWithoutInvalidCharacters.charAt(9)) != firstDigit) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpfWithoutInvalidCharacters.charAt(i)) * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) {
            secondDigit = 0;
        }

        return Character.getNumericValue(cpfWithoutInvalidCharacters.charAt(10)) == secondDigit;
    }
}
