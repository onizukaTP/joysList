import type { Category, AdResponse } from "./types";

export const MOCK_CATEGORIES: Category[] = [
  { id: 1, name: "Housing" },
  { id: 2, name: "For Sale" },
  { id: 3, name: "Jobs" },
  { id: 4, name: "Services" },
  { id: 5, name: "Gigs" },
  { id: 6, name: "Resumes" },
  { id: 7, name: "Community" },
  { id: 8, name: "Events" },
];

export const MOCK_ADS: AdResponse[] = [
  {
    id: 1,
    title: "Luxury 2-Bedroom Penthouse with Panoramic City Views",
    description:
      "Spacious 2-bedroom, 2-bathroom penthouse located in the heart of downtown. Features modern finishes, floor-to-ceiling windows, high-end stainless steel appliances, private balcony, in-unit washer/dryer, and assigned garage parking.",
    price: 3200,
    location: "Downtown, Metro City",
    subcategoryName: "Apartments",
    userId: 1,
  },
  {
    id: 2,
    title: "Vintage Mid-Century Modern Teak Dining Set",
    description:
      "Authentic 1960s Scandinavian teak dining table with 6 matching upholstered chairs. Excellent vintage condition with minimal wear. Table extends to comfortably seat 8 people. Pickup only.",
    price: 850,
    location: "West End, Metro City",
    subcategoryName: "Furniture",
    userId: 1,
  },
  {
    id: 3,
    title: "Senior Full Stack Software Engineer (React / Java)",
    description:
      "We are seeking an experienced Full Stack Engineer to join our core product engineering team. You will lead frontend architecture built with React and TypeScript while contributing to high-throughput Spring Boot microservices.",
    price: 145000,
    location: "Tech District / Remote",
    subcategoryName: "Software",
    userId: 2,
  },
  {
    id: 4,
    title: "Professional Home & Apartment Cleaning Services",
    description:
      "Licensed, bonded, and insured residential cleaning company. Offering deep cleans, move-in/move-out cleans, and recurring weekly or bi-weekly visits. Eco-friendly cleaning products supplied.",
    price: 120,
    location: "Metro Area",
    subcategoryName: "Household",
    userId: 2,
  },
  {
    id: 5,
    title: "Canon EOS R6 Mark II Mirrorless Camera (Barely Used)",
    description:
      "Mint condition Canon EOS R6 Mark II body. Shutter count under 3,500. Includes original box, 2 genuine Canon batteries, dual charger, original neck strap, and 128GB SanDisk Extreme Pro SD card.",
    price: 1950,
    location: "North Suburbs",
    subcategoryName: "Electronics",
    userId: 1,
  },
  {
    id: 6,
    title: "Community Dog Meetup & Social Hour in the Park",
    description:
      "Join local dog lovers this Saturday morning at Central Bark Dog Park! Free treats for pups, coffee for owners, and agility course equipment set up for fun.",
    price: 0,
    location: "Central Dog Park",
    subcategoryName: "Pets",
    userId: 2,
  },
  {
    id: 7,
    title: "Custom Web Application Design & Frontend Development",
    description:
      "Senior UX/UI Designer and Frontend Developer available for contract projects. Specializing in sleek React applications, Tailwind styling, motion UI, and responsive design systems.",
    price: 75,
    location: "Remote / Hybrid",
    subcategoryName: "Creative",
    userId: 1,
  },
  {
    id: 8,
    title: "Acoustic Guitar Lessons for Beginners & Intermediate",
    description:
      "Learn fingerstyle, rhythm, chord progressions, and song arrangement. Over 10 years of teaching experience. Lessons held at private studio or virtually via Zoom.",
    price: 45,
    location: "Eastside Music Studio",
    subcategoryName: "Lessons",
    userId: 2,
  },
];
