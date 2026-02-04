import { MessageRegistry } from "../MessageRegistry";

export type MessageEvent = {
  content: string;
  senderId?: string;
  timestamp?: string;
};

export const MessageEventFactory = (obj: {
  [key: string]: unknown;
}): MessageEvent => {
  return {
    content: obj.content as string,
    senderId: obj.senderId as string | undefined,
    timestamp: obj.timestamp as string | undefined,
  };
};

// Self-register and export typed token
export const CHAT_MESSAGE = MessageRegistry.register<
  MessageEvent,
  { chatId: string }
>("CHAT_MESSAGE", {
  topic: ({ chatId }) => `/topic/chat/${chatId}`,
  factory: MessageEventFactory,
});
