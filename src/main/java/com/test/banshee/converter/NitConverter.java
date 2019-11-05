package com.test.banshee.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Converter
public class NitConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String nit) {
        return Base64.getEncoder().encodeToString(nit.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return new String(Base64.getDecoder().decode(dbData.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
    }

}
