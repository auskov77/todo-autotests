package ru.buttonone.service;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static ru.buttonone.utils.TodoApiConstants.LOGIN;
import static ru.buttonone.utils.TodoApiConstants.PASSWORD;

public class PrepareTodoServiceImpl implements PrepareTodoService {

    @Override
    public void requestGetMethod(String endPoint) {
            given()
                    .when()
                    .get(endPoint)
                    .then();
    }

    @Override
    public void requestPostMethod(String endPoint, String jsonBody){
        given()
                .contentType(ContentType.JSON)
                .and()
                .body(jsonBody)
                .when()
                .post(endPoint)
                .then();
    }

    @Override
    public void requestDeleteMethod(String endPoint, long id) {
        given()
                .auth()
                .preemptive()
                .basic(LOGIN, PASSWORD)
                .when()
                .delete(endPoint + "/" + id)
                .then();
    }

    @Override
    public void requestDeleteMethodWithLoginAndPassword(String endPoint, long id, String login, String password) {
        given()
                .auth()
                .preemptive()
                .basic(login, password)
                .when()
                .delete(endPoint + "/" + id)
                .then();
    }
}
