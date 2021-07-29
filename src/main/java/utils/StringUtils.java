package fr.whyt.pubg.utils;

public class StringUtils {
    
    /**
     * Check if the given string is null, blank (empty ignoring tailing spaces).<br>
     *
     * @param str the string to check
     * @return true if null or blank, false otherwise.
     */
    public static boolean isBlank(final String str) {
        return str == null || isEmpty(str.trim());
    }
    
    /**
     * Check if the given string is null or empty.<br>
     *
     * @param str the string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isEmpty(final String str) {
        return str == null || str.isEmpty();
    }
    
    public static String removeEnclosingQuotes(final String str) {
        if (str == null) {
            return null;
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
