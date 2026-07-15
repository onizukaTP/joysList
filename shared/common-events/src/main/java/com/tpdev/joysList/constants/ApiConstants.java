package com.tpdev.joysList.constants;

/**
 * Shared API route constants for all JoysList microservices.
 * Defines uniform routing conventions, versioning patterns, and sub-resource paths.
 */
public final class ApiConstants {
    private ApiConstants() {}

    private static final String VERSION = "/v1";
    private static final String BASE_PATH = "/api" + VERSION;

    // ==========================================
    // Authentication Endpoints
    // ==========================================
    public static final String AUTH = BASE_PATH + "/auth";
    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String REFRESH = "/refresh";
    public static final String LOGOUT = "/logout";
    public static final String DELETE_USER = "/delete";

    // ==========================================
    // User & Profile Endpoints
    // ==========================================
    public static final String USERS = BASE_PATH + "/users";
    public static final String ID = "/{id}";
    public static final String EMAIL = "/email";
    public static final String PROFILE = ID + "/profile";
    public static final String AVATAR = ID + "/avatar";

    // ==========================================
    // Testing & Sandbox Endpoints
    // ==========================================
    public static final String TEST = "/hello";

    // ==========================================
    // Classified Ads Endpoints
    // ==========================================
    public static final String ADS = BASE_PATH + "/ads";
    public static final String LIST = "/list";
    public static final String SEARCH = "/search";

    // ==========================================
    // Category Management
    // ==========================================
    public static final String CATEGORIES = BASE_PATH + "/categories";
    public static final String NAME = "/{name}";

    // ==========================================
    // Subcategory Management
    // ==========================================
    public static final String SUBCATEGORIES = BASE_PATH + "/subcategories";
    public static final String CATEGORY_BY_ID = "/category/{categoryId}";

    // ==========================================
    // Category-Specific Ad Mappings
    // ==========================================
    public static final String FILTER = "/filter";
    public static final String ADS_SERVICES = ADS + "/services";
    public static final String ADS_RESUMES = ADS + "/resumes";
    public static final String ADS_JOBS = ADS + "/jobs";
    public static final String ADS_HOUSING = ADS + "/housing";
    public static final String ADS_GIGS = ADS + "/gigs";
    public static final String ADS_FOR_SALE = ADS + "/for_sale";
    public static final String ADS_EVENTS = ADS + "/events";
    public static final String ADS_COMMUNITY = ADS + "/community";
}
