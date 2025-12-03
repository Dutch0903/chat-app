import { Outlet } from "react-router-dom";
import { useAuth } from "../hooks/use-auth";
import Header from "./Header";
import Sidebar from "./Sidebar";

export default function Layout() {
  const { user } = useAuth();

  return (
    <div className="grid h-screen grid-rows-[auto_fr1] grid-cols-[200px-fr1]">
      {user && <Header />}
      <div className="flex">
        {user && <Sidebar />}
        <main className="flex-1">
          <Outlet />
        </main>
      </div>
    </div>
  );
}
