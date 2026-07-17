import React from "react";

interface SkeletonProps {
  className?: string;
  variant?: "text" | "circular" | "rectangular";
  width?: string;
  height?: string;
}

export default function Skeleton({
  className = "",
  variant = "rectangular",
  width,
  height,
}: SkeletonProps) {
  const variantClasses = {
    text: "rounded-md h-4",
    circular: "rounded-full",
    rectangular: "rounded-xl",
  };

  return (
    <div
      className={`animate-shimmer ${variantClasses[variant]} ${className}`}
      style={{ width, height }}
    />
  );
}

// Pre-built skeleton layouts
export function CardSkeleton() {
  return (
    <div className="bg-ivory border border-sand rounded-2xl p-6 space-y-4">
      <Skeleton height="12px" width="40%" variant="text" />
      <Skeleton height="20px" width="80%" variant="text" />
      <Skeleton height="14px" width="60%" variant="text" />
      <div className="flex gap-2 pt-2">
        <Skeleton height="24px" width="60px" className="rounded-full" />
        <Skeleton height="24px" width="80px" className="rounded-full" />
      </div>
    </div>
  );
}

export function AdCardSkeleton() {
  return (
    <div className="bg-ivory border border-sand rounded-2xl p-5 space-y-3">
      <Skeleton height="160px" className="rounded-xl" />
      <Skeleton height="14px" width="30%" variant="text" />
      <Skeleton height="20px" width="90%" variant="text" />
      <Skeleton height="14px" width="50%" variant="text" />
      <div className="flex justify-between pt-2">
        <Skeleton height="24px" width="70px" variant="text" />
        <Skeleton height="24px" width="90px" variant="text" />
      </div>
    </div>
  );
}
