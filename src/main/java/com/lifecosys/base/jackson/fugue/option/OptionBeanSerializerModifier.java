package com.lifecosys.base.jackson.fugue.option;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import io.atlassian.fugue.Option;

import java.util.*;

/**
 * {@link BeanSerializerModifier} needed to sneak in handler to exclude "absent"
 * optional values iff handling of "absent as nulls" is enabled.
 */
public class OptionBeanSerializerModifier extends BeanSerializerModifier
{
    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties)
    {
        for (int i = 0; i < beanProperties.size(); ++i) {
            final BeanPropertyWriter writer = beanProperties.get(i);
            JavaType type = writer.getType();

            Object empty;
            if (type.isTypeOrSubTypeOf(Option.class)) {
                empty = Option.none();
            }  else {
                continue;
            }
            beanProperties.set(i, new OptionBeanPropertyWriter(writer, empty));
        }
        return beanProperties;
    }
}
