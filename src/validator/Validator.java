package validator;

import java.util.Date;

public class Validator {
    public static Object requireNotNull(Object o) {
        if (o == null) {
            throw new NullPointerException("L'input è null");
        }
        return o;
    }

    public static String requireNotBlank(String s) {
        if (s == null || s.isBlank()) {
            throw new NullPointerException("La stringa è null o blank");
        }
        return s;
    }

    public static String validRegex(String toValidate, String regex) {
        if (toValidate == null || toValidate.isBlank()) {
            throw new NullPointerException("L'input è null");
        }
        if (!toValidate.matches(regex)) {
            throw new IllegalArgumentException("Input non valido, non rispetta la regex!");
        }
        return toValidate;
    }

    public static Double requirePositive(Double n) {
        if (n == null) {
            throw new NullPointerException("L'input è null");
        }
        if (n < 0) {
            throw new IllegalArgumentException("L'input deve essere positivo!");
        }
        return n;
    }

    public static int requirePositiveInt(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("L'input deve essere positivo!");
        }
        return n;
    }

    public static Integer requireGreaterThenZero(Integer n) {
        if (n == null) {
            throw new NullPointerException("L'input è null");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("L'input deve essere positivo!");
        }
        return n;
    }

    public static Double requireGreaterThenZero(Double n) {
        if (n == null) {
            throw new NullPointerException("L'input è null");
        }
        if (n <= 0) {
            throw new IllegalArgumentException("L'input deve essere positivo!");
        }
        return n;
    }

    public static Integer requireBetween(Integer a) {
        if (a == null) {
            throw new NullPointerException("L'input è null");
        }
        if (a < 1 || a > 4) {
            throw new IllegalArgumentException("Puoi prendere massimo 4 posti e almeno 1");
        }
        return a;
    }

    public static Date requireDateBefore(Date orario, Date from) {
    }
}
