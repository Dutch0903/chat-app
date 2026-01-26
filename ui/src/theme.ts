import { createTheme } from "@mui/material";

const theme = createTheme({
  palette: {
    mode: "light",
    primary: {
      main: "#27374D", // dark blue-grey
      contrastText: "#fff",
    },
    secondary: {
      main: "#526D82", // medium blue-grey
      contrastText: "#fff",
    },
    background: {
      default: "#DDE6ED", // very light blue-grey
      paper: "#9DB2BF", // light blue-grey
    },
    success: {
      main: "#526D82",
    },
    error: {
      main: "#27374D",
    },
    warning: {
      main: "#9DB2BF",
    },
    info: {
      main: "#526D82",
    },
    text: {
      primary: "#27374D",
      secondary: "#526D82",
      disabled: "#9DB2BF",
    },
    divider: "#9DB2BF",
  },
  typography: {
    fontFamily: "Inter, Roboto, Arial, sans-serif",
    h1: {
      fontWeight: 700,
      fontSize: "2.5rem",
      color: "#27374D",
    },
    h2: {
      fontWeight: 600,
      fontSize: "2rem",
      color: "#526D82",
    },
    h3: {
      fontWeight: 500,
      fontSize: "1.5rem",
      color: "#9DB2BF",
    },
    body1: {
      fontSize: "1rem",
      color: "#27374D",
    },
    body2: {
      fontSize: "0.95rem",
      color: "#526D82",
    },
    button: {
      textTransform: "none",
      fontWeight: 600,
      color: "#27374D",
    },
  },
  shape: {
    borderRadius: 12,
  },
  components: {
    MuiContainer: {
      styleOverrides: {
        root: {
          background: "#9DB2BF",
          boxShadow: "0 2px 8px rgba(39,55,77,0.08)",
          borderRadius: 16,
        },
      },
    },
  },
});

export default theme;
