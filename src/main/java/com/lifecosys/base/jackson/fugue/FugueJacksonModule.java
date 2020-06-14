package com.lifecosys.base.jackson.fugue;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.lifecosys.base.jackson.fugue.option.OptionBeanSerializerModifier;

/**
 * Fugue Json support.
 * <p>
 * Port from {@link com.fasterxml.jackson.datatype.jdk8.Jdk8Module}
 *
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class FugueJacksonModule extends Module {


    protected boolean _cfgHandleAbsentAsNull = false;

    @Override
    public void setupModule(SetupContext context) {
        context .addSerializers(new FugueSerializers());
        context .addDeserializers(new FugueDeserializers());
        context.addTypeModifier(new FugueTypeModifier());
        if (_cfgHandleAbsentAsNull) {
            context.addBeanSerializerModifier(new OptionBeanSerializerModifier());
        }
    }

    public FugueJacksonModule configureAbsentsAsNulls(boolean state) {
        _cfgHandleAbsentAsNull = state;
        return this;
    }

    @Override
    public String getModuleName() {
        return getClass().getName();
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

}
