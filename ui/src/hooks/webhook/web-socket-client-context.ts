import { createContext } from "react";
import type SocketClient from "../../socket/SocketClient";

export type WebSocketClientContextType = {
  socketClient: SocketClient | undefined;
};

export const WebSocketClientContext = createContext<
  WebSocketClientContextType | undefined
>(undefined);
