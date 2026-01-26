import type { FC } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "../hooks/auth/use-auth.ts";
import { WebSocketClientProvider } from "../hooks/webhook/web-socket-client-provider.tsx";
import Layout from "./Layout";

export const PrivateRoute: FC = () => {
  const { authenticatedUser, isLoading } = useAuth();
  const location = useLocation();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return authenticatedUser ? (
    <WebSocketClientProvider>
      <Layout />
    </WebSocketClientProvider>
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};
