import React from "react";
import { useParams, Link } from "react-router";
import { useQuery } from "@tanstack/react-query";
import api from "../services/api";
import type { AdResponse, UserProfile } from "../types";
import Button from "../components/ui/Button";
import Avatar from "../components/ui/Avatar";
import Badge from "../components/ui/Badge";
import { CardSkeleton } from "../components/ui/Skeleton";
import { MapPin, User, Calendar, Tag, FileText, ChevronLeft, Mail, Phone, ExternalLink } from "lucide-react";

export default function AdDetailPage() {
  const { id } = useParams<{ id: string }>();

  // 1. Fetch Ad details
  const { data: ad, isLoading: adLoading, error: adError } = useQuery<AdResponse>({
    queryKey: ["ad", id],
    queryFn: async () => {
      // Find category first to request the exact category details
      const genericRes = await api.get(`/ads`);
      const foundAd = genericRes.data.find((a: any) => a.id === parseInt(id || "", 10));
      if (!foundAd) throw new Error("Ad not found");
      return foundAd;
    },
  });

  // 2. Fetch User Profile who posted the ad
  const { data: posterProfile, isLoading: posterLoading } = useQuery<UserProfile>({
    queryKey: ["userProfile", ad?.userId],
    enabled: !!ad?.userId,
    queryFn: async () => {
      const res = await api.get(`/users/${ad?.userId}`);
      return res.data;
    },
  });

  if (adLoading) {
    return (
      <div className="max-w-4xl mx-auto py-8">
        <CardSkeleton />
      </div>
    );
  }

  if (adError || !ad) {
    return (
      <div className="text-center py-20 bg-ivory rounded-2xl border border-sand">
        <h2 className="text-2xl font-bold text-walnut">Error Loading Listing</h2>
        <p className="text-bronze mt-2">The listing might have been removed or does not exist.</p>
        <Link to="/browse" className="mt-4 inline-block text-amber font-semibold hover:underline">
          Back to Listings
        </Link>
      </div>
    );
  }

  const hasImage = ad.id % 2 === 0;

  return (
    <div className="max-w-5xl mx-auto space-y-6 py-4">
      {/* Back button */}
      <Link
        to="/browse"
        className="inline-flex items-center gap-1 text-sm font-semibold text-bronze hover:text-walnut transition-colors group"
      >
        <ChevronLeft size={16} className="group-hover:-translate-x-1 transition-transform" />
        Back to listings
      </Link>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Main Content (2/3 cols) */}
        <div className="lg:col-span-2 space-y-6">
          {/* Ad image gallery placeholder */}
          <div className="relative aspect-video bg-linen border border-sand rounded-3xl overflow-hidden flex items-center justify-center text-sand-dark">
            {hasImage ? (
              <img
                src="https://images.unsplash.com/photo-1570129477492-45c003edd2be?auto=format&fit=crop&w=1200&q=80"
                alt={ad.title}
                className="w-full h-full object-cover animate-fadeIn"
              />
            ) : (
              <div className="text-center space-y-2">
                <FileText size={48} className="mx-auto stroke-[1.5]" />
                <span className="text-xs uppercase tracking-wider font-bold block">No images attached</span>
              </div>
            )}
          </div>

          {/* Ad Info card */}
          <div className="bg-ivory border border-sand rounded-3xl p-6 sm:p-8 space-y-6">
            <div className="flex items-center gap-2">
              <Badge variant="default" className="uppercase font-bold tracking-wider px-3 py-1">
                {ad.subcategoryName}
              </Badge>
              {ad.price === 0 && <Badge variant="free">FREE</Badge>}
            </div>

            <div className="space-y-2">
              <h1 className="text-2xl sm:text-3xl font-black text-walnut">{ad.title}</h1>
              <div className="flex items-center gap-4 text-xs text-sand-dark">
                <span className="flex items-center gap-1">
                  <MapPin size={14} /> {ad.location}
                </span>
                <span className="flex items-center gap-1">
                  <Calendar size={14} /> Created Recently
                </span>
              </div>
            </div>

            <div className="border-t border-sand/40 pt-6">
              <h2 className="text-sm font-bold uppercase tracking-wider text-walnut mb-3">Description</h2>
              <p className="text-coffee leading-relaxed text-sm whitespace-pre-wrap">
                {ad.description}
              </p>
            </div>
          </div>
        </div>

        {/* Sidebar Actions (1/3 col) */}
        <aside className="space-y-6">
          {/* Price widget */}
          {ad.price !== null && (
            <div className="bg-espresso text-cream border border-espresso/20 rounded-3xl p-6 flex flex-col justify-center text-center space-y-1 shadow-md">
              <span className="text-xs text-sand uppercase tracking-widest font-semibold">Listing Price</span>
              <span className="text-3xl font-black">
                {ad.price === 0 ? "Free" : `$${ad.price.toLocaleString()}`}
              </span>
            </div>
          )}

          {/* User details card */}
          <div className="bg-ivory border border-sand rounded-3xl p-6 space-y-6">
            <h3 className="text-xs font-bold uppercase tracking-wider text-walnut border-b border-sand pb-3">
              Seller Profile
            </h3>

            {posterLoading ? (
              <div className="flex items-center gap-4">
                <div className="w-12 h-12 rounded-full bg-linen animate-shimmer" />
                <div className="space-y-2 flex-grow">
                  <div className="h-4 bg-linen animate-shimmer rounded w-1/2" />
                  <div className="h-3 bg-linen animate-shimmer rounded w-1/3" />
                </div>
              </div>
            ) : (
              <div className="space-y-4">
                <div className="flex items-center gap-4">
                  <Avatar fallback={posterProfile?.username || "A"} size="lg" />
                  <div>
                    <h4 className="font-bold text-coffee text-base">{posterProfile?.username}</h4>
                    <span className="text-xs text-sand-dark">Member since 2026</span>
                  </div>
                </div>

                <div className="space-y-2 text-xs text-bronze pt-2 border-t border-sand/40">
                  {posterProfile?.email && (
                    <div className="flex items-center gap-2">
                      <Mail size={14} />
                      <span>{posterProfile.email}</span>
                    </div>
                  )}
                  {posterProfile?.phone && (
                    <div className="flex items-center gap-2">
                      <Phone size={14} />
                      <span>{posterProfile.phone}</span>
                    </div>
                  )}
                </div>

                <div className="pt-2">
                  <a
                    href={`mailto:${posterProfile?.email || ""}`}
                    className="block"
                  >
                    <Button variant="primary" className="w-full justify-center">
                      Contact Seller
                    </Button>
                  </a>
                </div>
              </div>
            )}
          </div>
        </aside>
      </div>
    </div>
  );
}
