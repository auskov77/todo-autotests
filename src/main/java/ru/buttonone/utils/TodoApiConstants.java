package ru.buttonone.utils;

public enum TodoApiConstants {
    ;
    public static final int STATUS_CODE_POST_200 = 200;
    public static final int STATUS_CODE_POST_201 = 201;
    public static final int STATUS_CODE_POST_204 = 204;
    public static final String TODOS = Props.getProperty("todos");
    public static final String TEXT = "TODO";
    public static final boolean COMPLETED_TRUE = true;
    public static final boolean COMPLETED_FALSE = false;
    public static final String LOGIN = Props.getProperty("login");
    public static final String PASSWORD = Props.getProperty("password");
}
