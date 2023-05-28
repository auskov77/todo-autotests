package ru.buttonone;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.domain.Todo;
import ru.buttonone.service.Converter;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.buttonone.utils.TodoApiConstants.*;

@DisplayName(" positive checks:")
public class TodoAppPositiveTest extends BaseTest {

    @DisplayName(" request Get")
    @Test
    public void shouldHaveCorrectGetMethod() throws IOException {
        logger.info("Begin checking the request Get");
        long id1 = dataGenerateRandomId();
        logger.info("Get: Inserting a test entity " + id1);
        boolean completedTestData1 = randomBooleans();
        Todo todoExpectedId1 = new Todo(id1, TEXT, completedTestData1);
        entryTestData(baseURI + TODOS, todoExpectedId1);
        long id2 = dataGenerateRandomId();
        logger.info("Get: Inserting a test entity " + id2);
        boolean completedTestData2 = randomBooleans();
        Todo todoExpectedId2 = new Todo(id2, TEXT, completedTestData2);
        entryTestData(baseURI + TODOS, todoExpectedId2);
        logger.info("Get: Create a request Get");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Get: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        Todo todoActualId1 = extractingTodoFromTheListOfReceivedTodos(todoList, id1);
        Todo todoActualId2 = extractingTodoFromTheListOfReceivedTodos(todoList, id2);
        logger.info("Get: Checking that the received entities correspond to the expected values");
        assertAll(
                () -> softAssertions.assertThat(validatableResponse.extract().statusCode()).isEqualTo(STATUS_CODE_200),
                () -> softAssertions.assertThat(todoActualId1).isEqualTo(todoExpectedId1),
                () -> softAssertions.assertThat(todoActualId2).isEqualTo(todoExpectedId2)
        );
        logger.info("Get: Removing the test entity " + id1);
        deletingTestDataId(baseURI + TODOS, id1);
        logger.info("Get: Removing the test entity " + id2);
        deletingTestDataId(baseURI + TODOS, id2);
        logger.info("End checking request Get");
    }

    @DisplayName(" request Post")
    @Test
    public void shouldHaveCorrectPostMethod() throws IOException {
        logger.info("Begin checking request Post");
        long randomId = dataGenerateRandomId();
        logger.info("Post: Create a test entity with id=" + randomId + " and convert it to a json");
        boolean completedTestData = randomBooleans();
        Todo todoExpected = new Todo(randomId, TEXT, completedTestData);
        String todoToJson = Converter.todoToJson(todoExpected);
        String endPoint = baseURI + TODOS;
        logger.info("Post: Create a request Post with a test entity");
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        logger.info("Post: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Post: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        Todo todoActual = extractingTodoFromTheListOfReceivedTodos(todoList, randomId);
        logger.info("Post: Checking that the received entities correspond to the expected values");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_201),
                () -> softAssertions.assertThat(todoActual).isEqualTo(todoExpected)
        );
        logger.info("Post: Removing the test entity " + randomId);
        deletingTestDataId(endPoint, randomId);
        logger.info("End checking request Post");
    }

    @DisplayName(" request Put")
    @Test
    public void shouldHaveCorrectPutMethod() throws IOException {
        logger.info("Begin checking request Put");
        String endPoint = baseURI + TODOS;
        long randomId = dataGenerateRandomId();
        logger.info("Put: Inserting a test entity " + randomId);
        entryTestData(endPoint, new Todo(randomId, TEXT, COMPLETED_TRUE));
        logger.info("Put: Create a test entity with id=" + randomId + " and convert it to a json");
        boolean completedExpected = COMPLETED_FALSE;
        Todo todoExpected = new Todo(randomId, TEXT, completedExpected);
        String todoToJson = Converter.todoToJson(todoExpected);
        logger.info("Put: Create a request Post with a test entity with id=" + randomId);
        ValidatableResponse response = responsePut(todoToJson, endPoint, randomId);
        logger.info("Put: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Put: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        Todo todoActual = extractingTodoFromTheListOfReceivedTodos(todoList, randomId);
        logger.info("Put: Checking that the received entities correspond to the expected values");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_200),
                () -> softAssertions.assertThat(todoActual.getCompleted()).isEqualTo(completedExpected)
        );
        logger.info("Put: Removing the test entity " + randomId);
        deletingTestDataId(endPoint, randomId);
        logger.info("End checking request Put");
    }

    @DisplayName(" request Delete")
    @Test
    public void shouldHaveCorrectDeleteMethod() throws IOException {
        logger.info("Begin checking request Delete");
        String endPoint = baseURI + TODOS;
        long expectedId = dataGenerateRandomId();
        logger.info("Delete: Inserting a test entity " + expectedId);
        entryTestData(endPoint, new Todo(expectedId, TEXT, COMPLETED_TRUE));
        logger.info("Delete: Create a request Delete with a test entity with id=" + expectedId);
        ValidatableResponse response = responseDelete(endPoint, expectedId);
        logger.info("Delete: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Delete: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Delete: Checking that the received entities correspond to the expected values");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_204),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(expectedId))
        );
        logger.info("End checking request Delete");
    }
}
