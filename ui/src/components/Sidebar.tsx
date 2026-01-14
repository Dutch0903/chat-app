// src/views/components/Sidebar.tsx
import { useAuth } from "../hooks/auth/use-auth.ts";
import OnlineUsers from "./OnlineUsers";

export default function Sidebar() {
  const { user } = useAuth();

  return (
    <div className="p-4">
      <div className="mb-6 font-bold text-lg">Chat App</div>
      <OnlineUsers />
      <div className="mt-auto text-sm text-gray-400 absolute bottom-5">
        {/* Optionally show user info or logout button here */}
        Logged in as {user?.username}
      </div>
    </div>
  );
}
