import { useEffect, useRef, useState, type FC } from "react";
import SocketClient from "../../socket/SocketClient.ts";
import { useAuth } from "../auth/use-auth.ts";
import { WebSocketClientContext } from "./web-socket-client-context.ts";

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
  }, [user, isLoading, socketClient]);

  return (
    <WebSocketClientContext.Provider value={{ socketClient }}>
      {socketClient ? children : <div>Connection...</div>}
    </WebSocketClientContext.Provider>
  );
};
