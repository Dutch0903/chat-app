import { createContext } from "react";
import type { AuthenticatedUser } from "../../api";

export type AuthContextType = {
  authenticatedUser: AuthenticatedUser | null;
  login: (username: string, password: string) => Promise<void>;
  register: (
    email: string,
    password: string,
    username: string,
  ) => Promise<void>;
  logout: () => Promise<void>;
  isLoading: boolean;
};

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined,
);
