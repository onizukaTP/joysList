import { useState } from "react";
import { Link } from "react-router";
import { useQuery } from "@tanstack/react-query";
import api from "../../services/api";
import type { AdResponse } from "../../types";
import Card from "../../components/ui/Card";
import Button from "../../components/ui/Button";
import Badge from "../../components/ui/Badge";
import { CardSkeleton } from "../../components/ui/Skeleton";
import { Shield, Users, FileText, Activity, AlertTriangle, Trash2, Eye } from "lucide-react";
import { toast } from "sonner";

export default function AdminDashboardPage() {
  const [activeTab, setActiveTab] = useState<"overview" | "listings">("overview");

  // Fetch all ads across platform for moderation
  const { data: allAds = [], isLoading, refetch } = useQuery<AdResponse[]>({
    queryKey: ["adminAllAds"],
    queryFn: async () => {
      const res = await api.get("/ads");
      return res.data;
    },
  });

  const handleDeleteAd = async (adId: number) => {
    if (!window.confirm(`Are you sure you want to administratively delete Ad #${adId}?`)) return;
    try {
      await api.delete(`/ads/${adId}`);
      toast.success(`Ad #${adId} deleted successfully.`);
      refetch();
    } catch (err: any) {
      toast.error(err.response?.data?.message || "Failed to delete ad.");
    }
  };

  return (
    <div className="max-w-6xl mx-auto py-8 space-y-8">
      {/* Header Banner */}
      <div className="bg-gradient-to-r from-walnut via-coffee to-caramel text-ivory rounded-3xl p-6 sm:p-8 flex flex-col sm:flex-row items-start sm:items-center justify-between gap-6 shadow-md">
        <div className="flex items-center gap-4">
          <div className="w-14 h-14 rounded-2xl bg-gold/20 flex items-center justify-center text-gold border border-gold/40">
            <Shield size={32} />
          </div>
          <div>
            <div className="flex items-center gap-2">
              <h1 className="text-2xl font-black tracking-tight">Admin Control Panel</h1>
              <Badge variant="featured" className="text-[10px] uppercase font-bold tracking-widest">
                Administrator Mode
              </Badge>
            </div>
            <p className="text-xs text-sand-light/80 mt-1 font-medium">
              Platform administration, content moderation, and metrics overview.
            </p>
          </div>
        </div>

        <div className="flex items-center gap-3">
          <Link to="/dashboard">
            <Button variant="ghost" size="sm" className="text-ivory border-ivory/20 hover:bg-ivory/10">
              User View
            </Button>
          </Link>
        </div>
      </div>

      {/* Admin Stats Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-4 gap-6">
        <Card className="p-6 flex items-center gap-4 border-l-4 border-l-gold">
          <div className="w-12 h-12 rounded-2xl bg-linen flex items-center justify-center text-gold">
            <FileText size={24} />
          </div>
          <div>
            <span className="text-2xl font-black text-walnut">{allAds.length}</span>
            <p className="text-xs text-bronze uppercase font-bold tracking-wider">Total Ads Listed</p>
          </div>
        </Card>

        <Card className="p-6 flex items-center gap-4 border-l-4 border-l-amber">
          <div className="w-12 h-12 rounded-2xl bg-linen flex items-center justify-center text-amber">
            <Users size={24} />
          </div>
          <div>
            <span className="text-2xl font-black text-walnut">Active</span>
            <p className="text-xs text-bronze uppercase font-bold tracking-wider">System Health</p>
          </div>
        </Card>

        <Card className="p-6 flex items-center gap-4 border-l-4 border-l-caramel">
          <div className="w-12 h-12 rounded-2xl bg-linen flex items-center justify-center text-caramel">
            <Activity size={24} />
          </div>
          <div>
            <span className="text-2xl font-black text-walnut">Healthy</span>
            <p className="text-xs text-bronze uppercase font-bold tracking-wider">Kafka & Redis</p>
          </div>
        </Card>

        <Card className="p-6 flex items-center gap-4 border-l-4 border-l-walnut">
          <div className="w-12 h-12 rounded-2xl bg-linen flex items-center justify-center text-walnut">
            <AlertTriangle size={24} />
          </div>
          <div>
            <span className="text-2xl font-black text-walnut">0</span>
            <p className="text-xs text-bronze uppercase font-bold tracking-wider">Reports Pending</p>
          </div>
        </Card>
      </div>

      {/* Tabs */}
      <div className="flex border-b border-sand gap-4">
        <button
          onClick={() => setActiveTab("overview")}
          className={`pb-3 font-bold text-sm transition-colors border-b-2 ${
            activeTab === "overview"
              ? "border-gold text-walnut"
              : "border-transparent text-bronze hover:text-walnut"
          }`}
        >
          All Listings Moderation ({allAds.length})
        </button>
      </div>

      {/* Content */}
      {isLoading ? (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <CardSkeleton />
          <CardSkeleton />
        </div>
      ) : allAds.length > 0 ? (
        <div className="space-y-4">
          <div className="overflow-x-auto bg-ivory rounded-2xl border border-sand shadow-sm">
            <table className="w-full text-left text-xs text-walnut">
              <thead className="bg-linen uppercase text-[10px] font-bold tracking-wider border-b border-sand">
                <tr>
                  <th className="p-4">Ad ID</th>
                  <th className="p-4">Title</th>
                  <th className="p-4">Category / Subcategory</th>
                  <th className="p-4">Price</th>
                  <th className="p-4">User ID</th>
                  <th className="p-4 text-right">Actions</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-sand/40">
                {allAds.map((ad) => (
                  <tr key={ad.id} className="hover:bg-linen/50 transition-colors">
                    <td className="p-4 font-mono font-bold text-sand-dark">#{ad.id}</td>
                    <td className="p-4 font-bold max-w-xs truncate">{ad.title}</td>
                    <td className="p-4">
                      <Badge variant="default" className="text-[10px] uppercase">
                        {ad.subcategoryName}
                      </Badge>
                    </td>
                    <td className="p-4 font-semibold text-gold">
                      {ad.price === 0 ? "FREE" : ad.price ? `₹${ad.price.toLocaleString()}` : "N/A"}
                    </td>
                    <td className="p-4 font-mono">User #{ad.userId}</td>
                    <td className="p-4 text-right">
                      <div className="flex items-center justify-end gap-2">
                        <Link to={`/ads/${ad.id}`}>
                          <Button variant="ghost" size="sm" leftIcon={<Eye size={14} />}>
                            View
                          </Button>
                        </Link>
                        <Button
                          variant="danger"
                          size="sm"
                          leftIcon={<Trash2 size={14} />}
                          onClick={() => handleDeleteAd(ad.id)}
                        >
                          Delete
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      ) : (
        <div className="text-center py-16 bg-ivory rounded-2xl border border-dashed border-sand">
          <p className="text-bronze font-medium">No marketplace listings found to moderate.</p>
        </div>
      )}
    </div>
  );
}
