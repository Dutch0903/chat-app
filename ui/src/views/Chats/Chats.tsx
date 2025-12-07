import { Client } from "@stomp/stompjs";
import { useEffect, useRef, useState } from "react";
import SockJS from "sockjs-client/dist/sockjs";

export default function Chats() {
  const [newMessage, setNewMessage] = useState("");
  const [messages, setMessages] = useState<string[]>([]);

  const webSocketUrl = "http://localhost:8090/ws";
  const clientRef = useRef<Client | null>(null);

  useEffect(() => {
    const socket = new SockJS(webSocketUrl);
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: (str) => console.log("Debug log: " + str),
      onConnect: () => {
        console.log("Connected to WebSocket");

        client.subscribe("/topic/messages", (response) => {
          console.log("Message received:", response.body);
          setMessages((prev) => [...prev, response.body]);
        });
      },
      onStompError: (frame) => {
        console.error("Broker reported error: " + frame.headers["message"]);
        console.error("Additional details: " + frame.body);
      },
    });

    client.activate();
    clientRef.current = client;

    return () => {
      client.deactivate();
      clientRef.current = null;
    };
  }, []);

  const sendMessage = () => {
    const client = clientRef.current;

    if (client && client.connected) {
      console.log("Sending message:", newMessage);
      client.publish({
        destination: "/app/sendMessage",
        body: JSON.stringify({
          content: newMessage,
        }),
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
