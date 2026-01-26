// src/views/components/Sidebar.tsx
import { useAuth } from "../hooks/auth/use-auth.ts";
import UserList from "./Users/UserList.tsx";

export default function Sidebar() {
  const { authenticatedUser, logout } = useAuth();

  return (
    <div className="p-4">
      <div className="mb-6 font-bold text-lg">Chat App</div>
      <UserList />
      <div className="mt-auto text-sm absolute bottom-5">
        {/* Optionally show user info or logout button here */}
        Logged in as {authenticatedUser?.username}
        <div onClick={logout}>Logout</div>
      </div>
    </div>
  );
}
