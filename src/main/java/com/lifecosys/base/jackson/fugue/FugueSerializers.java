package com.lifecosys.base.jackson.fugue;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.lifecosys.base.jackson.fugue.option.OptionSerializer;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Option;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class FugueSerializers extends Serializers.Base {
    @Override
    public JsonSerializer<?> findReferenceSerializer(SerializationConfig config, ReferenceType refType,
                                                     BeanDescription beanDesc, TypeSerializer contentTypeSerializer,
                                                     JsonSerializer<Object> contentValueSerializer) {
        boolean staticTyping =
                (contentTypeSerializer == null) && config.isEnabled(MapperFeature.USE_STATIC_TYPING);

        final Class<?> raw = refType.getRawClass();
        if (Option.class.isAssignableFrom(raw)) {
            return new OptionSerializer(refType, staticTyping, contentTypeSerializer, contentValueSerializer);
        }

        if (Either.class.isAssignableFrom(raw)) {
            return new EitherSerializer(refType, staticTyping, contentTypeSerializer, contentValueSerializer);
        }

        return null;
    }
}
