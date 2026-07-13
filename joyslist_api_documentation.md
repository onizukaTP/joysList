# JoysList API Documentation

**Base URL:** `http://localhost:8080`  
**Auth:** Bearer token (JWT) — include in all protected endpoints as:
```
Authorization: Bearer <token>
```

---

## Authentication

### POST `/api/v1/auth/register`
Register a new user. Triggers a welcome email via Kafka.

**Auth required:** No

**Request body:**
```json
{
  "username": "tharun",
  "email": "tharun@example.com",
  "password": "Password@123"
}
```

**Response `200`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Errors:**
- `400` — Username already exists
- `400` — Email already exists

---

### POST `/api/v1/auth/login`
Login with existing credentials.

**Auth required:** No

**Request body:**
```json
{
  "username": "tharun",
  "password": "Password@123"
}
```

**Response `200`:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Errors:**
- `401` — Bad credentials

---

### DELETE `/api/v1/auth/delete`
Delete a user account.

**Auth required:** No ⚠️ *(should be secured — known issue)*

**Request body:**
```json
{
  "id": 1,
  "username": "tharun",
  "email": "tharun@example.com"
}
```

**Response `200`:** `"Successfully deleted tharun"`

---

## Users

### GET `/api/v1/user/{id}`
Get user by ID.

**Auth required:** Yes (USER or ADMIN)

**Response `200`:**
```json
{
  "id": 1,
  "username": "tharun",
  "email": "tharun@example.com",
  "role": "USER"
}
```

---

### GET `/api/v1/user/email?email={email}`
Get user by email address.

**Auth required:** Yes (USER or ADMIN)

**Query params:**
| Param | Type   | Required |
|-------|--------|----------|
| email | String | Yes      |

**Response `200`:** Same as above.

---

## Ads

### POST `/api/v1/ads`
Create a new ad. Dispatched to the correct category service via `AdFacadeService`.  
Triggers an `AdCreatedEvent` on Kafka → notification email sent.

**Auth required:** Yes

**Request body:**
```json
{
  "adType": "HOUSING",
  "title": "2BHK Apartment in Anna Nagar",
  "description": "Spacious apartment, fully furnished",
  "price": 25000.0,
  "location": "Chennai",
  "isFree": false,
  "deliveryAvailable": false,
  "postedToday": true,
  "subcategoryId": 1,

  "numberOfBeds": 2,
  "numberOfBathrooms": 2,
  "furnished": true,
  "housingType": "APARTMENT",
  "laundry": "IN_UNIT",
  "parking": "COVERED",
  "rentPeriod": "MONTHLY",
  "catsOk": false,
  "dogsOk": false
}
```

**`adType` values:**
`HOUSING` | `FOR_SALE` | `EVENTS` | `GIGS` | `JOBS` | `RESUMES` | `SERVICES` | `COMMUNITY`

**Response `201`:** `"Ad created successfully as HOUSING"`

---

### POST `/api/v1/ads/list`
Create multiple ads in one request.

**Auth required:** Yes

**Request body:** Array of `AdRequestDto` objects (same structure as above).

**Response `201`:** `"Ads created successfully"`

---

### GET `/api/v1/ads`
Get all ads (cached in Redis, TTL: 10 minutes).

**Auth required:** Yes

**Response `200`:**
```json
[
  {
    "id": 1,
    "title": "2BHK Apartment in Anna Nagar",
    "description": "Spacious apartment, fully furnished",
    "price": 25000.0,
    "location": "Chennai",
    "adType": "HOUSING",
    "isFree": false,
    "postedToday": true
  }
]
```

---

### PUT `/api/v1/ads/{id}`
Update base fields of an existing ad. Evicts Redis cache.

**Auth required:** Yes

**Path param:** `id` — Ad ID

**Request body:**
```json
{
  "title": "Updated Title",
  "description": "Updated description",
  "price": 20000.0,
  "location": "Bangalore"
}
```

**Response `200`:** `"Ad 1 updated successfully"`

**Errors:**
- `404` — Ad not found

---

### DELETE `/api/v1/ads/{id}`
Delete an ad. Evicts Redis cache. Triggers an `AdDeletedEvent` on Kafka → notification email sent.

**Auth required:** Yes

**Path param:** `id` — Ad ID

**Response `200`:** `"Ad 1 deleted successfully"`

**Errors:**
- `404` — Ad not found

---

### GET `/api/v1/ads/search`
Search ads across all categories with filters and pagination.

**Auth required:** Yes

**Query params:**
| Param             | Type    | Required | Description                              |
|-------------------|---------|----------|------------------------------------------|
| keyword           | String  | No       | Matches title or description             |
| location          | String  | No       | Partial match on location                |
| minPrice          | Double  | No       | Minimum price                            |
| maxPrice          | Double  | No       | Maximum price                            |
| isFree            | Boolean | No       | Filter free ads                          |
| deliveryAvailable | Boolean | No       | Filter ads with delivery                 |
| postedToday       | Boolean | No       | Filter ads posted today                  |
| category          | AdType  | No       | One of the 8 ad types                    |
| page              | Integer | No       | Page number (default: 0)                 |
| size              | Integer | No       | Page size (default: 20)                  |
| sortBy            | String  | No       | Field to sort by (default: `createdAt`)  |
| sortDir           | String  | No       | `asc` or `desc` (default: `desc`)        |

**Example:**
```
GET /api/v1/ads/search?keyword=bike&location=Chennai&maxPrice=5000&category=FOR_SALE&page=0&size=20
```

**Response `200`:**
```json
{
  "content": ["..."],
  "totalElements": 42,
  "totalPages": 3,
  "number": 0,
  "size": 20
}
```

---

## Ad Images

### POST `/api/v1/ads/{adId}/images`
Upload images for an ad. Max 5 images per ad. Stored on AWS S3.

**Auth required:** Yes

**Path param:** `adId` — Ad ID

**Request:** `multipart/form-data`
| Field | Type           | Required |
|-------|----------------|----------|
| files | List of files  | Yes      |

**Accepted types:** `image/jpeg`, `image/png`, `image/webp`  
**Max file size:** 5MB per file | 25MB per request

**Response `200`:**
```json
[
  "https://joyslist-images.s3.amazonaws.com/ads/1/uuid1.jpg",
  "https://joyslist-images.s3.amazonaws.com/ads/1/uuid2.png"
]
```

**Errors:**
- `400` — Exceeds 5 image limit
- `400` — Invalid file type
- `404` — Ad not found

---

### GET `/api/v1/ads/{adId}/images`
Get all image URLs for an ad.

**Auth required:** Yes

**Response `200`:**
```json
[
  "https://joyslist-images.s3.amazonaws.com/ads/1/uuid1.jpg",
  "https://joyslist-images.s3.amazonaws.com/ads/1/uuid2.png"
]
```

---

### DELETE `/api/v1/ads/images/{imageId}`
Delete a specific image by ID. Removes from S3 and database.

**Auth required:** Yes

**Path param:** `imageId` — Image ID (from DB, not URL)

**Response `200`:** `"Image deleted successfully"`

**Errors:**
- `404` — Image not found

---

## Category-Specific Ad Filtering

All filter endpoints return `200` with a list of typed ads.  
All params are optional — omitting a param disables that filter.

**Auth required:** Yes (all filter endpoints)

---

### GET `/api/v1/ads/housing/filter`
Filter housing ads.

| Param               | Type        | Values / Notes                                    |
|---------------------|-------------|---------------------------------------------------|
| type                | HousingType | `APARTMENT`, `HOUSE`, `CONDO`, `TOWNHOUSE`, `ROOM`|
| minBeds             | Byte        | Minimum number of bedrooms                        |
| minBaths            | Byte        | Minimum number of bathrooms                       |
| furnished           | Boolean     |                                                   |
| catsOk              | Boolean     |                                                   |
| dogsOk              | Boolean     |                                                   |
| sqft                | Integer     | Minimum square footage                            |
| privateRoom         | Boolean     |                                                   |
| privateBath         | Boolean     |                                                   |
| noSmoking           | Boolean     |                                                   |
| wheelChairAccessible| Boolean     |                                                   |
| airConditioning     | Boolean     |                                                   |
| evCharging          | Boolean     |                                                   |
| noBrokerFee         | Boolean     |                                                   |
| noApplicationFee    | Boolean     |                                                   |
| rentPeriod          | RentPeriod  | `MONTHLY`, `WEEKLY`, `DAILY`                      |
| laundry             | Laundry     | `IN_UNIT`, `IN_BUILDING`, `NONE`                  |
| parking             | Parking     | `COVERED`, `STREET`, `NONE`                       |

**Example:**
```
GET /api/v1/ads/housing/filter?type=APARTMENT&minBeds=2&furnished=true&catsOk=true
```

---

### GET `/api/v1/ads/for_sale/filter`

| Param     | Type      | Values                               |
|-----------|-----------|--------------------------------------|
| soldBy    | SoldBy    | `OWNER`, `DEALER`                    |
| condition | Condition | `NEW`, `LIKE_NEW`, `GOOD`, `FAIR`, `SALVAGE` |

---

### GET `/api/v1/ads/events/filter`

| Param      | Type            | Values                                         |
|------------|-----------------|------------------------------------------------|
| eventType  | EventType       | `MUSIC`, `SPORTS`, `ARTS`, `FOOD`, `TECH`, ... |
| eventTypes | List[EventType] | Filter by multiple types                       |

---

### GET `/api/v1/ads/gigs/filter`

| Param  | Type          | Values                              |
|--------|---------------|-------------------------------------|
| gigs   | Gigs          | `CREATIVE`, `LABOR`, `TALENT`, ...  |
| status | PaymentStatus | `PAID`, `UNPAID`, `NEGOTIABLE`      |

---

### GET `/api/v1/ads/jobs/filter`

| Param                | Type           | Values                              |
|----------------------|----------------|-------------------------------------|
| nonProfitOrganization| Boolean        |                                     |
| internship           | Boolean        |                                     |
| teleCommutingOk      | Boolean        |                                     |
| employmentType       | EmploymentType | `FULL_TIME`, `PART_TIME`, `CONTRACT`|
| jobType              | JobType        | Single job type filter              |
| jobTypes             | List[JobType]  | Multi job type filter               |

---

### GET `/api/v1/ads/resumes/filter`

| Param               | Type               | Values                                          |
|---------------------|--------------------|-------------------------------------------------|
| availableMornings   | Boolean            |                                                 |
| availableAfternoons | Boolean            |                                                 |
| availableEvenings   | Boolean            |                                                 |
| availableOvernights | Boolean            |                                                 |
| availableWeekdays   | Boolean            |                                                 |
| availableWeekends   | Boolean            |                                                 |
| educationCompleted  | EducationCompleted | `HIGH_SCHOOL`, `BACHELORS`, `MASTERS`, `PHD`    |

---

### GET `/api/v1/ads/services/filter`

| Param       | Type        | Values                                             |
|-------------|-------------|----------------------------------------------------|
| serviceType | ServiceType | `AUTOMOTIVE`, `BEAUTY`, `COMPUTER`, `LEGAL`, ...   |

---

### GET `/api/v1/ads/community/filter`

| Param         | Type          | Values                              |
|---------------|---------------|-------------------------------------|
| communityType | CommunityType | `ACTIVITIES`, `CHILDCARE`, `PETS`, `GENERAL`, ... |
| lostOrFound   | LostAndFound  | `LOST`, `FOUND`                     |

---

## Categories

### GET `/api/v1/categories`
Get all categories (auto-seeded on startup).

**Auth required:** Yes

**Response `200`:**
```json
[
  { "id": 1, "name": "HOUSING" },
  { "id": 2, "name": "FOR_SALE" }
]
```

---

### GET `/api/v1/categories/{name}`
Get a category by name.

**Auth required:** Yes

---

### POST `/api/v1/categories`
Create a category manually.

**Auth required:** Yes (ADMIN only in future — currently unguarded)

**Request body:**
```json
{ "name": "HOUSING" }
```

---

### DELETE `/api/v1/categories/{id}`
Delete a category by ID.

**Auth required:** Yes

---

## Subcategories

### GET `/api/v1/subcategories`
Get all subcategories.

**Auth required:** Yes

---

### GET `/api/v1/subcategories/category/{categoryId}`
Get subcategories under a specific category.

**Auth required:** Yes

---

### POST `/api/v1/subcategories`
Create a subcategory.

**Auth required:** Yes

**Request body:**
```json
{
  "name": "Apartments",
  "description": "Apartment listings",
  "category": { "id": 1 }
}
```

---

### PUT `/api/v1/subcategories/{id}`
Update a subcategory.

**Auth required:** Yes

---

### DELETE `/api/v1/subcategories/{id}`
Delete a subcategory.

**Auth required:** Yes

---

## Notification Service

### GET `/test/mail`
Send a test email to the configured `MAIL_USERNAME`.  
Dev-only endpoint — should be disabled in production via `@Profile("dev")`.

**Auth required:** No

**Response `200`:** `"Mail Sent Successfully"`

---

## Known Issues & TODOs

| # | Issue | Impact |
|---|-------|--------|
| 1 | `DELETE /api/v1/auth/delete` is not secured | Anyone can delete any user |
| 2 | Category filter endpoints (`/housing`, `/for_sale`, etc.) return full entity including DB internals — no DTO | Leaks internal fields |
| 3 | `GET /api/v1/ads` returns all ads with no pagination | Will be slow at scale |
| 4 | `userEmail` not included in `AdCreatedEvent` / `AdDeletedEvent` | Notification email goes to hardcoded sender address, not the ad poster |
| 5 | `/test/mail` not gated behind `@Profile("dev")` | Test endpoint exposed in production |