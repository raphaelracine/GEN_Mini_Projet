/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scotlandyardclient.JSon;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * This class provides utility methods to convert Plain Old Java Objects (POJOs)
 * into their json representation, and vice-versa. It relies on the jackson
 * library.
 *
 */
public class JsonObjectMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Converts a json string into a POJO of the specified class
   *
   * @param <T> the class that we want to instantiate
   * @param json the json representation of the object
   * @param type the class to instantiate
   * @return an instance of T, which value corresponds to the json string
   * @throws IOException
   */
  public static <T> T parseJson(String json, Class<T> type) throws IOException {
    return objectMapper.readValue(json, type);
  }

}

