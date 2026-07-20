import React from "react";
import { Link } from "react-router";

export default function Footer() {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-dark text-parchment border-t border-sand-dark/20">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          {/* Logo Section */}
          <div className="space-y-4">
            <span className="text-xl font-bold tracking-tight text-cream">
              Joys<span className="text-gold">List</span>
            </span>
            <p className="text-xs text-sand-dark leading-relaxed">
              Experience local shopping redesigned. A premium, modern, and warm classified marketplace for your neighborhood.
            </p>
          </div>

          {/* Quick Links */}
          <div>
            <h3 className="text-sm font-semibold text-cream mb-4 uppercase tracking-wider">
              Explore
            </h3>
            <ul className="space-y-2 text-xs">
              <li>
                <Link to="/browse" className="text-sand-dark hover:text-gold transition-colors">
                  Browse Listings
                </Link>
              </li>
              <li>
                <Link to="/search" className="text-sand-dark hover:text-gold transition-colors">
                  Search Marketplace
                </Link>
              </li>
            </ul>
          </div>

          {/* Support */}
          <div>
            <h3 className="text-sm font-semibold text-cream mb-4 uppercase tracking-wider">
              Support
            </h3>
            <ul className="space-y-2 text-xs">
              <li>
                <span className="text-sand-dark">Safety Tips</span>
              </li>
              <li>
                <span className="text-sand-dark">Terms of Service</span>
              </li>
              <li>
                <span className="text-sand-dark">Privacy Policy</span>
              </li>
            </ul>
          </div>

          {/* Contact */}
          <div>
            <h3 className="text-sm font-semibold text-cream mb-4 uppercase tracking-wider">
              Community
            </h3>
            <p className="text-xs text-sand-dark leading-relaxed">
              Connecting buyers, sellers, and services globally. Built with luxury ergonomics.
            </p>
          </div>
        </div>

        {/* Bottom bar */}
        <div className="mt-12 pt-8 border-t border-sand-dark/10 flex flex-col md:flex-row justify-between items-center gap-4 text-xs text-sand-dark">
          <p>© {currentYear} JoysList Inc. All rights reserved.</p>
          <div className="flex gap-4">
            <span className="hover:text-gold transition-colors cursor-pointer">English (US)</span>
            <span className="hover:text-gold transition-colors cursor-pointer">USD ($)</span>
          </div>
        </div>
      </div>
    </footer>
  );
}
