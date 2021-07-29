package fr.whyt.pubg.deprecated.parser.data;

import fr.whyt.pubg.deprecated.parser.IniFile;
import fr.whyt.pubg.deprecated.parser.ParserCIS;
import fr.whyt.pubg.deprecated.parser.exceptions.ParsingException;
import fr.whyt.pubg.deprecated.parser.exceptions.WritingException;
import fr.whyt.pubg.utils.StringUtils;

import java.util.Objects;
import java.util.regex.Matcher;

public class CustomInputSettings implements IniFile<CustomInputSettings> {
    
    public final ActionKeyList actionKeyList;
    public final AxisKeyList axisKeyList;

    public CustomInputSettings(final ActionKeyList actionKeyList,
                               final AxisKeyList axisKeyList) {
        this.actionKeyList = actionKeyList;
        this.axisKeyList = axisKeyList;
    }
    
    public CustomInputSettings(final ActionKeyList actionKeyList) {
        this(actionKeyList, new AxisKeyList());
    }
    
    public CustomInputSettings(final AxisKeyList axisKeyList) {
        this(new ActionKeyList(), axisKeyList);
    }
    
    public CustomInputSettings() {
        this(new ActionKeyList(), new AxisKeyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomInputSettings)) return false;

        final CustomInputSettings customInputSettings = (CustomInputSettings) o;
        
        return Objects.deepEquals(actionKeyList, customInputSettings.actionKeyList)
               && Objects.deepEquals(axisKeyList, customInputSettings.axisKeyList);
    }

    @Override
    public int hashCode() {
        int result = actionKeyList.hashCode();
        result = 31 * result + axisKeyList.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CustomInputSettings { " + "actionKeyList=" + actionKeyList + ", axisKeyList=" + axisKeyList + " }";
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public CustomInputSettings deserialize(final String source) throws ParsingException {
        final Matcher m_customInputSettings = ParserCIS.p_customInputSettingsNamed.matcher(source);
        if (!m_customInputSettings.matches()) {
            throw new ParsingException("The CustomInputSettings does not match with the expected format!", source, ParserCIS.p_customInputSettingsNamed);
        }
        final String s_customInputSettings = m_customInputSettings.group("customInputSettings");
        
        final Matcher m_actionKeyListNamed = ParserCIS.p_actionKeyListNamed.matcher(s_customInputSettings);
        m_actionKeyListNamed.find();
        final String s_actionKeyList = m_actionKeyListNamed.group();
        final ActionKeyList actionKeyList = IniFile.deserialize(ActionKeyList.class, s_actionKeyList);
    
        final Matcher m_axisKeyListNamed = ParserCIS.p_axisKeyListNamed.matcher(s_customInputSettings);
        m_axisKeyListNamed.find();
        final String s_axisKeyList = m_axisKeyListNamed.group();
        final AxisKeyList axisKeyList = IniFile.deserialize(AxisKeyList.class, s_axisKeyList);
    
        return new CustomInputSettings(actionKeyList, axisKeyList);
    }
    
    @Override
    public String serialize() throws WritingException {
        // note: the missing 'g' for settings is expected
        return "CustomInputSettins=("
                   + StringUtils.joinComma(actionKeyList.serialize(), axisKeyList.serialize())
               + ")";
    }
}
