import { useEffect, useState, type ReactNode } from "react";
import { getCurrentUser, login as apiLogin } from "../../api";
import type { User } from "../../types/user";
import { AuthContext } from "./auth-context";

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
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
    const data = await apiLogin({
      body: {
        username,
        password
      }
    });
    console.log(data);

    setUser(data);
  };

  const register = async (
    email: string,
    password: string,
    username: string,
  ) => {
    const data = await apiFetch<User>("/auth/register", {
      method: "POST",
      body: JSON.stringify({
        email,
        password,
        username,
      }),
    });

    setUser(data);
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
