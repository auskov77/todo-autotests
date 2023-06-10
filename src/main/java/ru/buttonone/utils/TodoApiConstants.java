package ru.buttonone.utils;

public enum TodoApiConstants {
    ;
    public static final int STATUS_CODE_200 = 200;
    public static final int STATUS_CODE_201 = 201;
    public static final int STATUS_CODE_204 = 204;
    public static final int STATUS_CODE_400 = 400;
    public static final int STATUS_CODE_401 = 401;
    public static final int STATUS_CODE_404 = 404;
    public static final String BASE_URI = Props.getProperty("base_uri");
    public static final String DEFAULT_ENDPOINT = BASE_URI + "/todos";
    public static final String DEFAULT_TEXT = "TODO";
    public static final boolean DEFAULT_COMPLETED_TRUE = true;
    public static final boolean DEFAULT_COMPLETED_FALSE = false;
    public static final String LOGIN = Props.getProperty("login");
    public static final String PASSWORD = Props.getProperty("password");
}
