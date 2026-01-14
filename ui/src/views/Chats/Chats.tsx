import { useEffect, useState } from "react";
import { useWebSocketClient } from "../../hooks/webhook/use-websocket";
import { Events } from "../../socket/Events";
import type { MessageEvent } from "../../socket/events/MessageEvent";

export default function Chats() {
  const [newMessage, setNewMessage] = useState("");
  const [messages, setMessages] = useState<string[]>([]);

  const { socketClient } = useWebSocketClient();

  useEffect(() => {
    if (!socketClient) {
      return;
    }

    const subscription = socketClient?.subscribe("/topic/messages", {
      [Events.Chat]: (event: MessageEvent) => {
        console.log("Message received: ", event);
        setMessages([...messages, event.content]);
      },
    });

    return () => {
      subscription?.unsubscribe();
    };
  });

  const sendMessage = () => {
    if (socketClient?.isConnected()) {
      console.log("Sending message:", newMessage);
      socketClient.publish({
        endpoint: "/app/sendMessage",
        body: {
          content: newMessage,
          type: Events.Chat,
        },
      });
    } else {
      console.error("Client is not connected!");
    }
  };

  return (
    <div>
      <span>Messages:</span>
      <ul>
        {messages.map((msg, index) => (
          <li>
            {index}: {msg}
          </li>
        ))}
      </ul>
      <input
        type="text"
        placeholder="Enter your name"
        value={newMessage}
        onChange={(e) => setNewMessage(e.target.value)}
      />
      <button onClick={sendMessage}>Send</button>
    </div>
  );
}
