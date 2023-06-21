package ru.buttonone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.buttonone.domain.Todo;

import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.buttonone.utils.TodoApiConstants.*;
import static ru.buttonone.service.Converter.*;

public class TodoServiceImpl implements TodoService {

    @Override
    public ValidatableResponse requestGet() {
        return given()
                .when()
                .get(DEFAULT_ENDPOINT)
                .then();
    }

    @Override
    public ValidatableResponse requestPost(Todo todo) throws JsonProcessingException {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(todoToJson(todo))
                .when()
                .post(DEFAULT_ENDPOINT)
                .then();
    }

    @Override
    public ValidatableResponse requestDeleteByIdWithLoginPassword(long id, String login, String password) {
        return given()
                .auth()
                .preemptive()
                .basic(login, password)
                .when()
                .delete(DEFAULT_ENDPOINT + "/" + id)
                .then();
    }

    @Override
    public List<Todo> getTodoList(ValidatableResponse validatableResponse) {
        return validatableResponse
                .extract()
                .body()
                .jsonPath()
                .getList("", Todo.class);
    }

    @Override
    public ValidatableResponse requestPut(String todoToJson, long id) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(todoToJson)
                .when()
                .put(DEFAULT_ENDPOINT + "/" + id)
                .then();
    }

}