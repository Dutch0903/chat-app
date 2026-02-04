import type { IMessage } from "@stomp/stompjs";

export type Message = {
  endpoint: string;
  body: MessageBody;
} & IMessage;

export type MessageBody = {
  content: string;
};

export type AwaitConnectionOptions = {
  retries: number;
  timeinterval: number;
  current: number;
};
