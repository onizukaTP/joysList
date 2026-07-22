import React from "react";
import { Link, useNavigate } from "react-router";
import { useQuery } from "@tanstack/react-query";
import api from "../services/api";
import type { Category, AdResponse } from "../types";
import SearchBar from "../components/search/SearchBar";
import AdCard from "../components/ads/AdCard";
import { CardSkeleton, Button } from "../components/ui";
import { Briefcase, Home, ShoppingBag, Wrench, Calendar, Users, FileText, Activity, ArrowRight } from "lucide-react";

const CATEGORY_ICONS: Record<string, React.ReactNode> = {
  HOUSING: <Home className="stroke-[1.5]" size={32} />,
  FOR_SALE: <ShoppingBag className="stroke-[1.5]" size={32} />,
  JOBS: <Briefcase className="stroke-[1.5]" size={32} />,
  SERVICES: <Wrench className="stroke-[1.5]" size={32} />,
  GIGS: <Activity className="stroke-[1.5]" size={32} />,
  RESUMES: <FileText className="stroke-[1.5]" size={32} />,
  COMMUNITY: <Users className="stroke-[1.5]" size={32} />,
  EVENTS: <Calendar className="stroke-[1.5]" size={32} />,
};

export default function HomePage() {
  const navigate = useNavigate();

  // 1. Fetch categories
  const { data: categories, isLoading: categoriesLoading } = useQuery<Category[]>({
    queryKey: ["categories"],
    queryFn: async () => {
      const res = await api.get("/categories");
      return res.data;
    },
  });

  // 2. Fetch recent ads
  const { data: recentAds, isLoading: adsLoading } = useQuery<AdResponse[]>({
    queryKey: ["recentAds"],
    queryFn: async () => {
      const res = await api.get("/ads");
      // Grab the first 8 listings
      return res.data.slice(0, 8);
    },
  });

  const handleSearch = (keyword: string, location: string) => {
    let queryParams = [];
    if (keyword) queryParams.push(`keyword=${encodeURIComponent(keyword)}`);
    if (location) queryParams.push(`location=${encodeURIComponent(location)}`);
    const searchString = queryParams.length > 0 ? `?${queryParams.join("&")}` : "";
    navigate(`/search${searchString}`);
  };

  return (
    <div className="space-y-16 py-4">
      {/* Hero section */}
      <section className="text-center max-w-4xl mx-auto space-y-8 py-10">
        <div className="space-y-3">
          <h1 className="text-4xl sm:text-6xl font-black text-walnut tracking-tight leading-tight">
            Find Anything Local, <br />
            <span className="bg-gradient-to-r from-gold via-caramel to-amber bg-clip-text text-transparent">
              Redesigned with Elegance
            </span>
          </h1>
          <p className="text-base sm:text-xl text-bronze max-w-2xl mx-auto font-medium leading-relaxed">
            The premium community marketplace. Safe, beautiful, and localized.
          </p>
        </div>

        {/* Hero Search */}
        <div className="max-w-2xl mx-auto pt-4">
          <SearchBar onSearch={handleSearch} />
        </div>
      </section>

      {/* Categories Grid */}
      <section className="space-y-6">
        <div className="flex items-center justify-between border-b border-sand pb-3">
          <h2 className="text-xl sm:text-2xl font-bold text-walnut">Browse Categories</h2>
          <Link to="/browse" className="text-sm font-semibold text-amber hover:text-gold flex items-center gap-1">
            See all <ArrowRight size={14} />
          </Link>
        </div>

        {categoriesLoading ? (
          <div className="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-8 gap-4">
            {Array.from({ length: 8 }).map((_, i) => (
              <div key={i} className="h-28 bg-ivory border border-sand/40 rounded-2xl animate-shimmer" />
            ))}
          </div>
        ) : (
          <div className="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-8 gap-4">
            {categories?.map((cat) => {
              // category names from backend might be uppercase/snake_case or regular case
              const adTypeKey = cat.name.toUpperCase().replace(" ", "_");
              return (
                <Link
                  key={cat.id}
                  to={`/browse/${adTypeKey}`}
                  className="bg-ivory hover:bg-cream border border-sand hover:border-gold/60 rounded-2xl p-4 flex flex-col items-center justify-center text-center gap-3 shadow-sm hover:shadow-md transition-all duration-300 group cursor-pointer"
                >
                  <div className="text-sand-dark group-hover:text-gold transition-colors">
                    {CATEGORY_ICONS[adTypeKey] || <ShoppingBag size={32} />}
                  </div>
                  <span className="text-xs font-bold text-walnut uppercase tracking-wider group-hover:text-coffee transition-colors">
                    {cat.name}
                  </span>
                </Link>
              );
            })}
          </div>
        )}
      </section>

      {/* Recent Listings */}
      <section className="space-y-6">
        <div className="flex items-center justify-between border-b border-sand pb-3">
          <h2 className="text-xl sm:text-2xl font-bold text-walnut">Recent Listings</h2>
          <Link to="/browse" className="text-sm font-semibold text-amber hover:text-gold flex items-center gap-1">
            Browse all <ArrowRight size={14} />
          </Link>
        </div>

        {adsLoading ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {Array.from({ length: 4 }).map((_, i) => (
              <CardSkeleton key={i} />
            ))}
          </div>
        ) : recentAds && recentAds.length > 0 ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {recentAds.map((ad) => (
              <AdCard key={ad.id} ad={ad} hasImage={ad.id % 2 === 0} />
            ))}
          </div>
        ) : (
          <div className="text-center py-12 bg-ivory rounded-2xl border border-dashed border-sand">
            <p className="text-bronze font-medium">No listings found. Be the first to post something!</p>
            <Link to="/ads/new" className="inline-block mt-4">
              <Button variant="secondary" size="sm">Post an Ad</Button>
            </Link>
          </div>
        )}
      </section>

      {/* Feature section */}
      <section className="bg-linen-warm/50 border border-sand/50 rounded-3xl p-8 sm:p-12 flex flex-col md:flex-row items-center justify-between gap-8">
        <div className="space-y-4 max-w-xl">
          <h2 className="text-2xl sm:text-3xl font-black text-walnut">Have something to list?</h2>
          <p className="text-bronze text-sm sm:text-base leading-relaxed">
            Post your services, properties, job offers, or second-hand items easily on JoysList. Connect with verified buyers in your local neighborhood safely.
          </p>
        </div>
        <Link to="/ads/new" className="shrink-0 w-full md:w-auto">
          <Button variant="primary" size="lg" className="w-full">
            List an Item Now
          </Button>
        </Link>
      </section>
    </div>
  );
}
