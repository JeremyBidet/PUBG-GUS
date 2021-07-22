package fr.whyt.pubg.inifile.exceptions;

import fr.whyt.pubg.deprecated.parser.IniFile;

public class WritingException extends Exception {
    
    public final IniFile<? extends IniFile> serializable;
    
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param serializable the serializable class (implementing {@link IniFile})
     */
    public WritingException(final String message, final IniFile<? extends IniFile> serializable) {
        super(message);
        this.serializable = serializable;
    }
    
    @Override
    public String toString() {
        return super.toString() + "\nWritingException {" + "\n\tclass=" + serializable + "\n}";
    }
}
