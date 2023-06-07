package ru.buttonone.service;

import io.restassured.response.ValidatableResponse;
import ru.buttonone.domain.Todo;

import java.util.List;

public interface TodoService {
    ValidatableResponse requestPostMethod(String jsonBody);
    ValidatableResponse requestGetMethod();
    ValidatableResponse requestDeleteMethodWithLoginAndPassword(long id, String login, String password);
    List<Todo> extractTodoList(ValidatableResponse validatableResponse);
    ValidatableResponse requestPut(String todoToJson, long id);
}
