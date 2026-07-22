import React, { useState } from "react";
import { useParams, useSearchParams, useNavigate } from "react-router";
import { useQuery } from "@tanstack/react-query";
import api from "../services/api";
import type { Category, Subcategory, AdResponse, Page } from "../types";
import AdCard from "../components/ads/AdCard";
import Pagination from "../components/ui/Pagination";
import Button from "../components/ui/Button";
import { CardSkeleton } from "../components/ui/Skeleton";
import { Filter, Grid, SlidersHorizontal, ArrowLeftRight } from "lucide-react";

export default function BrowsePage() {
  const { category: routeCategory } = useParams<{ category?: string }>();
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();

  // Filter States
  const [selectedSubcat, setSelectedSubcat] = useState<string>("");
  const [minPrice, setMinPrice] = useState<string>("");
  const [maxPrice, setMaxPrice] = useState<string>("");
  const [sortDir, setSortDir] = useState<"asc" | "desc">("desc");

  // Page States
  const currentPage = parseInt(searchParams.get("page") || "0", 10);
  const size = 12;

  // 1. Fetch categories
  const { data: categories } = useQuery<Category[]>({
    queryKey: ["categories"],
    queryFn: async () => {
      const res = await api.get("/categories");
      return res.data;
    },
  });

  const activeCategoryObject = categories?.find(
    (c) => c.name.toUpperCase().replace(" ", "_") === routeCategory
  );

  // 2. Fetch subcategories for active category
  const { data: subcategories } = useQuery<Subcategory[]>({
    queryKey: ["subcategories", activeCategoryObject?.id],
    enabled: !!activeCategoryObject,
    queryFn: async () => {
      const res = await api.get(`/subcategories/category/${activeCategoryObject?.id}`);
      return res.data;
    },
  });

  // 3. Search and filter listings matching parameters
  const { data: adsPage, isLoading: adsLoading } = useQuery<Page<AdResponse>>({
    queryKey: [
      "filteredAds",
      routeCategory,
      selectedSubcat,
      minPrice,
      maxPrice,
      sortDir,
      currentPage,
    ],
    queryFn: async () => {
      const params: Record<string, any> = {
        page: currentPage,
        size,
        sortBy: "createdAt",
        sortDir,
      };

      if (routeCategory) params.category = routeCategory;
      if (minPrice) params.minPrice = parseFloat(minPrice);
      if (maxPrice) params.maxPrice = parseFloat(maxPrice);
      if (selectedSubcat) params.subcategoryId = parseInt(selectedSubcat, 10);

      const res = await api.get("/ads/search", { params });
      return res.data;
    },
  });

  const handlePageChange = (newPage: number) => {
    setSearchParams({ page: newPage.toString() });
  };

  const handleClearFilters = () => {
    setSelectedSubcat("");
    setMinPrice("");
    maxPrice && setMaxPrice("");
    setSortDir("desc");
    setSearchParams({ page: "0" });
  };

  return (
    <div className="space-y-8 py-4">
      {/* Page Header */}
      <div className="border-b border-sand pb-4">
        <h1 className="text-3xl font-black text-walnut uppercase tracking-tight">
          {routeCategory ? routeCategory.replace("_", " ") : "All Marketplace Listings"}
        </h1>
        <p className="text-sm text-bronze mt-1">
          Showing {adsPage?.totalElements || 0} active listings.
        </p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-4 gap-8">
        {/* Filters Sidebar */}
        <aside className="lg:col-span-1 bg-ivory border border-sand rounded-2xl p-6 space-y-6 h-fit">
          <div className="flex items-center justify-between border-b border-sand pb-3">
            <h2 className="font-bold text-walnut text-sm uppercase tracking-wider flex items-center gap-2">
              <SlidersHorizontal size={16} /> Filters
            </h2>
            <button
              onClick={handleClearFilters}
              className="text-xs font-semibold text-amber hover:text-gold cursor-pointer"
            >
              Reset All
            </button>
          </div>

          {/* Subcategory Select */}
          {routeCategory && subcategories && subcategories.length > 0 && (
            <div className="space-y-2">
              <label className="text-xs font-bold uppercase text-walnut tracking-wider">Subcategory</label>
              <select
                value={selectedSubcat}
                onChange={(e) => {
                  setSelectedSubcat(e.target.value);
                  setSearchParams({ page: "0" });
                }}
                className="w-full bg-cream border border-sand rounded-xl px-3 py-2 text-sm text-coffee focus:outline-none focus:ring-1 focus:ring-gold"
              >
                <option value="">All Subcategories</option>
                {subcategories.map((sub) => (
                  <option key={sub.id} value={sub.id.toString()}>
                    {sub.name}
                  </option>
                ))}
              </select>
            </div>
          )}

          {/* Price Range Filter */}
          <div className="space-y-2">
            <label className="text-xs font-bold uppercase text-walnut tracking-wider">Price Range</label>
            <div className="flex items-center gap-2">
              <input
                type="number"
                placeholder="Min"
                value={minPrice}
                onChange={(e) => {
                  setMinPrice(e.target.value);
                  setSearchParams({ page: "0" });
                }}
                className="w-full bg-cream border border-sand rounded-xl px-3 py-1.5 text-xs text-coffee placeholder:text-sand-dark focus:outline-none"
              />
              <span className="text-sand-dark">-</span>
              <input
                type="number"
                placeholder="Max"
                value={maxPrice}
                onChange={(e) => {
                  setMaxPrice(e.target.value);
                  setSearchParams({ page: "0" });
                }}
                className="w-full bg-cream border border-sand rounded-xl px-3 py-1.5 text-xs text-coffee placeholder:text-sand-dark focus:outline-none"
              />
            </div>
          </div>

          {/* Sort Order */}
          <div className="space-y-2">
            <label className="text-xs font-bold uppercase text-walnut tracking-wider">Sort By Date</label>
            <div className="flex gap-2">
              <button
                onClick={() => setSortDir("desc")}
                className={`flex-1 py-1.5 rounded-xl border text-xs font-bold transition-all cursor-pointer ${
                  sortDir === "desc"
                    ? "bg-gold text-cream border-gold"
                    : "border-sand text-bronze hover:bg-linen"
                }`}
              >
                Newest
              </button>
              <button
                onClick={() => setSortDir("asc")}
                className={`flex-1 py-1.5 rounded-xl border text-xs font-bold transition-all cursor-pointer ${
                  sortDir === "asc"
                    ? "bg-gold text-cream border-gold"
                    : "border-sand text-bronze hover:bg-linen"
                }`}
              >
                Oldest
              </button>
            </div>
          </div>
        </aside>

        {/* Listings Display Grid */}
        <section className="lg:col-span-3 space-y-8">
          {adsLoading ? (
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
              {Array.from({ length: 6 }).map((_, i) => (
                <CardSkeleton key={i} />
              ))}
            </div>
          ) : adsPage && adsPage.content.length > 0 ? (
            <>
              <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-6">
                {adsPage.content.map((ad) => (
                  <AdCard key={ad.id} ad={ad} hasImage={ad.id % 2 === 0} />
                ))}
              </div>
              <div className="pt-6 border-t border-sand">
                <Pagination
                  currentPage={currentPage}
                  totalPages={adsPage.totalPages}
                  onPageChange={handlePageChange}
                />
              </div>
            </>
          ) : (
            <div className="text-center py-20 bg-ivory rounded-2xl border border-dashed border-sand">
              <p className="text-bronze font-medium">No listings found matching the chosen parameters.</p>
              <button
                onClick={handleClearFilters}
                className="mt-4 text-xs font-bold text-amber hover:text-gold uppercase tracking-wider underline cursor-pointer"
              >
                Clear all filters
              </button>
            </div>
          )}
        </section>
      </div>
    </div>
  );
}
