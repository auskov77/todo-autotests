package ru.buttonone;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.domain.Todo;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.buttonone.service.Converter.todoToJson;
import static ru.buttonone.utils.TodoApiConstants.*;
import static ru.buttonone.utils.TodoHelper.*;

@DisplayName(" positive checks:")
public class TodoAppPositiveTest extends BaseTest {

    @DisplayName(" request Get")
    @Test
    public void shouldHaveCorrectGetMethod() throws IOException {
        logger.info("Begin checking the request Get");

        long id1 = generatedId();
        long id2 = generatedId();
        Todo todoExpectedId1 = new Todo(id1, DEFAULT_TEXT, randomBooleans());
        Todo todoExpectedId2 = new Todo(id2, DEFAULT_TEXT, randomBooleans());

        logger.info("Pre-condition: Inserting a test entity: " + todoExpectedId1 + "; " + todoExpectedId2);
        requestPost(todoExpectedId1);
        requestPost(todoExpectedId2);

        logger.info("Create a request Get");
        ValidatableResponse validatableResponseActual = requestGet();

        logger.info("Extracting todoActualId's from response of received entities");
        Optional<Todo> todoActualId1 = getTodoIdFromList(getTodoList(validatableResponseActual), id1);
        Optional<Todo> todoActualId2 = getTodoIdFromList(getTodoList(validatableResponseActual), id2);

        logger.info("Checking that the received entities correspond to the expected values");
        assertAll(
                () -> assertThat(validatableResponseActual.extract().statusCode()).isEqualTo(STATUS_CODE_200),
                () -> assertThat(todoActualId1).isEqualTo(Optional.of(todoExpectedId1)),
                () -> assertThat(todoActualId2).isEqualTo(Optional.of(todoExpectedId2))
        );
        logger.info("Post-condition: Removing the test entity id1=" + id1 + " and id2=" + id2);
        requestDeleteIdWithLoginPassword(id1, LOGIN, PASSWORD);
        requestDeleteIdWithLoginPassword(id2, LOGIN, PASSWORD);

        logger.info("End checking request Get");
    }

    @DisplayName(" request Post")
    @Test
    public void shouldHaveCorrectPostMethod() throws IOException {
        logger.info("Begin checking request Post");

        logger.info("Pre-condition: Create a todoExpected of test entity");
        long idDataTest = generatedId();
        Todo todoExpected = new Todo(idDataTest, DEFAULT_TEXT, randomBooleans());

        logger.info("Create a request Post with a test entity: " + todoExpected);
        ValidatableResponse responseActual = requestPost(todoExpected);

        logger.info("Create a request Get with a test entity");
        ValidatableResponse validatableResponse = requestGet();

        logger.info("Extracting a todoActual of received entity");
        Optional<Todo> todoActual = getTodoIdFromList(getTodoList(validatableResponse), idDataTest);

        logger.info("Checking that the received entities correspond to the expected values");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_201),
                () -> assertThat(todoActual).isEqualTo(Optional.of(todoExpected))
        );

        logger.info("Post-condition: Removing the test entity");
        requestDeleteIdWithLoginPassword(idDataTest, LOGIN, PASSWORD);

        logger.info("End checking request Post");
    }

    @DisplayName(" request Put")
    @Test
    public void shouldHaveCorrectPutMethod() throws IOException {
        logger.info("Begin checking request Put");

        long expectedId = generatedId();

        logger.info("Pre-condition: Inserting a test entity: "
                + new Todo(expectedId, DEFAULT_TEXT, DEFAULT_COMPLETED_TRUE));
        requestPost(new Todo(expectedId, DEFAULT_TEXT, DEFAULT_COMPLETED_TRUE));

        Todo todoExpected = new Todo(expectedId, DEFAULT_TEXT, DEFAULT_COMPLETED_FALSE);

        logger.info("Create a request Put with a test entity: " + todoExpected);
        ValidatableResponse responseActual = requestPut(todoToJson(todoExpected), expectedId);

        logger.info("Create a request Get with a test entity");
        ValidatableResponse validatableResponse = requestGet();

        logger.info("Extracting a todoActual of received entity");
        Optional<Todo> todoActual = getTodoIdFromList(getTodoList(validatableResponse), expectedId);

        logger.info("Checking that the received entities correspond to the expected values");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_200),
                () -> assertThat(todoActual).isEqualTo(Optional.of(todoExpected))
        );
        logger.info("Post-condition: Removing the test entity with id=" + expectedId);
        requestDeleteIdWithLoginPassword(expectedId, LOGIN, PASSWORD);

        logger.info("End checking request Put");
    }

    @DisplayName(" request Delete")
    @Test
    public void shouldHaveCorrectDeleteMethod() throws IOException {
        logger.info("Begin checking request Delete");

        long actualId = generatedId();

        logger.info("Pre-condition: Inserting a test entity: "
                + new Todo(actualId, DEFAULT_TEXT, DEFAULT_COMPLETED_TRUE));
        requestPost(new Todo(actualId, DEFAULT_TEXT, DEFAULT_COMPLETED_TRUE));

        logger.info("Create a request Delete with a test entity with id=" + actualId);
        ValidatableResponse responseActual = requestDeleteIdWithLoginPassword(actualId, LOGIN, PASSWORD);

        logger.info("Create a request Get with a test entity");
        ValidatableResponse validatableResponse = requestGet();

        logger.info("Create a list of received entities");
        List<Long> collectListIds = getTodoList(validatableResponse)
                .stream().map(Todo::getId).collect(Collectors.toList());

        logger.info("Checking that the received entities correspond to the expected values");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_204),
                () -> assertThat(collectListIds.contains(actualId)).isEqualTo(false)
        );

        logger.info("End checking request Delete");
    }
}
