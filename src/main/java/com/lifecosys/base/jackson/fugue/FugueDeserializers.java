package com.lifecosys.base.jackson.fugue;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.lifecosys.base.jackson.fugue.option.OptionDeserializer;
import io.atlassian.fugue.Option;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class FugueDeserializers extends Deserializers.Base {
    @Override // since 2.7
    public JsonDeserializer<?> findReferenceDeserializer(ReferenceType refType, DeserializationConfig config,
                                                         BeanDescription beanDesc, TypeDeserializer contentTypeDeserializer,
                                                         JsonDeserializer<?> contentDeserializer) {
        if (refType.hasRawClass(Option.class)) {
            return new OptionDeserializer(refType, null, contentTypeDeserializer, contentDeserializer);
        }
        return null;
    }
}
