package com.example.rendezVous.utils;

import com.example.rendezVous.models.rendezVous.Status;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class EnumTypeDeserializer extends JsonDeserializer<Status> {

    @Override
    public Status deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final ObjectCodec objectCodec = jsonParser.getCodec();
        final JsonNode node = objectCodec.readTree(jsonParser);
        final String status = node.asText();
        return Status.valueOf(status.toUpperCase());
    }
}