package ru.buttonone.service;

public interface PrepareTodoService {
    void requestPostMethod(String endPoint, String jsonBody);
    void requestGetMethod(String endPoint);
    void requestDeleteMethod(String endPoint, long id);
    void requestDeleteMethodWithLoginAndPassword(String endPoint, long id, String login, String password);
}
