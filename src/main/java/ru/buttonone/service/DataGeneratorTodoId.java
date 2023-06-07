package ru.buttonone.service;

import io.restassured.response.ValidatableResponse;
import ru.buttonone.domain.Todo;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static ru.buttonone.utils.TodoApiConstants.DEFAULT_ENDPOINT;

public enum DataGeneratorTodoId {
    INSTANCE;

    public synchronized long todoRandomId() {
        ValidatableResponse response = given()
                .when()
                .get(DEFAULT_ENDPOINT)
                .then();
        List<Todo> todoList = response
                .extract()
                .body()
                .jsonPath()
                .getList("", Todo.class);
        List<Long> listId = todoList.stream().map(Todo::getId).collect(Collectors.toList());
        long randomId = (long) (Math.random() * (100 - 1)) + 1;
        if (listId.contains(randomId)) {
            return todoRandomId();
        }
        return randomId;
    }

}
