// ============================================
// Auth Types
// ============================================
export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  expiresIn: number;
  tokenType: string;
  userId: number;
  username: string;
  role: "USER" | "ADMIN";
}

export interface RefreshTokenRequest {
  refreshToken: string;
}

// ============================================
// User Types
// ============================================
export interface UserProfile {
  userId: number;
  username: string;
  email: string;
  role: "USER" | "ADMIN";
  bio: string | null;
  phone: string | null;
  location: string | null;
  avatarUrl: string | null;
  memberSince: string;
}

export interface UpdateProfileRequest {
  bio?: string;
  phone?: string;
  location?: string;
}

// ============================================
// Category Types
// ============================================
export interface Category {
  id: number;
  name: string;
}

export interface Subcategory {
  id: number;
  name: string;
  categoryId: number;
}

// ============================================
// Ad Types
// ============================================
export type AdType =
  | "HOUSING"
  | "FOR_SALE"
  | "JOBS"
  | "SERVICES"
  | "GIGS"
  | "RESUMES"
  | "COMMUNITY"
  | "EVENTS";

export interface AdResponse {
  id: number;
  title: string;
  description: string;
  price: number | null;
  location: string;
  subcategoryName: string;
  userId: number;
}

export interface AdRequestDto {
  adType: AdType;
  title: string;
  description: string;
  price?: number | null;
  location: string;
  hasImage?: boolean;
  postedToday?: boolean;
  isFree?: boolean;
  deliveryAvailable?: boolean;
  subcategoryId: number;

  // Housing
  numberOfBeds?: number;
  numberOfBathrooms?: number;
  catsOk?: boolean;
  dogsOk?: boolean;
  furnished?: boolean;
  housingType?: HousingType;
  laundry?: Laundry;
  parking?: Parking;
  rentPeriod?: RentPeriod;

  // For Sale
  soldBy?: SoldBy;
  condition?: ItemCondition;

  // Events
  eventTypes?: EventType[];

  // Gigs
  gigs?: GigType;
  status?: PaymentStatus;

  // Jobs
  nonProfitOrganization?: boolean;
  internship?: boolean;
  telecommutingOk?: boolean;
  employmentType?: EmploymentType;
  jobTypes?: JobType[];

  // Services
  serviceType?: ServiceType;

  // Community
  communityType?: CommunityType;
  lostOrFound?: LostAndFound;

  // Resumes
  availableMornings?: boolean;
  availableAfternoons?: boolean;
  availableEvenings?: boolean;
  availableOvernights?: boolean;
  availableWeekdays?: boolean;
  availableWeekends?: boolean;
  educationCompleted?: EducationCompleted;
}

// ============================================
// Enums
// ============================================
export type HousingType =
  | "APARTMENT"
  | "CONDO"
  | "TOWNHOUSE"
  | "HOUSE"
  | "LOFT"
  | "CABIN"
  | "STUDIO"
  | "DUPLEX"
  | "OFFICE"
  | "OTHER";

export type Laundry = "UNIT" | "HOOKUPS" | "INBUILDING" | "ONSITE" | "NOLAUNDRY";

export type Parking =
  | "CARPORT"
  | "ATTACHED_GARAGE"
  | "DETACHED_GARAGE"
  | "OFF_STREET"
  | "STREET"
  | "VALET"
  | "NOPARKING";

export type RentPeriod = "DAILY" | "WEEKLY" | "MONTHLY";

export type SoldBy = "OWNER" | "DEALER";

export type ItemCondition =
  | "NEW"
  | "LIKE_NEW"
  | "EXCELLENT"
  | "GOOD"
  | "FAIR"
  | "SALVAGE"
  | "USED";

export type EventType =
  | "FREE"
  | "FOOD_OR_DRINK"
  | "LITERARY"
  | "CHARITABLE"
  | "DANCE"
  | "SUSTAINABILITY"
  | "SINGLES"
  | "FITNESS_OR_HEALTH"
  | "COMPETITION"
  | "SALE"
  | "TECH"
  | "OUTDOOR"
  | "KID_FRIENDLY"
  | "MUSIC"
  | "CAREER"
  | "ART_OR_FILM"
  | "FEST_OR_FAIR";

export type GigType =
  | "LABOR"
  | "DOMESTIC"
  | "EVENT"
  | "COMPUTER"
  | "CREATIVE"
  | "CREW"
  | "TALENT"
  | "WRITING";

export type PaymentStatus = "PAID" | "UNPAID";

export type EmploymentType = "FULL_TIME" | "PART_TIME" | "CONTRACT" | "EMPLOYEE_CHOICE";

export type JobType =
  | "TRANSPORTATION"
  | "GENERAL_LABOR"
  | "SKILLED_TRADES_OR_ARTISAN"
  | "SALES"
  | "FOOD_BEVERAGE_HOSPITALITY"
  | "CUSTOMER_SERVICE"
  | "ET_CETERA"
  | "ADMIN_OR_OFFICE"
  | "EDUCATION_OR_TEACHING"
  | "RETAIL_OR_WHOLESALE"
  | "ACCOUNTING_OR_FINANCE"
  | "HEALTHCARE"
  | "BUSINESS_OR_MANAGEMENT"
  | "MANUFACTURING"
  | "MARKETING_OR_ADVERTISEMENT"
  | "REAL_ESTATE"
  | "SALON_SPA_FITNESS"
  | "SCIENCE_BIOTECH"
  | "SECURITY"
  | "TV_FILM_VIDEO_RADIO"
  | "LEGAL_PARALEGAL"
  | "SYSTEMS_NETWORKING"
  | "WRITING_EDITING";

export type ServiceType =
  | "AUTOMOTIVE"
  | "BEAUTY"
  | "CELL_PHONE_OR_MOBILE"
  | "COMPUTER"
  | "CYCLE"
  | "EVENT"
  | "FARM_AND_GARDEN"
  | "FINANCIAL"
  | "HEALTH_AND_WELLNESS"
  | "HOUSEHOLD"
  | "LABOR_AND_MOVING"
  | "LEGAL"
  | "LESSONS_AND_TUTORING"
  | "MARINE"
  | "PET"
  | "REAL_ESTATE"
  | "SKILLED_TRADE"
  | "SMALL_BIZ_ADS"
  | "TRAVEL_OR_VACATION"
  | "WRITE_OR_EDIT_OR_TRANSLATION";

export type CommunityType =
  | "ACTIVITY_PARTNERS"
  | "ARTISTS"
  | "CHILDCARE"
  | "GENERAL"
  | "GROUPS"
  | "LOCAL_NEWS_AND_VIEWS"
  | "LOST_AND_FOUND"
  | "MISSED_CONNECTIONS"
  | "MUSICIANS"
  | "PETS"
  | "POLITICS"
  | "RANTS_AND_RAVES"
  | "RIDESHARE"
  | "VOLUNTEERS";

export type LostAndFound = "LOST" | "FOUND";

export type EducationCompleted =
  | "LESS_THAN_HIGH_SCHOOL"
  | "HIGH_SCHOOL_OR_GED"
  | "SOME_COLLEGE"
  | "ASSOCIATES"
  | "BACHELORS"
  | "MASTERS"
  | "DOCTORAL";

// ============================================
// Pagination
// ============================================
export interface Page<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

// ============================================
// Search Params
// ============================================
export interface AdSearchParams {
  keyword?: string;
  location?: string;
  minPrice?: number;
  maxPrice?: number;
  isFree?: boolean;
  deliveryAvailable?: boolean;
  postedToday?: boolean;
  category?: AdType;
  page?: number;
  size?: number;
  sortBy?: string;
  sortDir?: "asc" | "desc";
}
