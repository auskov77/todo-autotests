package ru.buttonone;

import io.restassured.response.ValidatableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.buttonone.domain.Todo;
import ru.buttonone.service.TodoService;
import ru.buttonone.service.TodoServiceImpl;

import java.io.IOException;
import java.util.List;

import static ru.buttonone.service.Converter.todoToJson;

public class BaseTest {
    public final static TodoService TODO_SERVICE = new TodoServiceImpl();
    public final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    public static ValidatableResponse requestPost(Todo todo) throws IOException {
        return TODO_SERVICE.requestPost(todoToJson(todo));
    }

    public static ValidatableResponse requestDeleteIdWithLoginPassword(long id, String login, String password) {
        return TODO_SERVICE.requestDeleteIdWithLoginPassword(id, login, password);
    }

    public static ValidatableResponse requestGet() {
        return TODO_SERVICE.requestGet();
    }

    public static List<Todo> getTodoList(ValidatableResponse validatableResponse) {
        return TODO_SERVICE.getTodoList(validatableResponse);
    }

    public static ValidatableResponse requestPut(String todoToJson, long id){
        return TODO_SERVICE.requestPut(todoToJson, id);
    }
}
