package ru.buttonone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.buttonone.service.TodoService;
import ru.buttonone.service.TodoServiceImpl;

public class BaseTest {
    public final static TodoService TODO_SERVICE = new TodoServiceImpl();
    public final Logger logger = LoggerFactory.getLogger(BaseTest.class);

}
