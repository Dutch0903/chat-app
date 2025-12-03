import { RouterProvider } from "react-router-dom";
import { AuthProvider } from "./hooks/use-auth";
import router from "./router";

function App() {
  return (
    <div className="grid h-screen grid-rows-[auto_1fr_auto] bg-red-500">
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>
    </div>
  );
}

export default App;
