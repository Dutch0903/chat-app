import { createContext } from "react";
import type { User } from "../../types/user";

export type AuthContextType = {
  user: User | null;
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
