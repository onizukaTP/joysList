import { create } from "zustand";
import { AuthResponse, LoginRequest, RegisterRequest } from "../types";
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
    set({ isLoading: true });
    try {
      const response = await api.post<AuthResponse>("/auth/login", credentials);
      const data = response.data;

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
    } finally {
      set({ isLoading: false });
    }
  },

  register: async (credentials) => {
    set({ isLoading: true });
    try {
      const response = await api.post<AuthResponse>("/auth/register", credentials);
      const data = response.data;

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
    } finally {
      set({ isLoading: false });
    }
  },

  logout: async () => {
    try {
      await api.post("/auth/logout");
    } catch (e) {
      // Ignore errors on logout endpoint and clean local state regardless
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
    const accessToken = localStorage.getItem("accessToken");
    const refreshToken = localStorage.getItem("refreshToken");
    const userStr = localStorage.getItem("user");

    if (accessToken && refreshToken && userStr) {
      try {
        const user = JSON.parse(userStr);
        set({
          accessToken,
          refreshToken,
          user,
          isAuthenticated: true,
        });
      } catch (e) {
        localStorage.clear();
      }
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
