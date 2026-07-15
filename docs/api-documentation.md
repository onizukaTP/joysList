# JoysList Listing Service API Documentation

This document describes all the REST API endpoints exposed by the `listing-service`. The default base path when accessing via the API Gateway is `/api/v1` (with `/hello` being an exception).

---

## 🔐 Authentication & Authorization Rules

As configured in [SecurityConfig.java](file:///d:/Personal/Projects/joysList/services/listing-service/src/main/java/com/tpdev/joysList/security/SecurityConfig.java):
* **Public Endpoints**: `/api/v1/auth/**`, `/hello/**` (No authentication required)
* **Admin Endpoints**: `/api/v1/admin/**` (Requires role `ADMIN`)
* **User Endpoints**: `/api/v1/user/**` (Requires role `USER` or `ADMIN`)
* **Other Endpoints**: Any other request requires standard authentication via JWT header: `Authorization: Bearer <token>`

---

## 🔑 Authentication Endpoints (`/api/v1/auth`)

### 1. User Login
* **Method**: `POST`
* **Path**: `/api/v1/auth/login`
* **Access**: Public
* **Request Body** (`LoginRequest`):
  ```json
  {
    "username": "your_username",
    "password": "your_password"
  }
  ```
* **Response** (`AuthResponse`):
  ```json
  {
    "token": "jwt_access_token"
  }
  ```

### 2. User Registration
* **Method**: `POST`
* **Path**: `/api/v1/auth/register`
* **Access**: Public
* **Request Body** (`RegisterRequest`):
  ```json
  {
    "username": "desired_username",
    "email": "user@example.com",
    "password": "secure_password"
  }
  ```
* **Response** (`AuthResponse`):
  ```json
  {
    "token": "jwt_access_token"
  }
  ```

### 3. Delete User
* **Method**: `DELETE`
* **Path**: `/api/v1/auth/delete`
* **Access**: Authenticated User/Admin
* **Request Body**: `UserEntity` (JSON representation of the user entity to delete)
* **Response**: String message `Successfully deleted <username>`

---

## 👤 User Endpoints (`/api/v1/users`)

### 1. Get User by ID
* **Method**: `GET`
* **Path**: `/api/v1/users/{id}`
* **Access**: Authenticated
* **Path Variables**:
  * `id` (Long): The database ID of the user.
* **Response**: `UserEntity` JSON object.

### 2. Get User by Email
* **Method**: `GET`
* **Path**: `/api/v1/users/email`
* **Access**: Authenticated
* **Query Parameters**:
  * `email` (String): User's email address.
* **Response**: `UserEntity` JSON object.

---

## 📂 Category Management (`/api/v1/categories`)

### 1. Create Category
* **Method**: `POST`
* **Path**: `/api/v1/categories`
* **Access**: Authenticated
* **Request Body**: `Category` JSON object.
* **Response**: `category created successfully.` (HTTP 201 Created)

### 2. Get All Categories
* **Method**: `GET`
* **Path**: `/api/v1/categories`
* **Access**: Authenticated
* **Response**: List of `Category` JSON objects.

### 3. Get Category by Name
* **Method**: `GET`
* **Path**: `/api/v1/categories/{name}`
* **Access**: Authenticated
* **Path Variables**:
  * `name` (String): The category name.
* **Response**: `Category` JSON object.

### 4. Delete Category by ID
* **Method**: `DELETE`
* **Path**: `/api/v1/categories/{id}`
* **Access**: Authenticated
* **Path Variables**:
  * `id` (Long): The category ID.
* **Response**: `Category deleted successfully.`

---

## 📂 Subcategory Management (`/api/v1/subcategories`)

### 1. Get All Subcategories
* **Method**: `GET`
* **Path**: `/api/v1/subcategories`
* **Access**: Authenticated
* **Response**: List of `Subcategory` JSON objects.

### 2. Get Subcategories by Category ID
* **Method**: `GET`
* **Path**: `/api/v1/subcategories/category/{categoryId}`
* **Access**: Authenticated
* **Path Variables**:
  * `categoryId` (Long): The parent category ID.
* **Response**: List of `Subcategory` JSON objects.

### 3. Create Subcategory
* **Method**: `POST`
* **Path**: `/api/v1/subcategories`
* **Access**: Authenticated
* **Request Body**: `Subcategory` JSON object.
* **Response**: `Subcategory created successfully.` (HTTP 201 Created)

### 4. Update Subcategory
* **Method**: `PUT`
* **Path**: `/api/v1/subcategories/{id}`
* **Access**: Authenticated
* **Path Variables**:
  * `id` (Long): The subcategory ID.
* **Request Body**: `Subcategory` JSON object containing updated details.
* **Response**: `Updated successfully.` (or `Not found with the id: <id>` with HTTP 404)

### 5. Delete Subcategory
* **Method**: `DELETE`
* **Path**: `/api/v1/subcategories/{id}`
* **Access**: Authenticated
* **Path Variables**:
  * `id` (Long): The subcategory ID.
* **Response**: `Deleted successfully.` (or `Not found with the id: <id>` with HTTP 404)

---

## 📢 Generic Ad Management (`/api/v1/ads`)

### 1. Create Ad
* **Method**: `POST`
* **Path**: `/api/v1/ads`
* **Access**: Authenticated
* **Request Body** (`AdRequestDto`): JSON payload with details.
* **Response**: `Ad created successfully as <adType>` (HTTP 201 Created)

### 2. Create Multiple Ads
* **Method**: `POST`
* **Path**: `/api/v1/ads/list`
* **Access**: Authenticated
* **Request Body**: List of `AdRequestDto` JSON payloads.
* **Response**: `Ads created successfully` (HTTP 201 Created)

### 3. Get All Generic Ads
* **Method**: `GET`
* **Path**: `/api/v1/ads`
* **Access**: Authenticated
* **Response**: List of `AdResponse` DTO objects.

### 4. Search and Filter Ads
* **Method**: `GET`
* **Path**: `/api/v1/ads/search`
* **Access**: Authenticated
* **Query Parameters** (All optional unless specified):
  * `keyword` (String)
  * `location` (String)
  * `minPrice` (Double)
  * `maxPrice` (Double)
  * `isFree` (Boolean)
  * `deliveryAvailable` (Boolean)
  * `postedToday` (Boolean)
  * `category` (AdType enum)
  * `page` (Integer, default `0`)
  * `size` (Integer, default `20`)
  * `sortBy` (String, default `createdAt`)
  * `sortDir` (String, default `desc`)
* **Response**: Paginated `Page<AdResponse>` JSON object.

### 5. Update Generic Ad
* **Method**: `PUT`
* **Path**: `/api/v1/ads/{id}`
* **Access**: Authenticated
* **Path Variables**:
  * `id` (Long): The ad ID.
* **Request Body**: `AdRequestDto` payload.
* **Response**: `Ad <id> updated successfully`

### 6. Delete Generic Ad
* **Method**: `DELETE`
* **Path**: `/api/v1/ads/{id}`
* **Access**: Authenticated
* **Path Variables**:
  * `id` (Long): The ad ID.
* **Response**: `Ad <id> deleted successfully`

---

## 📑 Category-Specific Ad Management

Each category endpoint inherits general CRUD endpoints (`GET /{id}`, `GET`, `DELETE /{id}`) from `BaseAdController` and overrides creation, as well as providing `/filter` endpoints.

### Base Endpoints (Inherited)
* **Get Ad by ID**: `GET /api/v1/ads/{category}/{id}`
* **Get All Ads**: `GET /api/v1/ads/{category}`
* **Delete Ad**: `DELETE /api/v1/ads/{category}/{id}` (HTTP 204 No Content)

---

### 💼 Services (`/api/v1/ads/services`)

* **Create Service Ad**: `POST /api/v1/ads/services`
  * **Request Body**: `ServiceAd` JSON object.
* **Filter Service Ads**: `GET /api/v1/ads/services/filter`
  * **Query Parameters**:
    * `serviceType` (ServiceType enum): Option to filter by service type.

---

### 📄 Resumes (`/api/v1/ads/resumes`)

* **Create Resume Ad**: `POST /api/v1/ads/resumes`
  * **Request Body**: `ResumeAd` JSON object.
* **Filter Resume Ads**: `GET /api/v1/ads/resumes/filter`
  * **Query Parameters**:
    * `availableMornings` (Boolean)
    * `availableAfternoons` (Boolean)
    * `availableEvenings` (Boolean)
    * `availableOvernights` (Boolean)
    * `availableWeekdays` (Boolean)
    * `availableWeekends` (Boolean)
    * `educationCompleted` (EducationCompleted enum)

---

### 👔 Jobs (`/api/v1/ads/jobs`)

* **Create Job Ad**: `POST /api/v1/ads/jobs`
  * **Request Body**: `JobAd` JSON object.
* **Filter Job Ads**: `GET /api/v1/ads/jobs/filter`
  * **Query Parameters**:
    * `nonProfitOrganization` (Boolean)
    * `internship` (Boolean)
    * `teleCommutingOk` (Boolean)
    * `employmentType` (EmploymentType enum)
    * `jobType` (JobType enum)
    * `jobTypes` (List of JobType enums)

---

### 🏠 Housing (`/api/v1/ads/housing`)

* **Create Housing Ad**: `POST /api/v1/ads/housing`
  * **Request Body**: `HousingAd` JSON object.
* **Filter Housing Ads**: `GET /api/v1/ads/housing/filter`
  * **Query Parameters**:
    * `type` (HousingType enum)
    * `minBeds` (Byte)
    * `minBaths` (Byte)
    * `furnished` (Boolean)
    * `catsOk` (Boolean)
    * `dogsOk` (Boolean)
    * `sqft` (Integer)
    * `privateRoom` (Boolean)
    * `privateBath` (Boolean)
    * `noSmoking` (Boolean)
    * `wheelChairAccessible` (Boolean)
    * `airConditioning` (Boolean)
    * `evCharging` (Boolean)
    * `noBrokerFee` (Boolean)
    * `noApplicationFee` (Boolean)
    * `rentPeriod` (RentPeriod enum)
    * `laundry` (Laundry enum)
    * `parking` (Parking enum)

---

### 🎸 Gigs (`/api/v1/ads/gigs`)

* **Create Gig Ad**: `POST /api/v1/ads/gigs`
  * **Request Body**: `GigsAd` JSON object.
* **Filter Gig Ads**: `GET /api/v1/ads/gigs/filter`
  * **Query Parameters**:
    * `gigs` (Gigs enum)
    * `status` (PaymentStatus enum)

---

### 🛒 For Sale (`/api/v1/ads/for_sale`)

* **Create For Sale Ad**: `POST /api/v1/ads/for_sale`
  * **Request Body**: `ForSaleAd` JSON object.
* **Filter For Sale Ads**: `GET /api/v1/ads/for_sale/filter`
  * **Query Parameters**:
    * `soldBy` (SoldBy enum)
    * `condition` (Condition enum)

---

### 📅 Events (`/api/v1/ads/events`)

* **Create Event Ad**: `POST /api/v1/ads/events`
  * **Request Body**: `EventAd` JSON object.
* **Filter Event Ads**: `GET /api/v1/ads/events/filter`
  * **Query Parameters**:
    * `eventType` (EventType enum)
    * `eventTypes` (List of EventType enums)

---

### 👥 Community (`/api/v1/ads/community`)

* **Create Community Ad**: `POST /api/v1/ads/community`
  * **Request Body**: `CommunityAd` JSON object.
* **Filter Community Ads**: `GET /api/v1/ads/community/filter`
  * **Query Parameters**:
    * `communityType` (CommunityType enum)
    * `lostOrFound` (LostAndFound enum)

---

## 🧪 Testing Endpoint (`/hello`)

* **Method**: `GET`
* **Path**: `/hello`
* **Access**: Public
* **Response**: A plain-text welcome message containing the current server system date and time.
