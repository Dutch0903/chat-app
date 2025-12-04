// src/views/components/Sidebar.tsx
import { Link } from "react-router-dom";
import { useAuth } from "../hooks/use-auth";

export default function Sidebar() {
  const { user } = useAuth();

  return (
    <aside className="w-56 bg-gray-900 text-white h-full flex flex-col p-4">
      <div className="mb-6 font-bold text-lg">Chat App</div>
      <nav className="flex-1">
        <ul className="space-y-2">
          <li>
            <Link to="/app" className="hover:text-blue-400">
              Home
            </Link>
          </li>
          <li>
            <Link to="/app/channels" className="hover:text-blue-400">
              Channels
            </Link>
          </li>
          <li>
            <Link to="/app/profile" className="hover:text-blue-400">
              Profile
            </Link>
          </li>
        </ul>
      </nav>
      <div className="mt-auto text-sm text-gray-400">
        {/* Optionally show user info or logout button here */}
        Logged in as {user?.username}
      </div>
    </aside>
  );
}
