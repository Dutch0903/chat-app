import { useContext } from "react";
import { WebSocketClientContext } from "./web-socket-client-context";

export const useWebSocketClient = () => {
  const context = useContext(WebSocketClientContext);
  if (context === undefined) {
    throw new Error("useWebSocketClient must be used with an ");
  }

  return context;
};
