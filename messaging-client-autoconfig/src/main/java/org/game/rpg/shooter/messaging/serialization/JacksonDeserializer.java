package org.game.rpg.shooter.messaging.serialization;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

public class JacksonDeserializer<T> implements Deserializer<T> {

  private final ObjectMapper objectMapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  private final Class<T> clazz;

  public JacksonDeserializer(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public T deserialize(String topic, byte[] data) {
    try (InputStream inputStream = new ByteArrayInputStream(data)) {
      return objectMapper.readValue(inputStream, clazz);
    } catch (IOException exception) {
      throw new SerializationException(exception);
    }
  }
}
