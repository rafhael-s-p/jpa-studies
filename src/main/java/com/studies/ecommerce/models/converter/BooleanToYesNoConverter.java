package com.studies.ecommerce.models.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToYesNoConverter implements AttributeConverter<Boolean, String> {

    public static final String YES = "YES";
    public static final String NO = "NO";

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return Boolean.TRUE.equals(attribute) ? YES : NO;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return YES.equals(dbData) ? Boolean.TRUE : Boolean.FALSE;
    }

}
