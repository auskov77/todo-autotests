package ru.buttonone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.buttonone.domain.Todo;

import java.io.IOException;

public class Converter {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static String todoToJson(Todo todo) throws JsonProcessingException {
        return MAPPER.writeValueAsString(todo);
    }

    public static Object jsonToTodo(String json) throws IOException {
        return MAPPER.readValue(json, Todo.class);
    }
}
