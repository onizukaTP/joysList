import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router";
import AppLayout from "./components/layout/AppLayout";
import ProtectedRoute from "./components/guards/ProtectedRoute";
import GuestRoute from "./components/guards/GuestRoute";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import NotFoundPage from "./pages/NotFoundPage";
import HomePage from "./pages/HomePage";
import BrowsePage from "./pages/BrowsePage";
import AdDetailPage from "./pages/AdDetailPage";
import CreateAdPage from "./pages/CreateAdPage";
import SearchResultsPage from "./pages/SearchResultsPage";

import DashboardPage from "./pages/DashboardPage";
import ProfilePage from "./pages/ProfilePage";
import EditProfilePage from "./pages/EditProfilePage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <AppLayout />,
    children: [
      // Public Home Route
      { index: true, element: <HomePage /> },

      // Guest-only Routes (Redirect to / if logged in)
      {
        element: <GuestRoute />,
        children: [
          { path: "login", element: <LoginPage /> },
          { path: "register", element: <RegisterPage /> },
        ],
      },
      { path: "browse", element: <BrowsePage /> },
      { path: "browse/:category", element: <BrowsePage /> },

      // Protected User Routes
      {
        element: <ProtectedRoute allowedRoles={["USER", "ADMIN"]} />,
        children: [
          { path: "ads/:id", element: <AdDetailPage /> },
          { path: "ads/new", element: <CreateAdPage /> },
          { path: "ads/:id/edit", element: <CreateAdPage /> },
          { path: "dashboard", element: <DashboardPage /> },
          { path: "profile/:id", element: <ProfilePage /> },
          { path: "profile/:id/edit", element: <EditProfilePage /> },
          { path: "search", element: <SearchResultsPage /> },
        ],
      },

      // Protected Admin Routes
      {
        element: <ProtectedRoute allowedRoles={["ADMIN"]} />,
        children: [
          { path: "admin", element: <DashboardPage /> },
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
