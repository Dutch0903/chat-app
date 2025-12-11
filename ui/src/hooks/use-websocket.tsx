import {
  createContext,
  useContext,
  useEffect,
  useRef,
  useState,
  type FC,
} from "react";
import SocketClient from "../socket/SocketClient";
import { useAuth } from "./use-auth";

type WebSocketClientContextType = {
  socketClient: SocketClient | undefined;
};

const WebSocketClientContext = createContext<
  WebSocketClientContextType | undefined
>(undefined);

export const WebSocketClientProvider: FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const { user, isLoading } = useAuth();
  const [socketClient, setSocketClient] = useState<SocketClient | undefined>(
    undefined,
  );
  const isConnecting = useRef(false);

  useEffect(() => {
    if (!isLoading && user && !socketClient && !isConnecting.current) {
      isConnecting.current = true;
      const client = new SocketClient("http://localhost:8090/ws");
      client
        .awaitConnection()
        .then(() => {
          setSocketClient(client);
        })
        .finally(() => {
          isConnecting.current = false;
        });
    }

    return () => {
      if (socketClient) {
        socketClient.deactivate();
      }
    };
  }, [user]);

  return (
    <WebSocketClientContext.Provider value={{ socketClient }}>
      {socketClient ? children : <div>Connection...</div>}
    </WebSocketClientContext.Provider>
  );
};

export const useWebSocketClient = () => {
  const context = useContext(WebSocketClientContext);
  if (context === undefined) {
    throw new Error("useWebSocketClient must be used with an ");
  }

  return context;
};
