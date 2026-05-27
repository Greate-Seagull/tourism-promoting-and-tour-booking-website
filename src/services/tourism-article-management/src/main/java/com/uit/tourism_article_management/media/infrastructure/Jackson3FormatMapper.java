package com.uit.tourism_article_management.media.infrastructure;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;
import org.hibernate.type.format.FormatMapper;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

public class Jackson3FormatMapper implements FormatMapper {
    private final ObjectMapper mapper;

    public Jackson3FormatMapper() {
        this.mapper = JsonMapper.builder().build();
    }

    @Override
    public <T> T fromString(CharSequence charSequence, JavaType<T> javaType, WrapperOptions wrapperOptions) {
        if (charSequence == null || charSequence.isEmpty())
            return null;

        try {
            return this.mapper.readValue(charSequence.toString(), this.mapper.constructType(javaType.getJavaType()));
        } catch (Exception e) {
            throw new RuntimeException("Jackson 3 failed to deserialize JSONB string: " + charSequence, e);
        }
    }

    @Override
    public <T> String toString(T value, JavaType<T> javaType, WrapperOptions wrapperOptions) {
        if (value == null) return null;
        try {
            return this.mapper.writeValueAsString(value);
        } catch (Exception e) {
            throw new RuntimeException("Jackson 3 failed to serialize object to JSONB string: ", e);
        }
    }
}