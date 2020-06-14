package com.lifecosys.base.jackson.fugue.option;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.util.NameTransformer;

public class OptionBeanPropertyWriter extends BeanPropertyWriter
{
    private static final long serialVersionUID = 1L;

    /**
     * @since 2.9
     */
    protected final Object _empty;

    /**
     * @since 2.9
     */
    protected OptionBeanPropertyWriter(BeanPropertyWriter base, Object empty) {
        super(base);
        _empty = empty;
    }

    protected OptionBeanPropertyWriter(OptionBeanPropertyWriter base, PropertyName newName) {
        super(base, newName);
        _empty = base._empty;
    }

    @Override
    protected BeanPropertyWriter _new(PropertyName newName) {
        return new OptionBeanPropertyWriter(this, newName);
    }

    @Override
    public BeanPropertyWriter unwrappingWriter(NameTransformer unwrapper) {
        return new OptionUnwrappingBeanPropertyWriter(this, unwrapper, _empty);
    }

    @Override
    public void serializeAsField(Object bean, JsonGenerator g, SerializerProvider prov) throws Exception
    {
        if (_nullSerializer == null) {
            Object value = get(bean);
            if (value == null || value.equals(_empty)) {
                return;
            }
        }
        super.serializeAsField(bean, g, prov);
    }
}
