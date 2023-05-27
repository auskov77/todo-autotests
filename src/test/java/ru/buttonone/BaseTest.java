package ru.buttonone;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.buttonone.domain.Todo;
import ru.buttonone.service.Converter;
import ru.buttonone.service.DataGeneratorTodoId;
import ru.buttonone.service.PrepareTodoService;
import ru.buttonone.service.PrepareTodoServiceImpl;
import ru.buttonone.utils.Props;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static ru.buttonone.utils.TodoApiConstants.*;

public class BaseTest {
    public final static PrepareTodoService PREPARED_TODO_SERVICE = new PrepareTodoServiceImpl();
    public final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = Props.getProperty("base_uri");
    }

    public static void entryTestData(String endPoint, Todo todo) throws IOException {
        String jsonBodyId = Converter.todoToJson(todo);
        PREPARED_TODO_SERVICE.requestPostMethod(endPoint, jsonBodyId);
    }

    public static void deletingTestDataId(String endPoint, long id) {
        PREPARED_TODO_SERVICE.requestDeleteMethod(endPoint, id);
    }

    public static Todo extractingTodoFromTheListOfReceivedTodos(List<Todo> todoList, long id) {
        Todo resultTodoWithId = null;
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                resultTodoWithId = new Todo(todo.getId(), todo.getText(), todo.isCompleted());
            }
        }
        return resultTodoWithId;
    }

    public static long dataGenerateRandomId(){
        return DataGeneratorTodoId.INSTANCE.todoRandomId();
    }

    public static ValidatableResponse responseGet(){
        return given()
                .when()
                .get(baseURI + TODOS)
                .then();
    }

    public static List<Todo> extractTodoList(ValidatableResponse validatableResponse){
        return validatableResponse
                .extract()
                .body()
                .jsonPath()
                .getList("", Todo.class);
    }

    public static ValidatableResponse responsePost(String todoToJson, String endPoint){
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(todoToJson)
                .when()
                .post(endPoint)
                .then();
    }

    public static ValidatableResponse responsePut(String todoToJson, String endPoint, long randomId){
        return given()
                .contentType(ContentType.JSON)
                .and()
                .body(todoToJson)
                .when()
                .put(endPoint + "/" + randomId)
                .then();
    }

    public static ValidatableResponse responseDelete(String endPoint, long randomId){
        return given()
                .auth()
                .preemptive()
                .basic(LOGIN, PASSWORD)
                .when()
                .delete(endPoint + "/" + randomId)
                .then();
    }
}
