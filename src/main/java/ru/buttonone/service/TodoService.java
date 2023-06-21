package ru.buttonone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.ValidatableResponse;
import ru.buttonone.domain.Todo;

import java.util.List;

public interface TodoService {
    ValidatableResponse requestPost(Todo todo) throws JsonProcessingException;
    ValidatableResponse requestGet();
    ValidatableResponse requestDeleteByIdWithLoginPassword(long id, String login, String password);
    List<Todo> getTodoList(ValidatableResponse validatableResponse);
    ValidatableResponse requestPut(String todoToJson, long id);
}