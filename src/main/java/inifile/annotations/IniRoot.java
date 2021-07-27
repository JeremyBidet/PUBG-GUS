package fr.whyt.pubg.inifile.annotations;

import java.lang.annotation.*;

/**
 * This annotation define a Java class as the INI root.<br>
 * <br>
 * This means that every properties from this class will be serialized on different lines.<br>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface IniRoot {
}
