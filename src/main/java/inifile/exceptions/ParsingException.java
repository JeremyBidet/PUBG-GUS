package fr.whyt.pubg.inifile.exceptions;

public class ParsingException extends Exception {

    public final String source;
    public final String pattern;
    
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param source the source that triggered the exception
     * @param pattern the pattern that does not match with the source
     */
    public ParsingException(final String message, final String source, final String pattern) {
        super(message);
        this.source = source;
        this.pattern = pattern;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nParsingException {" + "\n\tsource='" + source + "\'" + "\n\tpattern=" + pattern + "\n}";
    }
    
}
