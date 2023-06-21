package ru.buttonone;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.domain.Todo;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.buttonone.service.Converter.todoToJson;
import static ru.buttonone.utils.TodoApiConstants.*;
import static ru.buttonone.utils.TodoHelper.generatedId;
import static ru.buttonone.utils.TodoHelper.randomBooleans;

@DisplayName(" negative checks:")
public class TodoAppNegativeTest extends BaseTest {

    @DisplayName(" request Post with ID<0")
    @Test
    public void shouldHaveIncorrectMethodPostWithIdLessThenZero() throws IOException {
        logger.info("Begin checking request Post with ID<0");

        long actualId = -1;

        logger.info("Create a request Post with a test entity with Id=" + actualId);
        ValidatableResponse responseActual = TODO_SERVICE.requestPost(new Todo(actualId, DEFAULT_TEXT, randomBooleans()));

        logger.info("Create a list of received entities from request Get");
        List<Todo> todoList = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> assertThat(todoList.stream().filter(todo -> todo.getId() == actualId).count()).isEqualTo(0)
        );

        logger.info("End checking request Post with ID<0");
    }

    @DisplayName(" request Post with ID is null")
    @Test
    public void shouldHaveIncorrectMethodPostWithIdIsNull() throws IOException {
        logger.info("Begin checking request Post with ID is null");

        logger.info("Create a request Post with a test entity with Id is null");
        ValidatableResponse responseActual = TODO_SERVICE.requestPost(new Todo(null, DEFAULT_TEXT, randomBooleans()));

        logger.info("Create a list of received entities from request Get");
        List<Todo> todoList = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> assertThat(todoList.stream().filter(todo -> todo.getId() == null).count()).isEqualTo(0)
        );

        logger.info("End checking request Post with ID=null");
    }

    @DisplayName(" request Post with Text is null")
    @Test
    public void shouldHaveIncorrectPostMethodWithTextNull() throws IOException {
        logger.info("Begin checking request Post with Text is null");

        long actualId = generatedId();

        logger.info("Create a request Post with a test entity with Text is null");
        ValidatableResponse responseActual = TODO_SERVICE.requestPost(new Todo(actualId, null, randomBooleans()));

        logger.info("Create a list of received entities");
        List<Todo> todoList = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> assertThat(todoList.stream().filter(todo -> todo.getText() == null).count()).isEqualTo(0)
        );

        logger.info("End checking request Post with Text=null");
    }

    @DisplayName(" request Post with Completed is null")
    @Test
    public void shouldHaveIncorrectPostMethodWithCompletedNull() throws IOException {
        logger.info("Begin checking request Post with Completed is null");

        long actualId = generatedId();

        logger.info("Create a request Post with a test entity with Completed is null");
        ValidatableResponse responseActual = TODO_SERVICE.requestPost(new Todo(actualId, DEFAULT_TEXT, null));

        logger.info("Create a list of received entities");
        List<Todo> todoList = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> assertThat(todoList.stream().filter(todo -> todo.getCompleted() == null).count()).isEqualTo(0)
        );

        logger.info("End checking request Post with Completed=null");
    }

    @DisplayName(" request Put with Id is null")
    @Test
    public void shouldHaveIncorrectPutMethodWithIdNull() throws IOException {
        logger.info("Begin checking request Put with Id is null");

        long idDataTest = generatedId();

        logger.info("Pre-condition: Entry a test entity with " +
                "Id=" + idDataTest + ", " +
                "Text=" + DEFAULT_TEXT + ", " +
                "Completed=" + DEFAULT_COMPLETED_FALSE);
        TODO_SERVICE.requestPost(new Todo(idDataTest, DEFAULT_TEXT, DEFAULT_COMPLETED_FALSE));

        logger.info("Create a request Put with a test entity with " +
                "Id is null, " +
                "Text=" + DEFAULT_TEXT + ", " +
                "Completed=" + DEFAULT_COMPLETED_FALSE);
        ValidatableResponse responseActualPut = TODO_SERVICE.requestPut(
                todoToJson(new Todo(null, DEFAULT_TEXT, DEFAULT_COMPLETED_FALSE)),
                idDataTest
        );

        logger.info("Extracting todoActual a list from request Get of received entities");
        List<Todo> todoListActual = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActualPut.extract().statusCode()).isEqualTo(STATUS_CODE_401),
                () -> assertThat(todoListActual.stream().filter(todo -> todo.getId() == null).count()).isEqualTo(0)
        );

        logger.info("Post-condition: Removing the test entity with Id=" + idDataTest);
        TODO_SERVICE.requestDeleteByIdWithLoginPassword(idDataTest, LOGIN, PASSWORD);

        logger.info("End checking request Put with Id is null");
    }

    @DisplayName(" request Put with Text is null")
    @Test
    public void shouldHaveIncorrectPutMethodWithTextNull() throws IOException {
        logger.info("Begin checking request Put with data Text null");

        long idDataTest = generatedId();

        logger.info("Pre-condition: Entry a test entity with " +
                "Id=" + idDataTest + ", " +
                "Text=" + DEFAULT_TEXT + ", " +
                "Completed=" + DEFAULT_COMPLETED_FALSE);
        TODO_SERVICE.requestPost(new Todo(idDataTest, DEFAULT_TEXT, DEFAULT_COMPLETED_FALSE));

        logger.info("Create a request Put with a test entity with " +
                "Id=" + idDataTest + ", " +
                "Text is null, " +
                "Completed=" + DEFAULT_COMPLETED_FALSE);
        ValidatableResponse responseActualPut = TODO_SERVICE.requestPut(
                todoToJson(new Todo(idDataTest, null, DEFAULT_COMPLETED_FALSE)),
                idDataTest
        );

        logger.info("Extracting todoActual a list from request Get of received entities");
        List<Todo> todoListActual = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActualPut.extract().statusCode()).isEqualTo(STATUS_CODE_401),
                () -> assertThat(todoListActual.stream().filter(todo -> todo.getText() == null).count()).isEqualTo(0)
        );

        logger.info("Post-condition: Removing the test entity with id=" + idDataTest);
        TODO_SERVICE.requestDeleteByIdWithLoginPassword(idDataTest, LOGIN, PASSWORD);

        logger.info("End checking request Put with Text is null");
    }

    @DisplayName(" request Put with Completed is null")
    @Test
    public void shouldHaveIncorrectPutMethodWithCompletedNull() throws IOException {
        logger.info("Begin checking request Put with Completed is null");

        long idDataTest = generatedId();

        logger.info("Pre-condition: Entry a test entity with " +
                "Id=" + idDataTest + ", " +
                "Text=" + DEFAULT_TEXT + ", " +
                "Completed=" + DEFAULT_COMPLETED_FALSE);
        TODO_SERVICE.requestPost(new Todo(idDataTest, DEFAULT_TEXT, DEFAULT_COMPLETED_FALSE));

        logger.info("Create a request Put with a test entity with " +
                "Id=" + idDataTest + ", " +
                "Text=" + DEFAULT_TEXT + ", " +
                "Completed is null");
        ValidatableResponse responseActualPut = TODO_SERVICE.requestPut(
                todoToJson(new Todo(idDataTest, DEFAULT_TEXT, null)),
                idDataTest
        );

        logger.info("Extracting todoActual a list from request Get of received entities");
        List<Todo> todoListActual = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActualPut.extract().statusCode()).isEqualTo(STATUS_CODE_401),
                () -> assertThat(todoListActual.stream().filter(todo -> todo.getCompleted() == null).count()).isEqualTo(0)
        );

        logger.info("Post-condition: Removing the test entity with Id=" + idDataTest);
        TODO_SERVICE.requestDeleteByIdWithLoginPassword(idDataTest, LOGIN, PASSWORD);

        logger.info("End checking request Put with Completed is null");
    }

    @DisplayName(" request Delete with Id is missing")
    @Test
    public void shouldHaveIncorrectDeleteMethodWithIdIsMissing() throws IOException {
        logger.info("Begin checking request Delete with Id is missing");

        long actualId = generatedId();

        logger.info("Pre-condition: Inserting a test entity with Id=" + actualId);
        TODO_SERVICE.requestPost(new Todo(actualId, DEFAULT_TEXT, randomBooleans()));

        logger.info("Removing the test entity with Id=" + actualId);
        TODO_SERVICE.requestDeleteByIdWithLoginPassword(actualId, LOGIN, PASSWORD);

        logger.info("Create a request Delete with a test entity with Id is missing");
        ValidatableResponse responseActual = TODO_SERVICE.requestDeleteByIdWithLoginPassword(actualId, LOGIN, PASSWORD);

        logger.info("Create a list of received entities from request Get");
        List<Todo> todoList = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_404),
                () -> assertThat(todoList.stream().filter(todo -> todo.getId() == actualId).count()).isEqualTo(0)
        );

        logger.info("End checking request Delete with Id is missing");
    }

    @DisplayName(" request Delete with not valid Login")
    @Test
    public void shouldHaveIncorrectDeleteMethodWithNotValidLogin() throws IOException {
        logger.info("Begin checking request Delete with not valid Login");

        long actualId = generatedId();

        logger.info("Pre-condition: Inserting a test entity with Id=" + actualId);
        TODO_SERVICE.requestPost(new Todo(actualId, DEFAULT_TEXT, randomBooleans()));

        logger.info("Create a request Delete with a not valid login");
        ValidatableResponse responseActual = TODO_SERVICE.requestDeleteByIdWithLoginPassword(actualId, new Random().toString(), PASSWORD);

        logger.info("Create a list of received entities");
        List<Todo> todoList = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_401),
                () -> assertThat(todoList.stream().filter(todo -> todo.getId() == actualId).count()).isEqualTo(1)
        );

        logger.info("Post-condition: Removing the test entity with id=" + actualId);
        TODO_SERVICE.requestDeleteByIdWithLoginPassword(actualId, LOGIN, PASSWORD);

        logger.info("End checking request Delete with not valid Login");
    }

    @DisplayName(" request Delete with not valid Password")
    @Test
    public void shouldHaveIncorrectDeleteMethodWithNotValidPassword() throws IOException {
        logger.info("Begin checking request Delete with not valid Password");

        long actualId = generatedId();

        logger.info("Pre-condition: Inserting a test entity with Id=" + actualId);
        TODO_SERVICE.requestPost(new Todo(actualId, DEFAULT_TEXT, randomBooleans()));

        logger.info("Create a request Delete with a not valid Password");
        ValidatableResponse responseActual = TODO_SERVICE.requestDeleteByIdWithLoginPassword(actualId, LOGIN, new Random().toString());

        logger.info("Create a list of received entities");
        List<Todo> todoList = TODO_SERVICE.getTodoList(TODO_SERVICE.requestGet());

        logger.info("Checking for a negative result");
        assertAll(
                () -> assertThat(responseActual.extract().statusCode()).isEqualTo(STATUS_CODE_401),
                () -> assertThat(todoList.stream().filter(todo -> todo.getId() == actualId).count()).isEqualTo(1)
        );

        logger.info("Post-condition: Removing the test entity with id=" + actualId);
        TODO_SERVICE.requestDeleteByIdWithLoginPassword(actualId, LOGIN, PASSWORD);

        logger.info("End checking request Delete with not valid Password");
    }
}