package ru.buttonone;

import ru.buttonone.domain.Todo;
import ru.buttonone.service.PrepareTodoServiceImpl;
import ru.buttonone.utils.Props;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.buttonone.service.Converter.todoToJson;

public class Main {

    public static void main(String[] args) throws IOException {
        String base_uri = Props.getProperty("base_uri");
        String todos = Props.getProperty("todos");
//        String jsonBodyId1 = Props.getProperty("requestBodyTodoId1");
//        String jsonBodyId2 = Props.getProperty("requestBodyTodoId2");
//        String jsonBodyId4 = Props.getProperty("requestBodyTodoId4");
//        String jsonBodyId4Put = Props.getProperty("requestBodyTodoId4Put");

//        int id1 = Integer.parseInt(Props.getProperty("id1"));
//        int id2 = Integer.parseInt(Props.getProperty("id2"));
//        int id4 = Integer.parseInt(Props.getProperty("id4"));

//        String todo_id4 = Props.getProperty("todo_id4");

        PrepareTodoServiceImpl todoService = new PrepareTodoServiceImpl();

        String endPoint = base_uri + todos;
//        System.out.println("endPoint = " + endPoint);
        Todo todoBody1 = new Todo(1, "TODO", true);
//        System.out.println("todoBody1 = " + todoBody1);
        Todo todoBody2 = new Todo(2, "TODO", false);
//        System.out.println("todoBody2 = " + todoBody2);
        String jsonBody1 = todoToJson(todoBody1);
//        System.out.println("jsonBody1 = " + jsonBody1);
        String jsonBody2 = todoToJson(todoBody2);
//        System.out.println("jsonBody2 = " + jsonBody2);

//        todoService.requestPostMethodRestAssured(endPoint, jsonBody1);
//        todoService.requestPostMethodRestAssured(endPoint, jsonBody2);

//        todoService.requestDeleteMethod(endPoint, 1);
//        todoService.requestDeleteMethod(endPoint, 2);

        List<Long> list = new ArrayList<>();
        Todo todo = new Todo();

        for (int i = 0; i < 2; i++) {
//            System.out.println("randomId() = " + randomId());
            long id = randomId();
            list.add(id);
        }


//        System.out.println("list = " + list);

//        try {
//            String endPoint = base_uri;
//            System.out.println("endPoint = " + endPoint);
//            System.out.println("jsonBodyId4 = " + jsonBodyId4);
//            todoService.requestPostMethod(base_uri, jsonBodyId4, PrepareTodoServiceImpl.getDefaultClient());
//            HttpResponse response = PrepareTodoServiceImpl.getResponse();
//            System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        HttpResponse response = PrepareTodoServiceImpl.getResponse();

//        todoService.requestGetMethod(endPoint, PrepareTodoServiceImpl.getHttpClient());
//        System.out.println("response = " + response);
//        System.out.println("response.getEntity() = " + response.getEntity());
//        System.out.println("response.getLocale() = " + response.getLocale());
//        System.out.println("response.getStatusLine() = " + response.getStatusLine());
//        todoService.requestPostMethod(endPoint, jsonBodyId1, PrepareTodoServiceImpl.getHttpClient());
//        System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        todoService.requestGetMethod(endPoint, PrepareTodoServiceImpl.getDefaultClient());
//        System.out.println("response = " + response);


//        System.out.println(todoService.requestGetMethod());
//        todoService.requestPostMethod(4, "TODO", true);
//        System.out.println(todoService.requestGetMethod());
//        System.out.println();
//        int id = Integer.parseInt(Props.getProperty("id4"));
//        String text = "TODO";
//        System.out.println(todoService.requestPutMethod(id, text, false));

//try {
//    todoService.requestGetMethod(endPoint, PrepareTodoServiceImpl.getDefaultClient());
//    HttpResponse response = PrepareTodoServiceImpl.getResponse();
//    System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//    System.out.println("response.getEntity().getContent() = " + response.getEntity().getContent());
//    System.out.println("response.getEntity() = " + response.getEntity());
//    System.out.println("response.getEntity().getContent().read() = " + response.getEntity().getContent().read());
//    System.out.println("response.getStatusLine().getReasonPhrase() = " + response.getStatusLine().getReasonPhrase());
//    System.out.println("response.getClass().getResource(endPoint) = " + response.getClass().getResource(endPoint));
//
//} catch (IOException e) {
//    e.printStackTrace();
//}

//        try {
//            String endPoint = base_uri;
//            System.out.println("endPoint = " + endPoint);
//            System.out.println("jsonBodyId4 = " + jsonBodyId4);
//            todoService.requestPostMethod(base_uri, jsonBodyId4, PrepareTodoServiceImpl.getDefaultClient());
//            HttpResponse response = PrepareTodoServiceImpl.getResponse();
//            System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//


//        try {
//            String endPoint = base_uri;
//            System.out.println("endPoint = " + endPoint);
//            System.out.println("jsonBodyId1 = " + jsonBodyId1);
//            todoService.requestPostMethod(base_uri, jsonBodyId1);
//            HttpResponse response = PrepareTodoServiceImpl.getResponse();
//            System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String endPoint = base_uri;
//            System.out.println("endPoint = " + endPoint);
//            System.out.println("jsonBodyId2 = " + jsonBodyId2);
//            todoService.requestPostMethod(base_uri, jsonBodyId2);
//            HttpResponse response = PrepareTodoServiceImpl.getResponse();
//            System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String endPoint = base_uri;
//            System.out.println("endPoint = " + endPoint);
//            todoService.requestGetMethod(endPoint);
//            HttpResponse response = PrepareTodoServiceImpl.getResponse();
//            System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            String endPoint = base_uri;
//            System.out.println("endPoint = " + endPoint);
//            System.out.println("jsonBodyId4 = " + jsonBodyId4);
//            todoService.requestPostMethod(base_uri, jsonBodyId4);
//            HttpResponse response = PrepareTodoServiceImpl.getResponse();
//            System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String endPoint = base_uri + todo_id4;
//            System.out.println("endPoint = " + endPoint);
//            System.out.println("jsonBodyId4Put = " + jsonBodyId4Put);
//            todoService.requestPutMethod(endPoint, jsonBodyId4Put);
//            HttpResponse response = PrepareTodoServiceImpl.getResponse();
//            System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            String endPoint = base_uri + todo_id4;
//            System.out.println("endPoint = " + endPoint);
//            todoService.requestDeleteMethod(endPoint);
//            HttpResponse response = PrepareTodoServiceImpl.getResponse();
//            System.out.println("response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public static long randomId(){
        return (long) Math.ceil((float) Math.random() * (100 + 1));
    }

}
