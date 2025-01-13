package validator;

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
}
