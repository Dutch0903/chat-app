import { Card, CardContent, CardHeader } from "@mui/material";
import { Navigate } from "react-router-dom";
import { useAuth } from "../../hooks/auth/use-auth.ts";
import LoginForm from "./components/LoginForm";

export default function Login() {
  const { user } = useAuth();

  if (user) {
    return <Navigate to="/chats" replace />;
  }

  return (
    <div className="flex justify-center p-4">
      <Card className="w-full max-w-md">
        <CardHeader>Login</CardHeader>
        <CardContent>
          <LoginForm />
        </CardContent>
      </Card>
    </div>
  );
}
