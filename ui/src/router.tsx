import { createBrowserRouter, Navigate } from "react-router-dom";
import Layout from "./components/Layout";
import Chats from "./views/Chats/Chats";
import Login from "./views/Login/Login";
import Register from "./views/Register/Register";

const router = createBrowserRouter([
  {
    element: <Layout />,
    children: [
      {
        path: "/app/chats",
        element: <Chats />,
      },
      {
        path: "/*",
        element: <Navigate to="/app/chats" replace />,
      },
    ],
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
]);

export default router;
