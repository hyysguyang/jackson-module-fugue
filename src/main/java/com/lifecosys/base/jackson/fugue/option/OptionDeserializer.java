package com.lifecosys.base.jackson.fugue.option;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.fasterxml.jackson.databind.deser.std.ReferenceTypeDeserializer;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import io.atlassian.fugue.Option;

/**
 * @author <a href="mailto:hyysguyang@gmail.com">Young Gu</a>
 */
public class OptionDeserializer extends ReferenceTypeDeserializer<Option<?>> {
    private static final long serialVersionUID = 1L;

    public OptionDeserializer(JavaType fullType, ValueInstantiator inst, TypeDeserializer typeDeser,
                              JsonDeserializer<?> deser) {
        super(fullType, inst, typeDeser, deser);
    }

    /*
    /**********************************************************
    /* Abstract method implementations
    /**********************************************************
     */

    @Override
    public OptionDeserializer withResolved(TypeDeserializer typeDeser, JsonDeserializer<?> valueDeser) {
        return new OptionDeserializer(_fullType, _valueInstantiator, typeDeser, valueDeser);
    }

    @Override
    public Option<?> getNullValue(DeserializationContext ctxt) {
        return Option.none();
    }

    @Override
    public Option<?> referenceValue(Object contents) {
        return Option.option(contents);
    }

    @Override
    public Object getReferenced(Option<?> reference) {
        return reference.get();
    }

    @Override
    public Option<?> updateReference(Option<?> reference, Object contents) {
        return Option.option(contents);
    }

}
