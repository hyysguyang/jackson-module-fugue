package com.lifecosys.base.jackson.fugue;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.type.TypeModifier;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Option;

import java.lang.reflect.Type;

/**
 * We need to ensure `Optional` is a `ReferenceType`
 */
public class FugueTypeModifier extends TypeModifier
{
    @Override
    public JavaType modifyType(JavaType type, Type jdkType, TypeBindings bindings, TypeFactory typeFactory)
    {
        if (type.isReferenceType() || type.isContainerType()) {
            return type;
        }
        final Class<?> raw = type.getRawClass();

        JavaType refType;

        if (raw == Option.class || raw == Either.class) {
            // 19-Oct-2015, tatu: Looks like we may be missing type information occasionally,
            //    perhaps due to raw types.
            refType = type.containedTypeOrUnknown(0);
        } else {
            return type;
        }
        return ReferenceType.upgradeFrom(type, refType);
    }
}
