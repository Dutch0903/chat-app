import { useEffect, useState } from "react";
import { CHAT_MESSAGE } from "../socket/events";
import { useWebSocketClient } from "./webhook/use-websocket";

export interface Message {
  id: string;
  content: string;
  senderId: string;
  timestamp: string;
  isOwn: boolean;
}

interface UseChatMessagesProps {
  chatId: string;
  currentUserId: string;
}

export function useChatMessages({
  chatId,
  currentUserId,
}: UseChatMessagesProps) {
  const [messages, setMessages] = useState<Message[]>([]);
  const { socketClient } = useWebSocketClient();

  useEffect(() => {
    if (!socketClient) return;

    // Clean API - token knows what params it needs!
    const subscription = socketClient.subscribe(
      CHAT_MESSAGE,
      { chatId },
      (event) => {
        const message: Message = {
          id: Date.now().toString(),
          content: event.content,
          senderId: event.senderId || "",
          timestamp: event.timestamp || new Date().toISOString(),
          isOwn: event.senderId === currentUserId,
        };
        setMessages((prev) => [...prev, message]);
      },
    );

    return () => {
      subscription.unsubscribe();
    };
  }, [chatId, socketClient, currentUserId]);

  const sendMessage = (content: string) => {
    if (!socketClient || !content.trim()) return;

    socketClient.publish({
      endpoint: `/app/chat/${chatId}/send`,
      body: {
        content: content.trim(),
      },
    });
  };

  return {
    messages,
    sendMessage,
  };
}
