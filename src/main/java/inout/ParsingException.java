package fr.whyt.pubg.inout;

import java.util.regex.Pattern;

public class ParsingException extends Exception {

    private String source;
    private Pattern pattern;
    
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public ParsingException(String message, String source, Pattern pattern) {
        super(message);
        this.source = source;
        this.pattern = pattern;
    }
    
    public String getSource() {
        return source;
    }
    
    public Pattern getPattern() {
        return pattern;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nParsingException{" + "\n\tsource='" + source + "\'" + "\n\tpattern=" + pattern + "\n}";
    }
    
}
