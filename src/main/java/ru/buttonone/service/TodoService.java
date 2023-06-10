package ru.buttonone.service;

import io.restassured.response.ValidatableResponse;
import ru.buttonone.domain.Todo;

import java.util.List;

public interface TodoService {
    ValidatableResponse requestPost(String jsonBody);
    ValidatableResponse requestGet();
    ValidatableResponse requestDeleteIdWithLoginPassword(long id, String login, String password);
    List<Todo> getTodoList(ValidatableResponse validatableResponse);
    ValidatableResponse requestPut(String todoToJson, long id);
}
