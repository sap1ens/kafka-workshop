package com.sap1ens.kafka.game_analytics.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap1ens.kafka.game_analytics.model.User;
import com.sap1ens.kafka.game_analytics.model.UserActivity;
import java.io.IOException;
import java.util.Map;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class JSONDeserializer<T> implements Deserializer<T> {

    private ObjectMapper objectMapper;

    public JSONDeserializer() {
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        // Brute-force deserialization for demo purposes, should be improved
        try {
            return (T) objectMapper.readValue(data, User.class);
        } catch (Exception e) {
            try {
                return (T) objectMapper.readValue(data, UserActivity.class);
            } catch (IOException ioex) {
                throw new SerializationException(ioex);
            }
        }
    }

    @Override
    public void close() {

    }
}
