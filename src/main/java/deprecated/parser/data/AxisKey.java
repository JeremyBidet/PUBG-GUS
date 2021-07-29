package fr.whyt.pubg.deprecated.parser.data;

import fr.whyt.pubg.deprecated.parser.IniFile;
import fr.whyt.pubg.deprecated.parser.ParserCIS;
import fr.whyt.pubg.deprecated.parser.exceptions.ParsingException;
import fr.whyt.pubg.deprecated.parser.exceptions.WritingException;
import fr.whyt.pubg.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

public class AxisKey implements IniFile<AxisKey> {

    public final String axisName;
    public final double scale;
    public final List<Key> keyList;

    public AxisKey(final String axisName, final double scale, final List<Key> keyList) {
        this.axisName = axisName;
        this.scale = scale;
        this.keyList = Objects.requireNonNull(keyList);
    }
    
    public AxisKey(final String axisName, final double scale) {
        this(axisName, scale, new ArrayList<>());
    }
    
    public AxisKey() {
        this(null, 0.0, new ArrayList<>());
    }
    
    @Override
    public int hashCode() {
        return axisName.hashCode();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof AxisKey)) return false;

        final AxisKey axisKey = (AxisKey) o;

        return Objects.equals(axisName, axisKey.axisName);
    }

    @Override
    public String toString() {
        return "AxisKey { axisName=" + axisName + ", scale=" + scale + ", keys=" + keyList + " }";
    }
    
    @Override
    public AxisKey deserialize(final String source) throws ParsingException {
        final Matcher m_axisKey = ParserCIS.p_axisKeyNamed.matcher(source);
        if (!m_axisKey.matches()) {
            throw new ParsingException("The AxisKey does not match with the expected format!", source, ParserCIS.p_axisKeyNamed);
        }
        final String axisName = m_axisKey.group("axisName");
        final double scale = Double.parseDouble(m_axisKey.group("scale"));
        final String s_keys = m_axisKey.group("keys");
        
        final AxisKey axisKey = new AxisKey(axisName, scale);
        
        final Matcher m_key = ParserCIS.p_keyNamed.matcher(s_keys);
        while (m_key.find()) {
            final Key key = IniFile.deserialize(Key.class, m_key.group());
            axisKey.keyList.add(key);
        }
        
        return axisKey;
    }
    
    @Override
    public String serialize() throws WritingException {
        if (StringUtils.isEmpty(axisName)) {
            throw new WritingException("The ActionName key cannot be null or empty", this);
        }
        return "("
                   + "AxisName=\"" + axisName + "\","
                   + "Scale=" + scale + ","
                   + "Keys=(" +
                       keyList.stream()
                               .map(ak -> {
                                   try {
                                       return ak.serialize();
                                   } catch (WritingException e) {
                                       e.printStackTrace();
                                       return null;
                                   }})
                               .reduce(StringUtils::joinComma)
                               .orElse("")
                   + ")"
               + ")";
    }

}
