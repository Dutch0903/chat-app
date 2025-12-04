import { Card, CardContent, CardHeader } from "@mui/material";
import LoginForm from "./components/LoginForm";

export default function Login() {
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
