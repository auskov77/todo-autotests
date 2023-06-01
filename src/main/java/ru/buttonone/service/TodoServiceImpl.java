package ru.buttonone.service;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import ru.buttonone.domain.Todo;

import java.util.List;

import static io.restassured.RestAssured.given;

public class TodoServiceImpl implements TodoService {

    @Override
    public ValidatableResponse requestGetMethod(String endPoint) {
        return given()
                .when()
                .get(endPoint)
                .then();
    }

    @Override
    public ValidatableResponse requestPostMethod(String endPoint, String jsonBody) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(jsonBody)
                .when()
                .post(endPoint)
                .then();
    }

    @Override
    public ValidatableResponse requestDeleteMethodWithLoginAndPassword(String endPoint, long id, String login, String password) {
        return given()
                .auth()
                .preemptive()
                .basic(login, password)
                .when()
                .delete(endPoint + "/" + id)
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
    public ValidatableResponse requestPut(String todoToJson, String endPoint, long id) {
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(todoToJson)
                .when()
                .put(endPoint + "/" + id)
                .then();
    }

}
