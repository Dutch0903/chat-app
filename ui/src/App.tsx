import { Box, CssBaseline, ThemeProvider } from "@mui/material";
import AppRoutes from "./AppRoutes";
import { AuthProvider } from "./hooks/auth/auth-provider";
import theme from "./theme";

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Box
        minHeight="100vh"
        display="flex"
        flexDirection="column"
        bgcolor="background.default"
      >
        <AuthProvider>
          <AppRoutes />
        </AuthProvider>
      </Box>
    </ThemeProvider>
  );
}

export default App;
