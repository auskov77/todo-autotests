package ru.buttonone.utils;

import ru.buttonone.domain.Todo;
import ru.buttonone.service.GeneratorId;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class TodoHelper {

    public static boolean randomBooleans() {
        return new Random().nextBoolean();
    }

    public static long generatedId() {
        return GeneratorId.INSTANCE.generatedId();
    }

    public static Optional<Todo> getTodoIdFromList(List<Todo> todoList, long id) {
        return todoList.stream().filter(todo -> todo.getId() == id).findAny();
    }
}
