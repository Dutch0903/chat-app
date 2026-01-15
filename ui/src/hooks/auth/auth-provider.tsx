import { useEffect, useState, type ReactNode } from "react";
import {
  login as apiLogin,
  register as apiRegister,
  getCurrentUser,
  type AuthenticatedUser,
} from "../../api";
import { AuthContext } from "./auth-context";

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<AuthenticatedUser | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    checkAuth();
  }, []);

  const checkAuth = async () => {
    try {
      const result = await getCurrentUser();
      setUser(result.data);
    } catch (error) {
      console.error("Failed to check auth status:", error);
      setUser(null);
    } finally {
      setIsLoading(false);
    }
  };

  const login = async (username: string, password: string) => {
    const result = await apiLogin({
      body: {
        username,
        password,
      },
    });

    setUser(result.data);
  };

  const register = async (
    email: string,
    password: string,
    username: string,
  ) => {
    await apiRegister({
      body: { email, password, username },
    });

    setUser(null);
  };

  const logout = async () => {
    await fetch("/auth/logout", {
      method: "POST",
    });

    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout, isLoading }}>
      {children}
    </AuthContext.Provider>
  );
}
