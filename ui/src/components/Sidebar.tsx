// src/views/components/Sidebar.tsx
import { useAuth } from "../hooks/use-auth";
import OnlineUsers from "./OnlineUsers";

export default function Sidebar() {
  const { user } = useAuth();

  return (
    <aside className="w-56 bg-gray-100 text-black h-full flex flex-col p-4">
      <div className="mb-6 font-bold text-lg">Chat App</div>
      <OnlineUsers />
      <div className="mt-auto text-sm text-gray-400">
        {/* Optionally show user info or logout button here */}
        Logged in as {user?.username}
      </div>
    </aside>
  );
}
