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
export type HousingType = "APARTMENT" | "CONDO" | "HOUSE" | "TOWNHOUSE" | "DUPLEX" | "ROOM";
export type Laundry = "IN_UNIT" | "ON_SITE" | "NONE";
export type Parking = "GARAGE" | "STREET" | "COVERED" | "NONE" | "VALET";
export type RentPeriod = "MONTHLY" | "WEEKLY" | "DAILY";
export type SoldBy = "OWNER" | "DEALER";
export type ItemCondition = "NEW" | "USED";
export type EventType =
  | "ARTS"
  | "CAREER"
  | "CHARITY"
  | "CLASSES"
  | "CLUBS"
  | "COMMUNITY"
  | "DANCE"
  | "FESTIVAL"
  | "FOOD"
  | "GAMES"
  | "MUSIC"
  | "OUTDOORS"
  | "SALE"
  | "SINGLES"
  | "SPORTS";
export type GigType = "COMPUTER" | "CREATIVE" | "CREW" | "DOMESTIC" | "EVENT";
export type PaymentStatus = "PAID" | "UNPAID";
export type EmploymentType = "FULL_TIME" | "PART_TIME" | "CONTRACT";
export type JobType =
  | "ACCOUNTING"
  | "ADMIN"
  | "ARCHITECT"
  | "BIOTECH"
  | "BUSINESS"
  | "CUSTOMER_SERVICE"
  | "EDUCATION"
  | "ENGINEERING"
  | "FINANCE"
  | "FOOD"
  | "GENERAL"
  | "GOVERNMENT"
  | "HEALTHCARE"
  | "HOSPITALITY"
  | "HR"
  | "IT"
  | "LEGAL"
  | "MANUFACTURING"
  | "MARKETING"
  | "MEDIA"
  | "NONPROFIT"
  | "QA"
  | "REAL_ESTATE"
  | "RETAIL"
  | "SALES"
  | "SALON"
  | "SCIENCE"
  | "SECURITY"
  | "SKILLED_TRADE"
  | "SOFTWARE"
  | "SYSTEMS"
  | "TECHNICAL"
  | "TRANSPORT"
  | "TV_FILM"
  | "WRITING";
export type ServiceType =
  | "AUTOMOTIVE"
  | "BEAUTY"
  | "CELL_PHONE"
  | "COMPUTER"
  | "CREATIVE"
  | "CYCLE"
  | "EVENT"
  | "FARM"
  | "FINANCIAL"
  | "HEALTH"
  | "HOUSEHOLD"
  | "LABOR"
  | "LEGAL"
  | "LESSONS"
  | "MARINE"
  | "PET"
  | "REAL_ESTATE"
  | "SKILLED_TRADE"
  | "SMALL_BIZ"
  | "THERAPEUTIC"
  | "TRAVEL"
  | "WRITING";
export type CommunityType =
  | "ACTIVITIES"
  | "ARTISTS"
  | "CHILDCARE"
  | "GENERAL"
  | "GROUPS"
  | "LOCAL_NEWS"
  | "LOST_FOUND"
  | "MISSED_CONNECTIONS"
  | "MUSICIANS"
  | "PETS"
  | "POLITICS"
  | "RANTS_RAVES"
  | "RIDESHARE"
  | "VOLUNTEERS";
export type LostAndFound = "LOST" | "FOUND";
export type EducationCompleted =
  | "HIGH_SCHOOL"
  | "ASSOCIATE"
  | "BACHELOR"
  | "MASTER"
  | "DOCTORATE";

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
