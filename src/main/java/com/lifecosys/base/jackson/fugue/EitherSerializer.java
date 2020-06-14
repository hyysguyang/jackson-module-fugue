package com.lifecosys.base.jackson.fugue;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.ser.std.ReferenceTypeSerializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.util.NameTransformer;
import io.atlassian.fugue.Either;

/**
 * Fugue Option support, copy from {@link com.fasterxml.jackson.datatype.jdk8.OptionalSerializer}.
 */

public class EitherSerializer extends ReferenceTypeSerializer<Either<?, ?>> {
    private static final long serialVersionUID = 1L;

    protected EitherSerializer(ReferenceType fullType, boolean staticTyping, TypeSerializer vts,
                               JsonSerializer<Object> ser) {
        super(fullType, staticTyping, vts, ser);
    }

    protected EitherSerializer(EitherSerializer base, BeanProperty property, TypeSerializer vts,
                               JsonSerializer<?> valueSer, NameTransformer unwrapper, Object suppressableValue,
                               boolean suppressNulls) {
        super(base, property, vts, valueSer, unwrapper, suppressableValue, suppressNulls);
    }

    @Override
    protected ReferenceTypeSerializer<Either<?, ?>> withResolved(BeanProperty prop, TypeSerializer vts,
                                                                 JsonSerializer<?> valueSer, NameTransformer unwrapper) {
        return new EitherSerializer(this, prop, vts, valueSer, unwrapper, _suppressableValue, _suppressNulls);
    }

    @Override
    public ReferenceTypeSerializer<Either<?, ?>> withContentInclusion(Object suppressableValue,
                                                                      boolean suppressNulls) {
        return new EitherSerializer(this, _property, _valueTypeSerializer, _valueSerializer, _unwrapper,
                suppressableValue, suppressNulls);
    }

/*
/**********************************************************
/* Abstract method impls
/**********************************************************
 */

    @Override
    protected boolean _isValuePresent(Either<?, ?> value) {
        return true;
    }

    @Override
    protected Object _getReferenced(Either<?, ?> value) {
        if (value.isRight()) return value.getOrNull();
        return value.left().get();
    }

    @Override
    protected Object _getReferencedIfPresent(Either<?, ?> value) {
        return _getReferenced(value);
    }
}
