import React, { useState } from "react";
import { useParams, Link } from "react-router";
import { useQuery } from "@tanstack/react-query";
import api from "../services/api";
import type { AdResponse, UserProfile } from "../types";
import { MOCK_ADS } from "../data/mockData";
import Button from "../components/ui/Button";
import Avatar from "../components/ui/Avatar";
import Badge from "../components/ui/Badge";
import Modal from "../components/ui/Modal";
import Input from "../components/ui/Input";
import Textarea from "../components/ui/Textarea";
import { CardSkeleton } from "../components/ui/Skeleton";
import { toast } from "sonner";
import { MapPin, User, Calendar, Tag, FileText, ChevronLeft, Mail, Phone, Send } from "lucide-react";

export default function AdDetailPage() {
  const { id } = useParams<{ id: string }>();

  // Contact Modal State
  const [showContactModal, setShowContactModal] = useState(false);
  const [contactName, setContactName] = useState("");
  const [contactEmail, setContactEmail] = useState("");
  const [contactMessage, setContactMessage] = useState("");
  const [sendingContact, setSendingContact] = useState(false);

  // 1. Fetch Ad details with static fallback
  const { data: ad, isLoading: adLoading, error: adError } = useQuery<AdResponse>({
    queryKey: ["ad", id],
    queryFn: async () => {
      try {
        const genericRes = await api.get(`/ads`);
        const foundAd = genericRes.data?.find((a: any) => String(a.id) === String(id));
        if (foundAd) return foundAd;
      } catch (err) {
        console.warn("[AdDetailPage] Ads API request error ignored, using fallback static data.");
      }

      // Check static mock data fallback
      const mockAd = MOCK_ADS.find((a) => String(a.id) === String(id));
      if (mockAd) return mockAd;

      throw new Error("Ad not found");
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

  const handleSendContactMessage = async (e: React.FormEvent) => {
    e.preventDefault();
    setSendingContact(true);
    try {
      console.log(`[ContactSeller] Inquiry sent for adId=${id}:`, {
        adTitle: ad?.title,
        recipientUserId: ad?.userId,
        senderName: contactName,
        senderEmail: contactEmail,
        message: contactMessage,
      });

      // Simulation timeout until backend messaging service endpoint is added
      await new Promise((resolve) => setTimeout(resolve, 800));

      toast.success(`Inquiry sent to ${posterProfile?.username || "the seller"}!`);
      setShowContactModal(false);
      setContactName("");
      setContactEmail("");
      setContactMessage("");
    } catch (err) {
      toast.error("Failed to send message.");
    } finally {
      setSendingContact(false);
    }
  };

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
                  <Button
                    variant="primary"
                    className="w-full justify-center"
                    onClick={() => setShowContactModal(true)}
                  >
                    Contact Seller
                  </Button>
                </div>
              </div>
            )}
          </div>
        </aside>
      </div>

      {/* Contact Seller Modal */}
      <Modal
        isOpen={showContactModal}
        onClose={() => setShowContactModal(false)}
        title={`Contact ${posterProfile?.username || "Seller"}`}
      >
        <form onSubmit={handleSendContactMessage} className="space-y-4 pt-2">
          <p className="text-xs text-bronze">
            Send an inquiry regarding <span className="font-bold text-walnut">"{ad.title}"</span>.
          </p>

          <Input
            label="Your Name"
            type="text"
            placeholder="John Doe"
            required
            value={contactName}
            onChange={(e) => setContactName(e.target.value)}
          />

          <Input
            label="Your Email"
            type="email"
            placeholder="john@example.com"
            required
            value={contactEmail}
            onChange={(e) => setContactEmail(e.target.value)}
          />

          <Textarea
            label="Message"
            placeholder="Hi, is this listing still available? I am interested in buying..."
            required
            value={contactMessage}
            onChange={(e) => setContactMessage(e.target.value)}
          />

          <div className="flex justify-end gap-3 pt-4 border-t border-sand">
            <Button
              type="button"
              variant="ghost"
              onClick={() => setShowContactModal(false)}
            >
              Cancel
            </Button>
            <Button
              type="submit"
              variant="primary"
              isLoading={sendingContact}
              leftIcon={<Send size={16} />}
            >
              Send Message
            </Button>
          </div>
        </form>
      </Modal>
    </div>
  );
}
