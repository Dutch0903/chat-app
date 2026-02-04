import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Chat from "./components/Chat/Chat";
import { PrivateRoute } from "./components/PrivateRoute";
import Dashboard from "./views/Dashboard/Dashboard";
import Login from "./views/Login/Login";
import Register from "./views/Register/Register";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<PrivateRoute />}>
          <Route path="/" element={<Dashboard />} />
          <Route path="/chats/new" element={<Chat />} />
          <Route path="/chats/:id" element={<Chat />} />
        </Route>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;
