package fr.whyt.pubg.inout;

import fr.whyt.pubg.inout.cis.CustomInputSettings;

public class WritingException extends Exception {
    
    private CustomInputSettings cis;
    
    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public WritingException(String message, CustomInputSettings cis) {
        super(message);
        this.cis = cis;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nWritingException{" + "\n\tcis=" + cis + "\n}";
    }
}
