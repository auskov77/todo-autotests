package ru.buttonone;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.domain.Todo;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.buttonone.service.Converter.*;
import static ru.buttonone.utils.TodoApiConstants.*;
import static ru.buttonone.utils.TodoHelper.*;

@DisplayName(" positive checks:")
public class TodoAppPositiveTest extends BaseTest {
    public static final String END_POINT = baseURI + DEFAULT_TODOS;

    @DisplayName(" request Get")
    @Test
    public void shouldHaveCorrectGetMethod() throws IOException {
        logger.info("Begin checking the request Get");

        long id1 = dataGenerateRandomId();
        long id2 = dataGenerateRandomId();
        Todo todoExpectedId1 = new Todo(id1, DEFAULT_TEXT, randomBooleans());
        Todo todoExpectedId2 = new Todo(id2, DEFAULT_TEXT, randomBooleans());

        logger.info("Pre-condition: Inserting a test entity id1=" + id1 + " and id2=" + id2);
        requestPost(END_POINT, todoExpectedId1);
        requestPost(END_POINT, todoExpectedId2);

        logger.info("Create a request Get");
        ValidatableResponse validatableResponseActual = requestGet(END_POINT);

        logger.info("Extracting todoActualId's from response of received entities");
        Todo todoActualId1 = extractingTodoFromTheListOfReceivedTodos(extractTodoList(validatableResponseActual), id1);
        Todo todoActualId2 = extractingTodoFromTheListOfReceivedTodos(extractTodoList(validatableResponseActual), id2);

        logger.info("Checking that the received entities correspond to the expected values");
        assertAll(
                () -> softAssertions.assertThat(validatableResponseActual.extract().statusCode()).isEqualTo(STATUS_CODE_200),
                () -> softAssertions.assertThat(todoActualId1).isEqualTo(todoExpectedId1),
                () -> softAssertions.assertThat(todoActualId2).isEqualTo(todoExpectedId2)
        );
        logger.info("Post-condition: Removing the test entity id1=" + id1 + " and id2=" + id2);
        requestDeleteIdWithLoginAndPassword(END_POINT, id1, LOGIN, PASSWORD);
        requestDeleteIdWithLoginAndPassword(END_POINT, id2, LOGIN, PASSWORD);

        logger.info("End checking request Get");
    }

    @DisplayName(" request Post")
    @Test
    public void shouldHaveCorrectPostMethod() throws IOException {
        logger.info("Begin checking request Post");

        logger.info("Pre-condition: Create a todoExpected of test entity");
        long idDataTest = dataGenerateRandomId();
        Todo todoExpected = new Todo(idDataTest, DEFAULT_TEXT, randomBooleans());

        logger.info("Create a request Post with a test entity");
        ValidatableResponse responseActual = requestPost(END_POINT, todoExpected);

        logger.info("Create a request Get with a test entity");
        ValidatableResponse validatableResponse = requestGet(END_POINT);

        logger.info("Extracting a todoActual of received entity");
        Todo todoActual = extractingTodoFromTheListOfReceivedTodos(extractTodoList(validatableResponse), idDataTest);

        logger.info("Checking that the received entities correspond to the expected values");
        assertAll(
                () -> softAssertions.assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_201),
                () -> softAssertions.assertThat(todoActual).isEqualTo(todoExpected)
        );

        logger.info("Post-condition: Removing the test entity");
        requestDeleteIdWithLoginAndPassword(END_POINT, idDataTest, LOGIN, PASSWORD);

        logger.info("End checking request Post");
    }

    @DisplayName(" request Put")
    @Test
    public void shouldHaveCorrectPutMethod() throws IOException {
        logger.info("Begin checking request Put");

        long expectedId = dataGenerateRandomId();

        logger.info("Pre-condition: Inserting a test entity with id=" + expectedId + " with completed=" + DEFAULT_COMPLETED_TRUE);
        requestPost(
                END_POINT,
                new Todo(expectedId, DEFAULT_TEXT, DEFAULT_COMPLETED_TRUE)
        );

        logger.info("Create a request Put with a test entity with id=" + expectedId + " with completed=" + DEFAULT_COMPLETED_FALSE);
        ValidatableResponse responseActual = requestPut(
                todoToJson(new Todo(expectedId, DEFAULT_TEXT, DEFAULT_COMPLETED_FALSE)),
                END_POINT,
                expectedId
        );

        logger.info("Create a request Get with a test entity");
        ValidatableResponse validatableResponse = requestGet(END_POINT);

        logger.info("Extracting a todoActual of received entity");
        Todo todoActual = extractingTodoFromTheListOfReceivedTodos(extractTodoList(validatableResponse), expectedId);

        logger.info("Checking that the received entities correspond to the expected values");
        assertAll(
                () -> softAssertions.assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_200),
                () -> {
                    assert todoActual != null;
                    softAssertions.assertThat(todoActual.getCompleted()).isEqualTo(DEFAULT_COMPLETED_FALSE);
                }
        );
        logger.info("Post-condition: Removing the test entity with id=" + expectedId);
        requestDeleteIdWithLoginAndPassword(END_POINT, expectedId, LOGIN, PASSWORD);

        logger.info("End checking request Put");
    }

    @DisplayName(" request Delete")
    @Test
    public void shouldHaveCorrectDeleteMethod() throws IOException {
        logger.info("Begin checking request Delete");

        long actualId = dataGenerateRandomId();

        logger.info("Pre-condition: Inserting a test entity with id=" + actualId);
        requestPost(
                END_POINT,
                new Todo(actualId, DEFAULT_TEXT, DEFAULT_COMPLETED_TRUE)
        );

        logger.info("Create a request Delete with a test entity with id=" + actualId);
        ValidatableResponse responseActual = requestDeleteIdWithLoginAndPassword(END_POINT, actualId, LOGIN, PASSWORD);

        logger.info("Create a request Get with a test entity");
        ValidatableResponse validatableResponse = requestGet(END_POINT);

        logger.info("Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);

        logger.info("Checking that the received entities correspond to the expected values");
        assertAll(
                () -> softAssertions.assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_204),
                () -> softAssertions.assertThat(todoList.contains(actualId)).isEqualTo(false)
        );

        logger.info("End checking request Delete");
    }
}
