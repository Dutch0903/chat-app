import type { IMessage } from "@stomp/stompjs";
import type { ValueOf } from "type-fest";
import { Events } from "./Events";

export type Message = {
  endpoint: string;
  body: MessageBody;
} & IMessage;

export type MessageBody = {
  content: string;
  type: string;
};

export type AwaitConnectionOptions = {
  retries: number;
  timeinterval: number;
  current: number;
};

export type SubscribeOptions = {
  topic: string;
  callback: (message: IMessage) => void;
  messagesTypes: ValueOf<typeof Events>[];
};
