import { useEffect, useState } from "react";
import { Outlet, useNavigate } from "react-router";
import { motion, AnimatePresence } from "framer-motion";
import { useAuthStore } from "../../store/authStore";
import Navbar from "./Navbar";
import Footer from "./Footer";
import Modal from "../ui/Modal";
import Button from "../ui/Button";
import { AlertTriangle, LogIn } from "lucide-react";
import { toast } from "sonner";

export default function AppLayout() {
  const hydrate = useAuthStore((state) => state.hydrate);
  const navigate = useNavigate();
  const [showSessionModal, setShowSessionModal] = useState(false);

  useEffect(() => {
    hydrate();
  }, [hydrate]);

  useEffect(() => {
    const handleSessionExpired = () => {
      console.warn("[AppLayout] Session expired event received.");
      toast.error("Your session has expired. Please log in again.", { duration: 5000 });
      setShowSessionModal(true);
    };

    window.addEventListener("session-expired", handleSessionExpired);
    return () => {
      window.removeEventListener("session-expired", handleSessionExpired);
    };
  }, []);

  const handleReLogin = () => {
    setShowSessionModal(false);
    navigate("/login");
  };

  return (
    <div className="flex flex-col min-h-screen bg-cream text-coffee">
      {/* Sticky Navigation Header */}
      <Navbar />

      {/* Main Container */}
      <main className="flex-1 flex flex-col w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <AnimatePresence mode="wait">
          <motion.div
            initial={{ opacity: 0, y: 10 }}
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: -10 }}
            transition={{ duration: 0.25, ease: "easeInOut" }}
            className="flex-1 flex flex-col"
          >
            <Outlet />
          </motion.div>
        </AnimatePresence>
      </main>

      {/* Footer */}
      <Footer />

      {/* Session Expired Warning Modal */}
      <Modal
        isOpen={showSessionModal}
        onClose={() => setShowSessionModal(false)}
        title="Session Expired"
      >
        <div className="space-y-4 pt-2">
          <div className="flex items-center gap-3 p-4 bg-linen/70 border border-sand rounded-xl text-walnut">
            <AlertTriangle className="text-gold shrink-0" size={28} />
            <div>
              <h4 className="font-bold text-sm">Action Required</h4>
              <p className="text-xs text-bronze mt-0.5">
                Your authentication session has timed out or expired. Please sign in again to continue managing listings and accessing protected features.
              </p>
            </div>
          </div>

          <div className="flex justify-end gap-3 pt-4 border-t border-sand">
            <Button
              type="button"
              variant="ghost"
              onClick={() => setShowSessionModal(false)}
            >
              Dismiss
            </Button>
            <Button
              type="button"
              variant="primary"
              onClick={handleReLogin}
              leftIcon={<LogIn size={16} />}
            >
              Re-login Now
            </Button>
          </div>
        </div>
      </Modal>
    </div>
  );
}
