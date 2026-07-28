import React, { useState } from "react";
import { Link, useNavigate } from "react-router";
import { Search, PlusCircle, LogOut, User, Menu, X, Shield } from "lucide-react";
import { useAuthStore } from "../../store/authStore";
import Button from "../ui/Button";
import Avatar from "../ui/Avatar";
import Dropdown from "../ui/Dropdown";

export default function Navbar() {
  const { isAuthenticated, user, logout } = useAuthStore();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [searchVal, setSearchVal] = useState("");
  const navigate = useNavigate();

  const handleSearchSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (searchVal.trim()) {
      navigate(`/search?keyword=${encodeURIComponent(searchVal)}`);
    }
  };

  const handleLogout = async () => {
    await logout();
    navigate("/");
  };

  const dropdownItems = [
    {
      label: "My Dashboard",
      icon: <User size={16} />,
      onClick: () => navigate("/dashboard"),
    },
    ...(user?.role === "ADMIN"
      ? [
          {
            label: "Admin Panel",
            icon: <Shield size={16} />,
            onClick: () => navigate("/admin"),
          },
        ]
      : []),
    {
      label: "Sign Out",
      icon: <LogOut size={16} />,
      onClick: handleLogout,
      danger: true,
    },
  ];

  return (
    <header className="sticky top-0 z-40 bg-cream/90 backdrop-blur-md border-b border-sand shadow-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-16 flex items-center justify-between">
        {/* Brand Logo */}
        <Link to="/" className="flex items-center gap-2.5 group">
          <img
            src="/logo.png"
            alt="JoysList Logo"
            className="w-9 h-9 rounded-full object-cover border-2 border-gold/70 group-hover:scale-105 transition-transform duration-200 shadow-sm"
          />
          <span className="text-2xl font-black tracking-tight text-walnut group-hover:text-gold transition-colors duration-200">
            Joys<span className="text-gold group-hover:text-walnut">List</span>
          </span>
        </Link>

        {/* Global Search Bar (desktop) */}
        <form
          onSubmit={handleSearchSubmit}
          className="hidden md:flex flex-1 max-w-md mx-8 relative"
        >
          <input
            type="text"
            placeholder="Search listings..."
            value={searchVal}
            onChange={(e) => setSearchVal(e.target.value)}
            className="w-full bg-ivory border border-sand rounded-xl pl-4 pr-10 py-1.5 text-sm text-coffee placeholder:text-sand-dark focus:outline-none focus:ring-2 focus:ring-gold/30 focus:border-gold transition-all duration-200"
          />
          <button
            type="submit"
            className="absolute right-3 top-1/2 -translate-y-1/2 text-sand-dark hover:text-gold cursor-pointer"
          >
            <Search size={18} />
          </button>
        </form>

        {/* Desktop Navigation */}
        <nav className="hidden md:flex items-center gap-4">
          <Link
            to="/browse"
            className="text-sm font-medium text-bronze hover:text-walnut transition-colors"
          >
            Browse
          </Link>

          {isAuthenticated ? (
            <>
              <Button
                variant="ghost"
                size="sm"
                className="gap-1.5"
                onClick={() => navigate("/ads/new")}
              >
                <PlusCircle size={16} />
                Post Ad
              </Button>
              <Dropdown
                align="right"
                trigger={
                  <button className="flex items-center gap-2 p-1 rounded-full hover:ring-2 hover:ring-sand transition-all focus:outline-none cursor-pointer">
                    <Avatar size="sm" fallback={user?.username || ""} />
                  </button>
                }
                items={dropdownItems}
              />
            </>
          ) : (
            <div className="flex items-center gap-2">
              <Link to="/login">
                <Button variant="ghost" size="sm">
                  Sign In
                </Button>
              </Link>
              <Link to="/register">
                <Button variant="primary" size="sm">
                  Join JoysList
                </Button>
              </Link>
            </div>
          )}
        </nav>

        {/* Mobile menu trigger */}
        <button
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          className="md:hidden p-1.5 rounded-lg text-sand-dark hover:text-coffee hover:bg-linen transition-colors cursor-pointer"
        >
          {mobileMenuOpen ? <X size={24} /> : <Menu size={24} />}
        </button>
      </div>

      {/* Mobile Drawer */}
      {mobileMenuOpen && (
        <div className="md:hidden border-t border-sand bg-cream-warm px-4 py-4 space-y-4 shadow-inner">
          <form onSubmit={handleSearchSubmit} className="relative">
            <input
              type="text"
              placeholder="Search listings..."
              value={searchVal}
              onChange={(e) => setSearchVal(e.target.value)}
              className="w-full bg-ivory border border-sand rounded-xl pl-4 pr-10 py-2 text-sm text-coffee"
            />
            <button type="submit" className="absolute right-3 top-1/2 -translate-y-1/2 text-sand-dark cursor-pointer">
              <Search size={18} />
            </button>
          </form>

          <div className="flex flex-col gap-3">
            <Link
              to="/browse"
              onClick={() => setMobileMenuOpen(false)}
              className="text-base font-medium text-bronze hover:text-walnut px-2 py-1"
            >
              Browse Listings
            </Link>

            {isAuthenticated ? (
              <>
                <Link
                  to="/ads/new"
                  onClick={() => setMobileMenuOpen(false)}
                  className="text-base font-medium text-bronze hover:text-walnut px-2 py-1 flex items-center gap-2"
                >
                  <PlusCircle size={18} /> Post Ad
                </Link>
                <Link
                  to="/dashboard"
                  onClick={() => setMobileMenuOpen(false)}
                  className="text-base font-medium text-bronze hover:text-walnut px-2 py-1 flex items-center gap-2"
                >
                  <User size={18} /> Dashboard
                </Link>
                {user?.role === "ADMIN" && (
                  <Link
                    to="/admin"
                    onClick={() => setMobileMenuOpen(false)}
                    className="text-base font-medium text-bronze hover:text-walnut px-2 py-1 flex items-center gap-2"
                  >
                    <Shield size={18} /> Admin Panel
                  </Link>
                )}
                <button
                  onClick={handleLogout}
                  className="text-base font-medium text-bronze-dark hover:text-walnut px-2 py-2 flex items-center gap-2 text-left cursor-pointer border-t border-sand pt-3"
                >
                  <LogOut size={18} /> Sign Out
                </button>
              </>
            ) : (
              <div className="flex flex-col gap-2 pt-2 border-t border-sand">
                <Link to="/login" onClick={() => setMobileMenuOpen(false)}>
                  <Button variant="ghost" size="md" className="w-full">
                    Sign In
                  </Button>
                </Link>
                <Link to="/register" onClick={() => setMobileMenuOpen(false)}>
                  <Button variant="primary" size="md" className="w-full">
                    Join JoysList
                  </Button>
                </Link>
              </div>
            )}
          </div>
        </div>
      )}
    </header>
  );
}
