import { useState } from "react";
import { useWebSocketClient } from "../../hooks/use-websocket";

export default function Chats() {
  const [newMessage, setNewMessage] = useState("");
  const [messages, setMessages] = useState<string[]>([]);

  const { socketClient } = useWebSocketClient();

  const sendMessage = () => {
    if (socketClient?.isConnected()) {
      console.log("Sending message:", newMessage);
      socketClient.publish({
        endpoint: "/app/sendMessage",
        body: {
          content: newMessage,
          type: "message",
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
