package br.com.compass.utils;

public class ValidationUtils {

    // Metodo para validar CPF

    public static boolean validarCPF(String cpf) {
        // Remove caracteres especiais
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) digito1 = 0;

        // Verifica primeiro dígito
        if (digito1 != (cpf.charAt(9) - '0')) {
            return false;
        }

        // Calcula segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) digito2 = 0;

        // Verifica segundo dígito
        return digito2 == (cpf.charAt(10) - '0');
    }

    public static boolean validarTelefone(String telefone) {
        // Remove caracteres especiais
        telefone = telefone.replaceAll("[^0-9]", "");

        // Verifica se o telefone tem entre 10 (fixo) e 11 (celular) dígitos
        if (telefone.length() < 10 || telefone.length() > 11) {
            return false;
        }

        // Se for celular (11 dígitos), verifica se começa com 9
        if (telefone.length() == 11 && telefone.charAt(2) != '9') {
            return false;
        }

        // Verifica DDD válido (11 a 99)
        int ddd = Integer.parseInt(telefone.substring(0, 2));
        if (ddd < 11 || ddd > 99) {
            return false;
        }

        return true;
    }

    public static boolean validarSenha(String senha) {
        // Verifica se a senha tem pelo menos 8 caracteres
        return senha != null && senha.length() >= 8;
    }

    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formatarTelefone(String telefone) {
        telefone = telefone.replaceAll("[^0-9]", "");
        if (telefone.length() == 11) {
            return telefone.replaceAll("(\\d{2})(\\d{5})(\\d{4})", "($1) $2-$3");
        } else {
            return telefone.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
        }
    }
}