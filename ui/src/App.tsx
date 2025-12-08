import { BrowserRouter, Route, Routes } from "react-router-dom";
import { PrivateRoute } from "./components/PrivateRoute";
import { AuthProvider } from "./hooks/use-auth";
import Chats from "./views/Chats/Chats";
import Login from "./views/Login/Login";
import Register from "./views/Register/Register";

function App() {
  return (
    <div className="grid h-screen grid-rows-[auto_1fr_auto] bg-red-100">
      <AuthProvider>
        <BrowserRouter>
          <Routes>
            <Route element={<PrivateRoute />}>
              <Route path="/chats" element={<Chats />} />
            </Route>

            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </div>
  );
}

export default App;
