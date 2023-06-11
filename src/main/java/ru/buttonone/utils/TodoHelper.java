package ru.buttonone.utils;

import ru.buttonone.domain.Todo;
import ru.buttonone.service.DataGeneratorTodoId;

import java.util.List;
import java.util.Random;

public class TodoHelper {

    public static boolean randomBooleans() {
        Random booleans = new Random();
        return booleans.nextBoolean();
    }

    public static long dataGenerateRandomId() {
        return DataGeneratorTodoId.INSTANCE.todoRandomId();
    }

    public static Todo extractingTodoFromTheListOfReceivedTodos(List<Todo> todoList, long id) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }
}
