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

    // Categories
    public static final String CATEGORIES = BASE_BATH + "/categories";
    public static final String NAME = "/{name}";

    // Subcategories
    public static final String SUBCATEGORIES = BASE_BATH + "/subcategories";
    public static final String CATEGORY_BY_ID = "/category/{categoryId}";

    // Sub-mappings
    public static final String FILTER = "/filter";

    // Category Ads
    public static final String ADS_SERVICES = ADS + "/services";
    public static final String ADS_RESUMES = ADS + "/resumes";
    public static final String ADS_JOBS = ADS + "/jobs";
    public static final String ADS_HOUSING = ADS + "/housing";
    public static final String ADS_GIGS = ADS + "/gigs";
    public static final String ADS_FOR_SALE = ADS + "/for_sale";
    public static final String ADS_EVENTS = ADS + "/events";
    public static final String ADS_COMMUNITY = ADS + "/community";
}
