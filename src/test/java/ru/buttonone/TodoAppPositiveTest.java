package ru.buttonone;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.domain.Todo;
import ru.buttonone.service.Converter;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.junit.jupiter.api.Assertions.*;
import static ru.buttonone.utils.TodoApiConstants.*;

@DisplayName(" positive checks:")
public class TodoAppPositiveTest extends BaseTest {

    @DisplayName(" request Get")
    @Test
    public void shouldHaveCorrectGetMethodNew() throws IOException {
        logger.info("Begin checking the request Get");
        long id1 = dataGenerateRandomId();
        logger.info("Get: Inserting a test entity " + id1);
        entryTestData(baseURI + TODOS, new Todo(id1, TEXT, COMPLETED_TRUE));
        long id2 = dataGenerateRandomId();
        logger.info("Get: Inserting a test entity " + id2);
        entryTestData(baseURI + TODOS, new Todo(id2, TEXT, COMPLETED_FALSE));
        logger.info("Get: Create a request Get");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Get: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        Todo todoId1 = extractingTodoFromTheListOfReceivedTodos(todoList, id1);
        Todo todoId2 = extractingTodoFromTheListOfReceivedTodos(todoList, id2);
        logger.info("Get: Checking that the received entities correspond to the expected values");
        assertAll(
                () -> assertEquals(STATUS_CODE_POST_200, validatableResponse.extract().statusCode()),
                () -> assertEquals(id1, todoId1.getId()),
                () -> assertEquals(TEXT, todoId1.getText()),
                () -> assertEquals(COMPLETED_TRUE, todoId1.isCompleted()),
                () -> assertEquals(id2, todoId2.getId()),
                () -> assertEquals(TEXT, todoId2.getText()),
                () -> assertEquals(COMPLETED_FALSE, todoId2.isCompleted())
        );
        logger.info("Get: Removing the test entity " + id1);
        deletingTestDataId(baseURI + TODOS, id1);
        logger.info("Get: Removing the test entity " + id2);
        deletingTestDataId(baseURI + TODOS, id2);
        logger.info("End checking request Get");
    }

    @DisplayName(" request Post")
    @Test
    public void shouldHaveCorrectPostMethodNew() throws IOException {
        logger.info("Begin checking request Post");
        long randomId = dataGenerateRandomId();
        logger.info("Post: Create a test entity with id=" + randomId + " and convert it to a json");
        Todo todo = new Todo(randomId, TEXT, COMPLETED_TRUE);
        String todoToJson = Converter.todoToJson(todo);
        String endPoint = baseURI + TODOS;
        logger.info("Post: Create a request Post with a test entity");
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        logger.info("Post: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Post: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        Todo todoResult = extractingTodoFromTheListOfReceivedTodos(todoList, randomId);
        logger.info("Post: Checking that the received entities correspond to the expected values");
        assertAll(
                () -> assertEquals(STATUS_CODE_POST_201, response.extract().statusCode()),
                () -> assertEquals(randomId, todoResult.getId()),
                () -> assertEquals(TEXT, todoResult.getText()),
                () -> assertEquals(COMPLETED_TRUE, todoResult.isCompleted())
        );
        logger.info("Post: Removing the test entity " + randomId);
        deletingTestDataId(endPoint, randomId);
        logger.info("End checking request Post");
    }

    @DisplayName(" request Put")
    @Test
    public void shouldHaveCorrectPutMethodNew() throws IOException {
        logger.info("Begin checking request Put");
        String endPoint = baseURI + TODOS;
        long randomId = dataGenerateRandomId();
        logger.info("Put: Inserting a test entity " + randomId);
        entryTestData(endPoint, new Todo(randomId, TEXT, COMPLETED_TRUE));
        logger.info("Put: Create a test entity with id=" + randomId + " and convert it to a json");
        Todo todo = new Todo(randomId, TEXT, COMPLETED_FALSE);
        String todoToJson = Converter.todoToJson(todo);
        logger.info("Put: Create a request Post with a test entity with id=" + randomId);
        ValidatableResponse response = responsePut(todoToJson, endPoint, randomId);
        logger.info("Put: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Put: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        Todo todoResult = extractingTodoFromTheListOfReceivedTodos(todoList, randomId);
        logger.info("Put: Checking that the received entities correspond to the expected values");
        assertAll(
                () -> assertEquals(STATUS_CODE_POST_200, response.extract().statusCode()),
                () -> assertEquals(COMPLETED_FALSE, todoResult.isCompleted())
        );
        logger.info("Put: Removing the test entity " + randomId);
        deletingTestDataId(endPoint, randomId);
        logger.info("End checking request Put");
    }

    @DisplayName(" request Delete")
    @Test
    public void shouldHaveCorrectDeleteMethodNew() throws IOException {
        logger.info("Begin checking request Delete");
        String endPoint = baseURI + TODOS;
        long randomId = dataGenerateRandomId();
        logger.info("Delete: Inserting a test entity " + randomId);
        entryTestData(endPoint, new Todo(randomId, TEXT, COMPLETED_TRUE));
        logger.info("Delete: Create a request Delete with a test entity with id=" + randomId);
        ValidatableResponse response = responseDelete(endPoint, randomId);
        logger.info("Delete: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Delete: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Delete: Checking that the received entities correspond to the expected values");
        assertAll(
                () -> assertEquals(STATUS_CODE_POST_204, response.extract().statusCode()),
                () -> assertFalse(todoList.contains(randomId))
        );
        logger.info("End checking request Delete");
    }
}
