package br.com.tgid.TgidTestJavaDeveloper.utils;

public class CNPJUtil {

    public static String cleaning(String raw) {
        return raw.replaceAll("[^0-9]", "");
    }

    public static boolean validate(String cnpjWithoutInvalidCharacters) {
        var invalidCnpjSize = cnpjWithoutInvalidCharacters.length() != 14;
        if (invalidCnpjSize) {
            return false;
        }

        var checkIfAllDigitsAreEquals = cnpjWithoutInvalidCharacters.matches("(\\d)\\1{13}");
        if (checkIfAllDigitsAreEquals) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += Character.getNumericValue(cnpjWithoutInvalidCharacters.charAt(i)) * (5 + (i % 8));
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit > 9) {
            firstDigit = 0;
        }

        if (Character.getNumericValue(cnpjWithoutInvalidCharacters.charAt(12)) != firstDigit) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < 13; i++) {
            sum += Character.getNumericValue(cnpjWithoutInvalidCharacters.charAt(i)) * (6 + (i % 8));
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit > 9) {
            secondDigit = 0;
        }

        return Character.getNumericValue(cnpjWithoutInvalidCharacters.charAt(13)) == secondDigit;
    }

}
