import { useEffect, useState, type ReactNode } from "react";
import {
  login as apiLogin,
  logout as apiLogout,
  register as apiRegister,
  getCurrentUser,
  type AuthenticatedUser,
} from "../../api";
import { AuthContext } from "./auth-context";

export function AuthProvider({ children }: { children: ReactNode }) {
  const [authenticatedUser, setAuthenticatedUser] =
    useState<AuthenticatedUser | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    checkAuth();
  }, []);

  const checkAuth = async () => {
    try {
      const result = await getCurrentUser();
      setAuthenticatedUser(result.data);
    } catch (error) {
      console.error("Failed to check auth status:", error);
      setAuthenticatedUser(null);
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

    setAuthenticatedUser(result.data);
  };

  const register = async (
    email: string,
    password: string,
    username: string,
  ) => {
    await apiRegister({
      body: { email, password, username },
    });

    setAuthenticatedUser(null);
  };

  const logout = async () => {
    await apiLogout();

    setAuthenticatedUser(null);
  };

  return (
    <AuthContext.Provider
      value={{ authenticatedUser, login, register, logout, isLoading }}
    >
      {children}
    </AuthContext.Provider>
  );
}
