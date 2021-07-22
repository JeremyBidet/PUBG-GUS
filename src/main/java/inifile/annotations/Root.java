package fr.whyt.pubg.inifile.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation define a Java class as the INI root.<br>
 * <br>
 * This means that every properties from this class will be serialized on different lines.<br>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Root {
}
