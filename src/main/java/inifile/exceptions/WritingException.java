package fr.whyt.pubg.inifile.exceptions;

import java.io.Serializable;

public class WritingException extends Exception {
    
    public final Serializable serializable;
    
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param serializable the serializable class
     */
    public WritingException(final String message, final Serializable serializable) {
        super(message);
        this.serializable = serializable;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nWritingException {" + "\n\tclass=" + serializable + "\n}";
    }
    
}
