import { create } from "zustand";
import type { AuthResponse, LoginRequest, RegisterRequest } from "../types";
import api from "../services/api";

interface AuthState {
  user: {
    userId: number;
    username: string;
    role: "USER" | "ADMIN";
  } | null;
  accessToken: string | null;
  refreshToken: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (credentials: LoginRequest) => Promise<void>;
  register: (credentials: RegisterRequest) => Promise<void>;
  logout: () => Promise<void>;
  hydrate: () => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  accessToken: null,
  refreshToken: null,
  isAuthenticated: false,
  isLoading: false,

  login: async (credentials) => {
    console.log("[AuthStore] Initiating login for user:", credentials.username);
    set({ isLoading: true });
    try {
      const response = await api.post<AuthResponse>("/auth/login", credentials);
      const data = response.data;
      console.log("[AuthStore] Login successful:", data);

      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("refreshToken", data.refreshToken);
      localStorage.setItem("user", JSON.stringify({
        userId: data.userId,
        username: data.username,
        role: data.role,
      }));

      set({
        accessToken: data.accessToken,
        refreshToken: data.refreshToken,
        user: {
          userId: data.userId,
          username: data.username,
          role: data.role,
        },
        isAuthenticated: true,
      });
    } catch (err) {
      console.error("[AuthStore] Login failed:", err);
      throw err;
    } finally {
      set({ isLoading: false });
    }
  },

  register: async (credentials) => {
    console.log("[AuthStore] Initiating registration for user:", credentials.username);
    set({ isLoading: true });
    try {
      const response = await api.post<AuthResponse>("/auth/register", credentials);
      const data = response.data;
      console.log("[AuthStore] Registration successful:", data);

      localStorage.setItem("accessToken", data.accessToken);
      localStorage.setItem("refreshToken", data.refreshToken);
      localStorage.setItem("user", JSON.stringify({
        userId: data.userId,
        username: data.username,
        role: data.role,
      }));

      set({
        accessToken: data.accessToken,
        refreshToken: data.refreshToken,
        user: {
          userId: data.userId,
          username: data.username,
          role: data.role,
        },
        isAuthenticated: true,
      });
    } catch (err) {
      console.error("[AuthStore] Registration failed:", err);
      throw err;
    } finally {
      set({ isLoading: false });
    }
  },

  logout: async () => {
    console.log("[AuthStore] Logging out user");
    try {
      await api.post("/auth/logout");
    } catch (e) {
      console.warn("[AuthStore] Logout endpoint error ignored:", e);
    } finally {
      localStorage.removeItem("accessToken");
      localStorage.removeItem("refreshToken");
      localStorage.removeItem("user");
      set({
        user: null,
        accessToken: null,
        refreshToken: null,
        isAuthenticated: false,
      });
    }
  },

  hydrate: () => {
    console.log("[AuthStore] Hydrating auth state from localStorage");
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");
    const userStr = localStorage.getItem("user");

    if (accessToken && refreshToken && userStr) {
      try {
        const user = JSON.parse(userStr);
        console.log("[AuthStore] Successfully hydrated user:", user.username);
        set({
          accessToken,
          refreshToken,
          user,
          isAuthenticated: true,
        });
      } catch (e) {
        console.error("[AuthStore] Failed parsing cached user string, resetting:", e);
        localStorage.clear();
      }
    } else {
      console.log("[AuthStore] No cached session found during hydration");
    }
  },
}));

// Listen to interceptor logout events
if (typeof window !== "undefined") {
  window.addEventListener("auth-logout", () => {
    useAuthStore.setState({
      user: null,
      accessToken: null,
      refreshToken: null,
      isAuthenticated: false,
    });
  });
}
