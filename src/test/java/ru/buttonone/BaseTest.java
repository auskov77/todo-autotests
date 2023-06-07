package ru.buttonone;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.buttonone.domain.Todo;
import ru.buttonone.service.TodoService;
import ru.buttonone.service.TodoServiceImpl;
import ru.buttonone.utils.Props;

import java.io.IOException;
import java.util.List;

import static ru.buttonone.service.Converter.todoToJson;

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

    public static ValidatableResponse requestPost(Todo todo) throws IOException {
        return TODO_SERVICE.requestPostMethod(todoToJson(todo));
    }

    public static ValidatableResponse requestDeleteIdWithLoginAndPassword(long id, String login, String password) {
        return TODO_SERVICE.requestDeleteMethodWithLoginAndPassword(id, login, password);
    }

    public static ValidatableResponse requestGet() {
        return TODO_SERVICE.requestGetMethod();
    }

    public static List<Todo> extractTodoList(ValidatableResponse validatableResponse) {
        return TODO_SERVICE.extractTodoList(validatableResponse);
    }

    public static ValidatableResponse requestPut(String todoToJson, long id){
        return TODO_SERVICE.requestPut(todoToJson, id);
    }
}
