package ru.buttonone;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.buttonone.domain.Todo;
import ru.buttonone.service.Converter;
import ru.buttonone.service.TodoService;
import ru.buttonone.service.TodoServiceImpl;
import ru.buttonone.utils.Props;

import java.io.IOException;
import java.util.List;

public class BaseTest {
    public final static TodoService TODO_SERVICE = new TodoServiceImpl();
    public final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected SoftAssertions softAssertions;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = Props.getProperty("base_uri");
    }

    @BeforeEach
    public void softAssert() {
        softAssertions = new SoftAssertions();
    }

    public static ValidatableResponse requestPost(String endPoint, Todo todo) throws IOException {
        String jsonBodyId = Converter.todoToJson(todo);
        return TODO_SERVICE.requestPostMethod(endPoint, jsonBodyId);
    }

    public static ValidatableResponse requestDeleteIdWithLoginAndPassword(String endPoint, long id, String login, String password) {
        return TODO_SERVICE.requestDeleteMethodWithLoginAndPassword(endPoint, id, login, password);
    }

    public static ValidatableResponse requestGet(String endPoint) {
        return TODO_SERVICE.requestGetMethod(endPoint);
    }

    public static List<Todo> extractTodoList(ValidatableResponse validatableResponse) {
        return TODO_SERVICE.extractTodoList(validatableResponse);
    }

    public static ValidatableResponse requestPut(String todoToJson, String endPoint, long id){
        return TODO_SERVICE.requestPut(todoToJson, endPoint, id);
    }
}
