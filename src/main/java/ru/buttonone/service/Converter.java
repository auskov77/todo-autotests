package ru.buttonone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.buttonone.domain.Todo;

import java.io.IOException;

public class Converter {

    public static String todoToJson(Todo todo) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(todo);
    }

    public static Object jsonToTodo(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Todo.class);
    }
}
