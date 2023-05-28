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
        long idDataTest = -1;
        boolean completedDataTest = randomBooleans();
        Todo todoExpectedDataTestId = new Todo(idDataTest, TEXT, completedDataTest);
        entryTestData(baseURI + TODOS, todoExpectedDataTestId);
        ValidatableResponse validatableResponse = responseGet();
        List<Todo> todoList = extractTodoList(validatableResponse);
        assertAll(
                () -> softAssertions.assertThat(validatableResponse.extract().statusCode()).isEqualTo(STATUS_CODE_200),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
    }

    @DisplayName(" request Post with ID<0")
    @Test
    public void shouldHaveIncorrectMethodPostWithIdLessThenZero() throws IOException {
        long idDataTest = -1;
        boolean completedDataTest = randomBooleans();
        Todo todoExpectedDataTestId = new Todo(idDataTest, TEXT, completedDataTest);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        String endPoint = baseURI + TODOS;
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        ValidatableResponse validatableResponse = responseGet();
        List<Todo> todoList = extractTodoList(validatableResponse);
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
    }

    @DisplayName(" request Post with ID is null")
    @Test
    public void shouldHaveIncorrectMethodPutWithIdIsNull() throws IOException {
        boolean completedDataTest = randomBooleans();
        Todo todoExpectedDataTestId = new Todo(null, TEXT, completedDataTest);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        String endPoint = baseURI + TODOS;
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        ValidatableResponse validatableResponse = responseGet();
        List<Todo> todoList = extractTodoList(validatableResponse);
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(null))
        );
    }

    @DisplayName(" request Post with text is null")
    @Test
    public void shouldHaveIncorrectPostMethodWithTextNull() throws IOException {
        long idDataTest = dataGenerateRandomId();
        boolean completedDataTest = randomBooleans();
        Todo todoExpectedDataTestId = new Todo(idDataTest, null, completedDataTest);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        String endPoint = baseURI + TODOS;
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        ValidatableResponse validatableResponse = responseGet();
        List<Todo> todoList = extractTodoList(validatableResponse);
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
    }

    @DisplayName(" request Post with completed is null")
    @Test
    public void shouldHaveIncorrectPostMethodWithCompletedNull() throws IOException {
        long idDataTest = dataGenerateRandomId();
        Todo todoExpectedDataTestId = new Todo(idDataTest, TEXT, null);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        String endPoint = baseURI + TODOS;
        ValidatableResponse response = responsePost(todoToJson, endPoint);
        ValidatableResponse validatableResponse = responseGet();
        List<Todo> todoList = extractTodoList(validatableResponse);
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
    }

    @DisplayName(" request Put with Id, Text, Completed is null")
    @Test
    public void shouldHaveIncorrectPutMethodWithDataTestNull() throws IOException {
        long idDataTest = dataGenerateRandomId();
        boolean completedDataTest = randomBooleans();
        Todo todoEntryDataTest = new Todo(idDataTest, TEXT, completedDataTest);
        String todoToJsonEntryDataTest = Converter.todoToJson(todoEntryDataTest);
        String endPoint = baseURI + TODOS;
        responsePost(todoToJsonEntryDataTest, endPoint);
        Todo todoExpectedDataTestId = new Todo(null, null, null);
        String todoToJson = Converter.todoToJson(todoExpectedDataTestId);
        ValidatableResponse responseExpectedPut = responsePut(todoToJson, endPoint, idDataTest);
        ValidatableResponse validatableResponse = responseGet();
        List<Todo> todoList = extractTodoList(validatableResponse);
        Todo todoActual = extractingTodoFromTheListOfReceivedTodos(todoList, idDataTest);
        assertAll(
                () -> softAssertions.assertThat(responseExpectedPut.extract().statusCode()).isEqualTo(STATUS_CODE_400),
                () -> softAssertions.assertThat(idDataTest).isEqualTo(todoActual.getId()),
                () -> softAssertions.assertThat(TEXT).isEqualTo(todoActual.getText()),
                () -> softAssertions.assertThat(completedDataTest).isEqualTo(todoActual.getCompleted())
        );
        deletingTestDataId(endPoint, idDataTest);
    }

    @DisplayName(" request Delete with Id is missing")
    @Test
    public void shouldHaveIncorrectDeleteMethodWithIdIsMissing() throws IOException {
        long idDataTest = dataGenerateRandomId();
        boolean completedDataTest = randomBooleans();
        Todo todoEntryDataTest = new Todo(idDataTest, TEXT, completedDataTest);
        String endPoint = baseURI + TODOS;
        entryTestData(endPoint, todoEntryDataTest);
        deletingTestDataId(endPoint, idDataTest);
        ValidatableResponse response = responseDelete(endPoint, idDataTest);
        ValidatableResponse validatableResponse = responseGet();
        List<Todo> todoList = extractTodoList(validatableResponse);
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_404),
                () -> softAssertions.assertThat(false).isEqualTo(todoList.contains(idDataTest))
        );
    }

    @DisplayName(" request Delete with not valid Login and Password")
    @Test
    public void shouldHaveIncorrectDeleteMethodWithNotValidLoginAndPassword() throws IOException {
        long idDataTest = dataGenerateRandomId();
        boolean completedDataTest = randomBooleans();
        Todo todoEntryDataTest = new Todo(idDataTest, TEXT, completedDataTest);
        String endPoint = baseURI + TODOS;
        entryTestData(endPoint, todoEntryDataTest);
        String login = new Random().toString();
        String password = new Random().toString();
        ValidatableResponse response = responseDeleteWithLoginAndPassword(endPoint, idDataTest, login, password);
        ValidatableResponse validatableResponse = responseGet();
        List<Todo> todoList = extractTodoList(validatableResponse);
        assertAll(
                () -> softAssertions.assertThat(response.extract().statusCode()).isEqualTo(STATUS_CODE_401),
                () -> softAssertions.assertThat(true).isEqualTo(todoList.contains(idDataTest))
        );
        deletingTestDataId(endPoint, idDataTest);
    }
}
