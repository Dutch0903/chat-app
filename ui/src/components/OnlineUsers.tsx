import { useEffect, useState } from "react";
import { useWebSocketClient } from "../hooks/use-websocket";
import { Events } from "../socket/Events";
import type { UserOfflineEvent } from "../socket/events/UserOfflineEvent";
import type { UserOnlineEvent } from "../socket/events/UserOnlineEvent";

export default function OnlineUsers() {
  const [onlineUsers, setOnlineUsers] = useState<string[]>([]);
  const { socketClient } = useWebSocketClient();

  useEffect(() => {
    if (!socketClient) {
      return;
    }
    console.log("Subscribing");
    const subscription = socketClient?.subscribe("/topic/online", {
      [Events.UserOnline]: (event: UserOnlineEvent) => {
        console.log("User come online: ", event);
        setOnlineUsers([...onlineUsers, event.username]);
      },
      [Events.UserOffline]: (event: UserOfflineEvent) => {
        console.log("User went offline: ", event);
        const index: number = onlineUsers.indexOf(event.username);
        if (index > -1) {
          setOnlineUsers([...onlineUsers.splice(index, 1)]);
        }
      },
    });

    return () => {
      subscription?.unsubscribe();
    };
  });

  return (
    <ol>
      {onlineUsers.map((onlineUser, index) => (
        <li>
          {index}: {onlineUser}
        </li>
      ))}
    </ol>
  );
}
