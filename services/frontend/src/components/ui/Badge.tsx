import React, { type ReactNode } from "react";

type BadgeVariant = "default" | "free" | "new" | "featured" | "delivery";

interface BadgeProps {
  variant?: BadgeVariant;
  children: ReactNode;
  className?: string;
}

const variantStyles: Record<BadgeVariant, string> = {
  default: "bg-sand text-bronze",
  free: "bg-caramel/20 text-amber-dark",
  new: "bg-gold/20 text-gold-dark",
  featured: "bg-gold text-cream",
  delivery: "bg-amber/15 text-amber",
};

export default function Badge({
  variant = "default",
  children,
  className = "",
}: BadgeProps) {
  return (
    <span
      className={`
        inline-flex items-center px-2.5 py-0.5
        text-xs font-semibold rounded-full
        ${variantStyles[variant]}
        ${className}
      `}
    >
      {children}
    </span>
  );
}
