import React from "react";
import { useQuery } from "@tanstack/react-query";
import { Link, useSearchParams } from "react-router";
import api from "../services/api";
import type { AdResponse, Page } from "../types";
import AdCard from "../components/ads/AdCard";
import SearchBar from "../components/search/SearchBar";
import Pagination from "../components/ui/Pagination";
import { CardSkeleton } from "../components/ui/Skeleton";
import { ArrowLeft } from "lucide-react";

export default function SearchResultsPage() {
  const [searchParams, setSearchParams] = useSearchParams();
  const keyword = searchParams.get("keyword") || "";
  const location = searchParams.get("location") || "";
  const currentPage = parseInt(searchParams.get("page") || "0", 10);
  const size = 12;

  // 1. Fetch filtered/searched ads
  const { data: searchPage, isLoading } = useQuery<Page<AdResponse>>({
    queryKey: ["searchAds", keyword, location, currentPage],
    queryFn: async () => {
      const params: Record<string, any> = {
        page: currentPage,
        size,
        sortBy: "createdAt",
        sortDir: "desc",
      };
      if (keyword) params.keyword = keyword;
      if (location) params.location = location;

      const res = await api.get("/ads/search", { params });
      return res.data;
    },
  });

  const handlePageChange = (newPage: number) => {
    setSearchParams({
      keyword,
      location,
      page: newPage.toString(),
    });
  };

  const handleSearchSubmit = (newKeyword: string, newLocation: string) => {
    setSearchParams({
      keyword: newKeyword,
      location: newLocation,
      page: "0",
    });
  };

  return (
    <div className="space-y-8 py-4">
      {/* Header and Back Link */}
      <div className="space-y-4">
        <Link
          to="/"
          className="inline-flex items-center gap-1.5 text-sm font-semibold text-bronze hover:text-walnut"
        >
          <ArrowLeft size={16} /> Back to Home
        </Link>
        <div className="border-b border-sand pb-4">
          <h1 className="text-3xl font-black text-walnut uppercase tracking-tight">
            Search Results
          </h1>
          <p className="text-sm text-bronze mt-1">
            Found {searchPage?.totalElements || 0} listings for keyword: "{keyword}"
            {location && ` in "${location}"`}
          </p>
        </div>
      </div>

      {/* Refined Search Bar */}
      <div className="max-w-3xl">
        <SearchBar
          initialKeyword={keyword}
          initialLocation={location}
          onSearch={handleSearchSubmit}
        />
      </div>

      {/* Listings Grid */}
      <div className="space-y-8">
        {isLoading ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {Array.from({ length: 8 }).map((_, i) => (
              <CardSkeleton key={i} />
            ))}
          </div>
        ) : searchPage && searchPage.content.length > 0 ? (
          <>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
              {searchPage.content.map((ad) => (
                <AdCard key={ad.id} ad={ad} hasImage={ad.id % 2 === 0} />
              ))}
            </div>
            <div className="pt-6 border-t border-sand">
              <Pagination
                currentPage={currentPage}
                totalPages={searchPage.totalPages}
                onPageChange={handlePageChange}
              />
            </div>
          </>
        ) : (
          <div className="text-center py-20 bg-ivory rounded-2xl border border-dashed border-sand">
            <p className="text-bronze font-medium">
              We couldn't find any listings matching your search.
            </p>
            <p className="text-xs text-sand-dark mt-1">
              Try checking your spelling or adjusting keyword filters.
            </p>
          </div>
        )}
      </div>
    </div>
  );
}
