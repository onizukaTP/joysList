import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router";
import AppLayout from "./components/layout/AppLayout";
import ProtectedRoute from "./components/guards/ProtectedRoute";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import NotFoundPage from "./pages/NotFoundPage";
import HomePage from "./pages/HomePage";
import BrowsePage from "./pages/BrowsePage";
import AdDetailPage from "./pages/AdDetailPage";
import CreateAdPage from "./pages/CreateAdPage";
import SearchResultsPage from "./pages/SearchResultsPage";

// Placeholders for Phase 4 pages
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
      { index: true, element: <HomePage /> },
      { path: "login", element: <LoginPage /> },
      { path: "register", element: <RegisterPage /> },
      { path: "browse", element: <BrowsePage /> },
      { path: "browse/:category", element: <BrowsePage /> },

      // Protected User Routes
      {
        element: <ProtectedRoute allowedRoles={["USER", "ADMIN"]} />,
        children: [
          { path: "ads/:id", element: <AdDetailPage /> },
          { path: "ads/new", element: <CreateAdPage /> },
          { path: "ads/:id/edit", element: <CreateAdPage /> },
          { path: "dashboard", element: <DashboardPlaceholder /> },
          { path: "profile/:id", element: <DashboardPlaceholder /> },
          { path: "profile/:id/edit", element: <DashboardPlaceholder /> },
          { path: "search", element: <SearchResultsPage /> },
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
