package com.tpdev.joysList.constants;

public final class ApiConstants {
    private static final String VERSION = "/v1";
    private static final String BASE_BATH = "/api" + VERSION;

    // Auth endpoints
    public static final String AUTH = BASE_BATH + "/auth";
    public static final String LOGIN = "/login";
    public static final String REGISTER_USER = "/register";
    public static final String DELETE_USER = "/delete";

    // User endpoints
    public static final String USERS = BASE_BATH + "/users";
    public static final String ID = "/{id}";
    public static final String EMAIL = "/email";

     // Testing endpoint
    public static final String TEST = "/hello";

    // Ads
    public static final String ADS = BASE_BATH + "/ads";
    public static final String LIST = "/list";
    public static final String SEARCH = "/search";

}
