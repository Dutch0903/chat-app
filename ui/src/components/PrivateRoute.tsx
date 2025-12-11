import type { FC } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "../hooks/use-auth";
import { WebSocketClientProvider } from "../hooks/use-websocket";
import Layout from "./Layout";

export const PrivateRoute: FC = () => {
  const { user, isLoading } = useAuth();
  const location = useLocation();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return user ? (
    <WebSocketClientProvider>
      <Layout />
    </WebSocketClientProvider>
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};
