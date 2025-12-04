import type { FC } from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "../hooks/use-auth";
import Layout from "./Layout";

export const PrivateRoute: FC = () => {
  const { user, isLoading } = useAuth();
  const location = useLocation();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return user ? (
    <Layout />
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};
