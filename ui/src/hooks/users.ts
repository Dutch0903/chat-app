import { useEffect, useState } from "react";
import { getOnlineUsers, getUsers, type User } from "../api";
import { Events } from "../socket/Events";
import { useWebSocketClient } from "./webhook/use-websocket";

export const useUsers = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [onlineUserIds, setOnlineUserIds] = useState<Set<string>>(new Set());
  const [refresh, setRefresh] = useState(false);
  const { socketClient } = useWebSocketClient();

  useEffect(() => {
    Promise.all([getUsers(), getOnlineUsers()]).then(
      ([userResponse, onlineResponse]) => {
        setUsers(userResponse.data);
        setOnlineUserIds(new Set(onlineResponse.data));
      },
    );
  }, [refresh]);

  useEffect(() => {
    if (!socketClient) return;

    const userOnlineSubscription = socketClient.subscribe(
      "/topic/user/online",
      {
        [Events.UserOnline]: (event) => {
          console.log("User went online", event.userId);
          setOnlineUserIds((prev) => new Set([...prev, event.userId]));
        },
      },
    );

    const userOfflineSubscription = socketClient.subscribe(
      "/topic/user/offline",
      {
        [Events.UserOffline]: (event) => {
          console.log("User went offline", event.userId);
          setOnlineUserIds((prev) => {
            const next = new Set(prev);
            next.delete(event.userId);
            return next;
          });
        },
      },
    );

    // Handle window close/refresh
    const handleWindowClose = () => {
      userOnlineSubscription.unsubscribe();
      userOfflineSubscription.unsubscribe();
      socketClient.deactivate();
    };

    window.addEventListener("beforeunload", handleWindowClose);

    return () => {
      userOnlineSubscription.unsubscribe();
      userOfflineSubscription.unsubscribe();
      window.removeEventListener("beforeunload", handleWindowClose);
      console.log("Unsubscribed from user online & offline");
    };
  }, [socketClient]);

  const refreshUsers = () => {
    setRefresh(!refresh);
  };

  const isUserOnline = (userId: string) => onlineUserIds.has(userId);

  return {
    users,
    onlineUserIds,
    isUserOnline,
    refreshUsers,
  };
};
