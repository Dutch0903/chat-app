import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import { PrivateRoute } from "./components/PrivateRoute";
import Chats from "./views/Chats/Chats";
import Login from "./views/Login/Login";
import Register from "./views/Register/Register";
import Chat from "./components/Chat/Chat";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<PrivateRoute />}>
          <Route path="/chats" element={<Chats />} />
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
