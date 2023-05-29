package ru.buttonone;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.buttonone.domain.Todo;
import ru.buttonone.service.Converter;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.baseURI;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.buttonone.utils.TodoApiConstants.*;

@DisplayName(" negative checks:")
public class TodoAppNegativeTest extends BaseTest {

    @DisplayName(" request Get with ID<0")
    @Test
    public void shouldHaveIncorrectMethodGetWithIdLessThanZero() throws IOException {
        logger.info("Begin checking the request Get with ID<0");
        long idDataTest = -1;
        boolean completedDataTest = randomBooleans();
        logger.info("Get ID<0:Formation of a test entity to test the request with id=" + idDataTest);
        Todo todoExpectedDataTestId = new Todo(idDataTest, TEXT, completedDataTest);
        logger.info("Get ID<0: Inserting a test entity " + idDataTest);
        entryTestData(baseURI + TODOS, todoExpectedDataTestId);
        logger.info("Get ID<0: Create a request Get");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Get ID<0: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Get ID<0: Checking for a negative result");
        assertAll(
                () -> softAssertions.assertThat(validatableResponse.extract().statusCode()).isEqualTo(STATUS_CODE_200),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
        logger.info("End checking request Get with ID<0");
    }

    @DisplayName(" request Post with ID<0")
    @Test
    public void shouldHaveIncorrectMethodPostWithIdLessThenZero() throws IOException {
        logger.info("Begin checking request Post with ID<0");
        long idDataTest = -1;
        logger.info("Post ID<0: Create a test entity with id=" + idDataTest + " and convert it to a json");
        boolean completedDataTest = randomBooleans();
        Todo todoExpectedDataTestId = new Todo(idDataTest, TEXT, completedDataTest);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        String endPoint = baseURI + TODOS;
        logger.info("Post ID<0: Create a request Post with a test entity");
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        logger.info("Post ID<0: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Post ID<0: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Post ID<0: Checking for a negative result");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
        logger.info("End checking request Post with ID<0");
    }

    @DisplayName(" request Post with ID is null")
    @Test
    public void shouldHaveIncorrectMethodPostWithIdIsNull() throws IOException {
        logger.info("Begin checking request Post with ID is null");
        logger.info("Post ID=null: Create a test entity with id=" + null + " and convert it to a json");
        boolean completedDataTest = randomBooleans();
        Todo todoExpectedDataTestId = new Todo(null, TEXT, completedDataTest);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        String endPoint = baseURI + TODOS;
        logger.info("Post ID=null: Create a request Post with a test entity");
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        logger.info("Post ID=null: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Post ID=null: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Post ID=null: Checking for a negative result");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(null))
        );
        logger.info("End checking request Post with ID=null");
    }

    @DisplayName(" request Post with Text is null")
    @Test
    public void shouldHaveIncorrectPostMethodWithTextNull() throws IOException {
        logger.info("Begin checking request Post with Text is null");
        long idDataTest = dataGenerateRandomId();
        logger.info("Post Text=null: Create a test entity with id=" + idDataTest + " and convert it to a json");
        boolean completedDataTest = randomBooleans();
        Todo todoExpectedDataTestId = new Todo(idDataTest, null, completedDataTest);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        String endPoint = baseURI + TODOS;
        logger.info("Post Text=null: Create a request Post with a test entity");
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        logger.info("Post Text=null: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Post Text=null: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Post Text=null: Checking for a negative result");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
        logger.info("End checking request Post with Text=null");
    }

    @DisplayName(" request Post with Completed is null")
    @Test
    public void shouldHaveIncorrectPostMethodWithCompletedNull() throws IOException {
        logger.info("Begin checking request Post with Completed is null");
        long idDataTest = dataGenerateRandomId();
        logger.info("Post Completed=null: Create a test entity with id=" + idDataTest + " and convert it to a json");
        Todo todoExpectedDataTestId = new Todo(idDataTest, TEXT, null);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        String endPoint = baseURI + TODOS;
        logger.info("Post Completed=null: Create a request Post with a test entity");
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        logger.info("Post Completed=null: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Post Completed=null: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Post Completed=null: Checking for a negative result");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
        logger.info("End checking request Post with Completed=null");
    }

    @DisplayName(" request Put with Id, Text, Completed is null")
    @Test
    public void shouldHaveIncorrectPutMethodWithDataTestNull() throws IOException {
        logger.info("Begin checking request Put with data is null");
        long idDataTest = dataGenerateRandomId();
        logger.info("Put Data=null: Create a test entity with id=" + idDataTest + " and convert it to a json");
        boolean completedDataTest = randomBooleans();
        Todo todoEntryDataTest = new Todo(idDataTest, TEXT, completedDataTest);
        String todoToJsonEntryDataTest = Converter.todoToJson(todoEntryDataTest);
        String endPoint = baseURI + TODOS;
        logger.info("Put Data=null: Create a test entity with id=" + idDataTest + " and convert it to a json");
        responsePost(todoToJsonEntryDataTest, endPoint);
        logger.info("Put Data=null: Create a test entity with a test entity with data is null");
        Todo todoExpectedDataTestId = new Todo(null, null, null);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        logger.info("Put Data=null: Create a request Put with a test entity with data is null");
        ValidatableResponse responseExpectedPut = responsePut(todoToJson, endPoint, idDataTest);
        logger.info("Put Data=null: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Put Data=null: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        Todo todoActual = extractingTodoFromTheListOfReceivedTodos(todoList, idDataTest);
        logger.info("Put Data=null: Checking for a negative result");
        assertAll(
                () -> softAssertions.assertThat(responseExpectedPut.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(idDataTest).isEqualTo(todoActual.getId()),
                () -> softAssertions.assertThat(TEXT).isEqualTo(todoActual.getText()),
                () -> softAssertions.assertThat(completedDataTest).isEqualTo(todoActual.getCompleted())
        );
        logger.info("Put Data=null: Removing the test entity " + idDataTest);
        deletingTestDataIdWithLoginAndPassword(endPoint, idDataTest, LOGIN, PASSWORD);
        logger.info("End checking request Put with Data=null");
    }

    @DisplayName(" request Delete with Id is missing")
    @Test
    public void shouldHaveIncorrectDeleteMethodWithIdIsMissing() throws IOException {
        logger.info("Begin checking request Delete with Id is missing");
        long idDataTest = dataGenerateRandomId();
        logger.info("Delete ID=missing: Create a test entity with id=" + idDataTest);
        boolean completedDataTest = randomBooleans();
        Todo todoEntryDataTest = new Todo(idDataTest, TEXT, completedDataTest);
        String endPoint = baseURI + TODOS;
        logger.info("Delete ID=missing: Inserting a test entity " + idDataTest);
        entryTestData(endPoint, todoEntryDataTest);
        logger.info("Delete ID=missing: Removing the test entity " + idDataTest);
        deletingTestDataIdWithLoginAndPassword(endPoint, idDataTest, LOGIN, PASSWORD);
        logger.info("Delete ID=missing: Create a request Delete with a test entity with id=missing");
        ValidatableResponse response = responseDelete(endPoint, idDataTest);
        logger.info("Delete ID=missing: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Delete ID=missing: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Delete ID=missing: Checking for a negative result");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_404),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
        logger.info("End checking request Delete with Id is missing");
    }

    @DisplayName(" request Delete with not valid Login and Password")
    @Test
    public void shouldHaveIncorrectDeleteMethodWithNotValidLoginAndPassword() throws IOException {
        logger.info("Begin checking request Delete with not valid Login and Password");
        long idDataTest = dataGenerateRandomId();
        logger.info("Delete not valid auth: Create a test entity with id=" + idDataTest);
        boolean completedDataTest = randomBooleans();
        Todo todoEntryDataTest = new Todo(idDataTest, TEXT, completedDataTest);
        String endPoint = baseURI + TODOS;
        logger.info("Delete not valid auth: Inserting a test entity " + idDataTest);
        entryTestData(endPoint, todoEntryDataTest);
        logger.info("Delete not valid auth: Create random login and password");
        String login = new Random().toString();
        String password = new Random().toString();
        logger.info("Delete not valid auth: Create a request Delete with a not valid login and password");
        ValidatableResponse response = responseDeleteWithLoginAndPassword(endPoint, idDataTest, login, password);
        logger.info("Delete not valid auth: Create a request Get with a test entity");
        ValidatableResponse validatableResponse = responseGet();
        logger.info("Delete not valid auth: Create a list of received entities");
        List<Todo> todoList = extractTodoList(validatableResponse);
        logger.info("Delete not valid auth: Checking for a negative result");
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_401),
                () -> softAssertions.assertThat(true).isEqualTo(todoList.contains(idDataTest))
        );
        logger.info("Delete not valid auth: Removing the test entity " + idDataTest);
        deletingTestDataIdWithLoginAndPassword(endPoint,idDataTest, LOGIN, PASSWORD);
        logger.info("End checking request Delete with not valid Login and Password");
    }
}
