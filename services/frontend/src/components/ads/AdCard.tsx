import React from "react";
import { Link } from "react-router";
import type { AdResponse } from "../../types";
import Card from "../ui/Card";
import Badge from "../ui/Badge";
import { MapPin, Image as ImageIcon } from "lucide-react";

interface AdCardProps {
  ad: AdResponse;
  hasImage?: boolean;
}

export default function AdCard({ ad, hasImage = false }: AdCardProps) {
  // Format price
  const displayPrice =
    ad.price !== null && ad.price !== undefined
      ? ad.price === 0
        ? "Free"
        : `$${ad.price.toLocaleString()}`
      : null;

  return (
    <Link to={`/ads/${ad.id}`} className="block h-full">
      <Card className="flex flex-col h-full overflow-hidden hover:border-gold/60 border-sand-light transition-all duration-300">
        {/* Placeholder image card shell */}
        <div className="relative aspect-video w-full bg-linen flex items-center justify-center text-sand-dark border-b border-sand-light rounded-t-xl overflow-hidden shrink-0">
          {hasImage ? (
            <div className="absolute inset-0 bg-cover bg-center bg-no-repeat" style={{ backgroundImage: 'url("https://images.unsplash.com/photo-1570129477492-45c003edd2be?auto=format&fit=crop&w=400&q=80")' }} />
          ) : (
            <div className="flex flex-col items-center gap-1.5">
              <ImageIcon size={28} className="stroke-[1.5]" />
              <span className="text-xs uppercase tracking-wider font-semibold">No Image</span>
            </div>
          )}
          {displayPrice && (
            <div className="absolute bottom-3 left-3 bg-espresso/90 backdrop-blur-sm text-cream text-sm font-bold px-2.5 py-1 rounded-lg">
              {displayPrice}
            </div>
          )}
        </div>

        {/* Content body */}
        <div className="flex-1 flex flex-col p-4 space-y-2">
          <div className="flex items-center gap-2">
            <Badge variant="default" className="text-[10px] uppercase font-bold tracking-wider">
              {ad.subcategoryName}
            </Badge>
            {ad.price === 0 && (
              <Badge variant="free" className="text-[10px] uppercase font-bold tracking-wider">
                FREE
              </Badge>
            )}
          </div>

          <h3 className="font-semibold text-coffee line-clamp-1 group-hover:text-gold transition-colors text-base">
            {ad.title}
          </h3>

          <p className="text-xs text-bronze line-clamp-2 leading-relaxed flex-1">
            {ad.description}
          </p>

          <div className="flex items-center gap-1 text-[11px] text-sand-dark pt-2 border-t border-sand-light/50 shrink-0">
            <MapPin size={12} />
            <span className="truncate">{ad.location}</span>
          </div>
        </div>
      </Card>
    </Link>
  );
}
