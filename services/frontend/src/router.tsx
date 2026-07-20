import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router";
import AppLayout from "./components/layout/AppLayout";
import ProtectedRoute from "./components/guards/ProtectedRoute";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import NotFoundPage from "./pages/NotFoundPage";

// Placeholders for Phase 3 pages
const HomePlaceholder = () => (
  <div className="py-20 text-center">
    <h1 className="text-4xl font-extrabold text-walnut">Welcome to JoysList</h1>
    <p className="text-bronze mt-2">Discover premium local classified ads.</p>
  </div>
);

const BrowsePlaceholder = () => (
  <div className="py-12">
    <h1 className="text-2xl font-bold text-walnut mb-4">Browse Listings</h1>
    <p className="text-bronze">Loading categories and ads...</p>
  </div>
);

const AdDetailPlaceholder = () => (
  <div className="py-12">
    <h1 className="text-2xl font-bold text-walnut mb-4">Listing Detail</h1>
    <p className="text-bronze">Fetching listing details...</p>
  </div>
);

const CreateAdPlaceholder = () => (
  <div className="py-12">
    <h1 className="text-2xl font-bold text-walnut mb-4">Post a New Ad</h1>
    <p className="text-bronze">Postings forms loading...</p>
  </div>
);

const DashboardPlaceholder = () => (
  <div className="py-12">
    <h1 className="text-2xl font-bold text-walnut mb-4">User Dashboard</h1>
    <p className="text-bronze">Manage your listings here.</p>
  </div>
);

const router = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    children: [
      // Public Routes
      { index: true, element: <HomePlaceholder /> },
      { path: "login", element: <LoginPage /> },
      { path: "register", element: <RegisterPage /> },
      { path: "browse", element: <BrowsePlaceholder /> },
      { path: "browse/:category", element: <BrowsePlaceholder /> },

      // Protected User Routes
      {
        element: <ProtectedRoute allowedRoles={["USER", "ADMIN"]} />,
        children: [
          { path: "ads/:id", element: <AdDetailPlaceholder /> },
          { path: "ads/new", element: <CreateAdPlaceholder /> },
          { path: "ads/:id/edit", element: <CreateAdPlaceholder /> },
          { path: "dashboard", element: <DashboardPlaceholder /> },
          { path: "profile/:id", element: <DashboardPlaceholder /> },
          { path: "profile/:id/edit", element: <DashboardPlaceholder /> },
          { path: "search", element: <BrowsePlaceholder /> },
        ],
      },

      // Protected Admin Routes
      {
        element: <ProtectedRoute allowedRoles={["ADMIN"]} />,
        children: [
          { path: "admin", element: <DashboardPlaceholder /> },
        ],
      },

      // Fallback
      { path: "*", element: <NotFoundPage /> },
    ],
  },
]);

export default function AppRouter() {
  return <RouterProvider router={router} />;
}
