import React from "react";
import { Navigate, Outlet, useLocation } from "react-router";
import { useAuthStore } from "../../store/authStore";
import { CardSkeleton } from "../ui/Skeleton";

interface ProtectedRouteProps {
  allowedRoles?: Array<"USER" | "ADMIN">;
}

export default function ProtectedRoute({ allowedRoles }: ProtectedRouteProps) {
  const { isAuthenticated, user, isLoading } = useAuthStore();
  const location = useLocation();

  if (isLoading) {
    return (
      <div className="min-h-screen bg-cream flex items-center justify-center p-8">
        <div className="w-full max-w-md">
          <CardSkeleton />
        </div>
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  if (allowedRoles && user && !allowedRoles.includes(user.role)) {
    return <Navigate to="/" replace />;
  }

  return <Outlet />;
}
