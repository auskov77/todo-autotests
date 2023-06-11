package ru.buttonone.service;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.buttonone.domain.Todo;

import java.util.List;

import static io.restassured.RestAssured.given;
import static ru.buttonone.utils.TodoApiConstants.*;

public class TodoServiceImpl implements TodoService {

    @Override
    public ValidatableResponse requestGetMethod() {
        return given()
                .when()
                .get(DEFAULT_ENDPOINT)
                .then();
    }

    @Override
    public ValidatableResponse requestPostMethod(String jsonBody) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(jsonBody)
                .when()
                .post(DEFAULT_ENDPOINT)
                .then();
    }

    @Override
    public ValidatableResponse requestDeleteMethodWithLoginAndPassword(long id, String login, String password) {
        return given()
                .auth()
                .preemptive()
                .basic(login, password)
                .when()
                .delete(DEFAULT_ENDPOINT + "/" + id)
                .then();
    }

    @Override
    public List<Todo> extractTodoList(ValidatableResponse validatableResponse) {
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
