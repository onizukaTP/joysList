import React from "react";
import { useParams, Link } from "react-router";
import { useQuery } from "@tanstack/react-query";
import api from "../services/api";
import { useAuthStore } from "../store/authStore";
import type { UserProfile, AdResponse } from "../types";
import Card from "../components/ui/Card";
import Avatar from "../components/ui/Avatar";
import Button from "../components/ui/Button";
import AdCard from "../components/ads/AdCard";
import { CardSkeleton } from "../components/ui/Skeleton";
import { MapPin, Mail, Phone, Calendar, Edit3, ArrowLeft } from "lucide-react";

export default function ProfilePage() {
  const { id } = useParams<{ id: string }>();
  const { user: currentUser } = useAuthStore();
  const isOwner = currentUser?.userId === parseInt(id || "", 10);

  // 1. Fetch user public profile
  const { data: profile, isLoading: profileLoading, error: profileError } = useQuery<UserProfile>({
    queryKey: ["userProfile", id],
    queryFn: async () => {
      console.log(`[ProfilePage] Fetching profile for user ID: ${id}`);
      const res = await api.get(`/users/${id}`);
      return res.data;
    },
  });

  // 2. Fetch user listings
  const { data: allAds, isLoading: adsLoading } = useQuery<AdResponse[]>({
    queryKey: ["allAds"],
    queryFn: async () => {
      const res = await api.get("/ads");
      return res.data;
    },
  });

  const userAds = allAds?.filter((ad) => ad.userId === parseInt(id || "", 10)) || [];

  if (profileLoading) {
    return (
      <div className="max-w-4xl mx-auto py-8">
        <CardSkeleton />
      </div>
    );
  }

  if (profileError || !profile) {
    return (
      <div className="text-center py-20 bg-ivory rounded-2xl border border-sand">
        <h2 className="text-2xl font-bold text-walnut">User Not Found</h2>
        <p className="text-bronze mt-2">The requested profile does not exist.</p>
        <Link to="/browse" className="mt-4 inline-block text-amber font-semibold hover:underline">
          Back to Listings
        </Link>
      </div>
    );
  }

  return (
    <div className="max-w-5xl mx-auto space-y-8 py-4">
      <Link
        to="/browse"
        className="inline-flex items-center gap-1.5 text-sm font-semibold text-bronze hover:text-walnut"
      >
        <ArrowLeft size={16} /> Back to Marketplace
      </Link>

      {/* Profile Header Card */}
      <Card className="p-8">
        <div className="flex flex-col md:flex-row items-start md:items-center justify-between gap-6">
          <div className="flex items-center gap-6">
            <Avatar fallback={profile.username} src={profile.avatarUrl || undefined} size="lg" />
            <div className="space-y-1">
              <h1 className="text-3xl font-black text-walnut">{profile.username}</h1>
              <p className="text-sm text-bronze flex items-center gap-2">
                <Calendar size={14} className="text-gold" /> Member since {new Date(profile.memberSince).getFullYear() || 2026}
              </p>
              {profile.location && (
                <p className="text-xs text-sand-dark flex items-center gap-1">
                  <MapPin size={14} /> {profile.location}
                </p>
              )}
            </div>
          </div>

          {isOwner && (
            <Link to={`/profile/${id}/edit`}>
              <Button variant="secondary" size="sm" leftIcon={<Edit3 size={16} />}>
                Edit Profile
              </Button>
            </Link>
          )}
        </div>

        {/* Bio Section */}
        {profile.bio && (
          <div className="mt-6 pt-6 border-t border-sand/40">
            <h2 className="text-xs font-bold uppercase tracking-wider text-walnut mb-2">About</h2>
            <p className="text-coffee text-sm leading-relaxed">{profile.bio}</p>
          </div>
        )}

        {/* Contact info if provided */}
        <div className="mt-6 pt-6 border-t border-sand/40 flex flex-wrap gap-6 text-xs text-bronze">
          {profile.email && (
            <div className="flex items-center gap-2">
              <Mail size={14} className="text-gold" />
              <span>{profile.email}</span>
            </div>
          )}
          {profile.phone && (
            <div className="flex items-center gap-2">
              <Phone size={14} className="text-gold" />
              <span>{profile.phone}</span>
            </div>
          )}
        </div>
      </Card>

      {/* Public Listings Section */}
      <div className="space-y-6">
        <div className="border-b border-sand pb-3 flex items-center justify-between">
          <h2 className="text-xl font-bold text-walnut">Active Listings by {profile.username}</h2>
          <span className="text-xs font-semibold text-bronze">{userAds.length} items</span>
        </div>

        {adsLoading ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            <CardSkeleton />
            <CardSkeleton />
            <CardSkeleton />
          </div>
        ) : userAds.length > 0 ? (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {userAds.map((ad) => (
              <AdCard key={ad.id} ad={ad} hasImage={ad.id % 2 === 0} />
            ))}
          </div>
        ) : (
          <div className="text-center py-12 bg-ivory rounded-2xl border border-dashed border-sand">
            <p className="text-bronze font-medium">This user currently has no active marketplace listings.</p>
          </div>
        )}
      </div>
    </div>
  );
}
