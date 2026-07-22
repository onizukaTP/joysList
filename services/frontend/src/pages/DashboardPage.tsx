import React from "react";
import { Link } from "react-router";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import api from "../services/api";
import { useAuthStore } from "../store/authStore";
import type { AdResponse } from "../types";
import Card from "../components/ui/Card";
import Button from "../components/ui/Button";
import Badge from "../components/ui/Badge";
import Avatar from "../components/ui/Avatar";
import { CardSkeleton } from "../components/ui/Skeleton";
import { toast } from "sonner";
import { PlusCircle, Trash2, Edit3, Eye, FileText, User } from "lucide-react";

export default function DashboardPage() {
  const { user } = useAuthStore();
  const queryClient = useQueryClient();

  // 1. Fetch user listings
  const { data: allAds, isLoading } = useQuery<AdResponse[]>({
    queryKey: ["allAds"],
    queryFn: async () => {
      console.log("[Dashboard] Fetching all listings...");
      const res = await api.get("/ads");
      return res.data;
    },
  });

  // Filter listings by current logged-in user (handles string/number equality safely)
  const userAds =
    allAds?.filter((ad) => {
      console.log(`[Dashboard] Checking ad ${ad.id} (postedById: ${ad.userId}) against logged-in user (${user?.userId})`);
      return String(ad.userId) === String(user?.userId);
    }) || [];

  // 2. Delete listing mutation
  const deleteMutation = useMutation({
    mutationFn: async (adId: number) => {
      console.log(`[Dashboard] Deleting ad listing ID: ${adId}`);
      await api.delete(`/ads/${adId}`);
    },
    onSuccess: () => {
      toast.success("Listing deleted successfully.");
      queryClient.invalidateQueries({ queryKey: ["allAds"] });
    },
    onError: (err: any) => {
      console.error("[Dashboard] Failed to delete listing:", err);
      toast.error(err.response?.data?.message || "Failed to delete listing.");
    },
  });

  const handleDelete = (adId: number) => {
    if (window.confirm("Are you sure you want to delete this listing?")) {
      deleteMutation.mutate(adId);
    }
  };

  return (
    <div className="max-w-6xl mx-auto py-8 space-y-8">
      {/* Header Banner */}
      <div className="bg-ivory border border-sand rounded-3xl p-6 sm:p-8 flex flex-col sm:flex-row items-center justify-between gap-6">
        <div className="flex items-center gap-4">
          <Avatar fallback={user?.username || "U"} size="lg" />
          <div>
            <h1 className="text-2xl font-black text-walnut">Welcome back, {user?.username}!</h1>
            <p className="text-xs text-bronze mt-0.5">Manage your active marketplace listings & profile settings.</p>
          </div>
        </div>
        <div className="flex items-center gap-3 w-full sm:w-auto">
          <Link to={`/profile/${user?.userId}`} className="flex-1 sm:flex-initial">
            <Button variant="ghost" size="sm" leftIcon={<User size={16} />} className="w-full">
              View Profile
            </Button>
          </Link>
          <Link to="/ads/new" className="flex-1 sm:flex-initial">
            <Button variant="primary" size="sm" leftIcon={<PlusCircle size={16} />} className="w-full">
              Post New Ad
            </Button>
          </Link>
        </div>
      </div>

      {/* Stats row */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-6">
        <Card className="p-6 flex items-center gap-4">
          <div className="w-12 h-12 rounded-2xl bg-linen flex items-center justify-center text-gold">
            <FileText size={24} />
          </div>
          <div>
            <span className="text-2xl font-black text-walnut">{userAds.length}</span>
            <p className="text-xs text-bronze uppercase font-bold tracking-wider">Total Listings</p>
          </div>
        </Card>
        <Card className="p-6 flex items-center gap-4">
          <div className="w-12 h-12 rounded-2xl bg-linen flex items-center justify-center text-gold">
            <Eye size={24} />
          </div>
          <div>
            <span className="text-2xl font-black text-walnut">{userAds.length}</span>
            <p className="text-xs text-bronze uppercase font-bold tracking-wider">Active Ads</p>
          </div>
        </Card>
        <Card className="p-6 flex items-center gap-4">
          <div className="w-12 h-12 rounded-2xl bg-linen flex items-center justify-center text-gold">
            <User size={24} />
          </div>
          <div>
            <span className="text-2xl font-black text-walnut">{user?.role}</span>
            <p className="text-xs text-bronze uppercase font-bold tracking-wider">Account Role</p>
          </div>
        </Card>
      </div>

      {/* Listings Table / Section */}
      <div className="space-y-4">
        <h2 className="text-xl font-bold text-walnut border-b border-sand pb-3">My Listings</h2>

        {isLoading ? (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <CardSkeleton />
            <CardSkeleton />
          </div>
        ) : userAds.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            {userAds.map((ad) => (
              <Card key={ad.id} className="p-5 flex flex-col justify-between space-y-4">
                <div className="space-y-2">
                  <div className="flex items-center justify-between">
                    <Badge variant="default" className="text-[10px] uppercase font-bold tracking-wider">
                      {ad.subcategoryName}
                    </Badge>
                    <span className="text-sm font-bold text-gold">
                      {ad.price === 0 ? "FREE" : ad.price ? `$${ad.price.toLocaleString()}` : "Contact for Price"}
                    </span>
                  </div>
                  <h3 className="font-bold text-walnut text-lg line-clamp-1">{ad.title}</h3>
                  <p className="text-xs text-bronze line-clamp-2">{ad.description}</p>
                </div>

                <div className="flex items-center justify-between pt-4 border-t border-sand/40 text-xs">
                  <span className="text-sand-dark">{ad.location}</span>
                  <div className="flex items-center gap-2">
                    <Link to={`/ads/${ad.id}`}>
                      <Button variant="ghost" size="sm" leftIcon={<Eye size={14} />}>
                        View
                      </Button>
                    </Link>
                    <Link to={`/ads/${ad.id}/edit`}>
                      <Button variant="ghost" size="sm" leftIcon={<Edit3 size={14} />}>
                        Edit
                      </Button>
                    </Link>
                    <Button
                      variant="danger"
                      size="sm"
                      leftIcon={<Trash2 size={14} />}
                      onClick={() => handleDelete(ad.id)}
                      isLoading={deleteMutation.isPending}
                    >
                      Delete
                    </Button>
                  </div>
                </div>
              </Card>
            ))}
          </div>
        ) : (
          <div className="text-center py-16 bg-ivory rounded-2xl border border-dashed border-sand">
            <p className="text-bronze font-medium">You haven't posted any classified listings yet.</p>
            <Link to="/ads/new" className="inline-block mt-4">
              <Button variant="primary" size="sm" leftIcon={<PlusCircle size={16} />}>
                Create Your First Ad
              </Button>
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}
