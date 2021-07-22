package fr.whyt.pubg.utils;

public class StringUtils {
    
    public static boolean isEmpty(final String str) {
        return str == null || str.trim().isEmpty();
    }
    
    public static String removeEnclosingQuotes(final String str) {
        if (str == null) {
            return str;
        }
        return str.matches("\".*\"") ? str.substring(1, str.length()-1) : str;
    }

    public static String joinComma(final String... strings) {
        return join(",", strings);
    }

    public static String joinCommaWithSpace(final String... strings) {
        return join(", ", strings);
    }

    public static String joinLN(final String... strings) {
        return join("\n", strings);
    }
    
    private static String join(final String delimiter, final String... strings) {
        if (strings == null) {
            return "";
        }
        return String.join(delimiter, strings);
    }

}
