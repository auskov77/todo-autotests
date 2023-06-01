package ru.buttonone.service;

import io.restassured.response.ValidatableResponse;
import ru.buttonone.domain.Todo;

import java.util.List;

public interface TodoService {
    ValidatableResponse requestPostMethod(String endPoint, String jsonBody);
    ValidatableResponse requestGetMethod(String endPoint);
    ValidatableResponse requestDeleteMethodWithLoginAndPassword(String endPoint, long id, String login, String password);
    List<Todo> extractTodoList(ValidatableResponse validatableResponse);
    ValidatableResponse requestPut(String todoToJson, String endPoint, long id);
}
