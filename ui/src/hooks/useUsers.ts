import { useEffect, useState } from "react";
import { getOnlineUsers, getUsers, type User } from "../api";
import { USER_OFFLINE } from "../socket/events/UserOfflineEvent";
import { USER_ONLINE } from "../socket/events/UserOnlineEvent";
import { useWebSocketClient } from "./webhook/use-websocket";

export function useUsers() {
  const [users, setUsers] = useState<User[]>([]);
  const [onlineUsers, setOnlineUsers] = useState<Set<string>>(new Set());
  const [refresh, setRefresh] = useState(false);
  const { socketClient } = useWebSocketClient();

  useEffect(() => {
    Promise.all([getUsers(), getOnlineUsers()]).then(
      ([userResponse, onlineResponse]) => {
        setUsers(userResponse.data);
        setOnlineUsers(new Set(onlineResponse.data));
      },
    );
  }, [refresh]);

  useEffect(() => {
    if (!socketClient) return;

    const onlineSubscription = socketClient.subscribe(USER_ONLINE, (event) => {
      setOnlineUsers((prev) => new Set(prev).add(event.userId));
    });

    const offlineSubscription = socketClient.subscribe(
      USER_OFFLINE,
      (event) => {
        setOnlineUsers((prev) => {
          const next = new Set(prev);
          next.delete(event.userId);
          return next;
        });
      },
    );

    return () => {
      onlineSubscription.unsubscribe();
      offlineSubscription.unsubscribe();
    };
  }, [socketClient]);

  const refreshUsers = () => {
    setRefresh(!refresh);
  };

  const isUserOnline = (userId: string) => onlineUsers.has(userId);

  return {
    users,
    onlineUsers: Array.from(onlineUsers),
    isUserOnline,
    refreshUsers,
  };
}
