package br.com.tgid.TgidTestJavaDeveloper.utils;

public class CNPJUtil {

    private static final int CNPJ_LENGTH = 14;

    public static String cleaning(String raw) {
        return raw.replaceAll("[^0-9]", "");
    }

    // fonte: https://guj.com.br/t/validar-cnpj/129994/6
    // creditos ao @brunocechet o metodo dele funcionou corretamente.
    public static boolean validate(String cnpj) {
        try {
            cnpj = cnpj.replace('.', ' ');//onde há ponto coloca espaço
            cnpj = cnpj.replace('/', ' ');//onde há barra coloca espaço
            cnpj = cnpj.replace('-', ' ');//onde há traço coloca espaço
            cnpj = cnpj.replaceAll(" ", "");//retira espaço
            int soma = 0, dig;
            String cnpj_calc = cnpj.substring(0, 12);

            if (cnpj.length() != 14) {
                return false;
            }
            char[] chr_cnpj = cnpj.toCharArray();
            /* Primeira parte */
            for (int i = 0; i < 4; i++) {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                    soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9) {
                    soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                    dig);
            /* Segunda parte */
            soma = 0;
            for (int i = 0; i < 5; i++) {
                if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9) {
                    soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
                }
            }
            for (int i = 0; i < 8; i++) {
                if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9) {
                    soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
                }
            }
            dig = 11 - (soma % 11);
            cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(
                    dig);
            return cnpj.equals(cnpj_calc);
        } catch (Exception e) {
            return false;
        }
    }

}
