import { Client, type IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";
import { EventRegistry } from "./EventRegistry";
import type { AwaitConnectionOptions, Message } from "./SocketClient.types";

export default class SocketClient {
  private url: string;
  private client: Client;

  constructor(url: string) {
    this.url = url;

    this.client = new Client({
      webSocketFactory: () => new SockJS(this.url),
      // debug: (str) => console.log("Debug log: " + str),
      onConnect: () => {
        console.log("Client Connected");
      },
      onStompError: (frame) => {
        console.error("Broker reported error: " + frame.headers["message"]);
        console.error("Additional details: " + frame.body);
      },
    });

    this.client.activate();
  }

  deactivate = () => this.client.deactivate();

  publish = ({ endpoint, body }: Message) => {
    this.client.publish({
      destination: endpoint,
      body: JSON.stringify(body),
    });
  };

  isConnected = () => this.client.connected;

  subscribe = <T extends keyof typeof EventRegistry>(
    topic: string,
    handlers: {
      [K in T]: (event: ReturnType<(typeof EventRegistry)[K]>) => void;
    },
  ) => {
    return this.client.subscribe(topic, (message: IMessage) => {
      const body = JSON.parse(message.body) as { type: string } & {
        [key: string]: unknown;
      };

      const type = body.type as T;

      const factory = EventRegistry[type];
      if (type && handlers[type] && factory) {
        const event = factory(body);
        handlers[type](
          event as ReturnType<(typeof EventRegistry)[typeof type]>,
        );
      }
    });
  };

  awaitConnection = async (
    options: AwaitConnectionOptions = {
      retries: 3,
      timeinterval: 100,
      current: 0,
    },
  ): Promise<void> => {
    if (this.isConnected()) {
      return;
    }

    const { retries, timeinterval, current } = options;
    return new Promise<void>((resolve, reject) => {
      setTimeout(() => {
        if (this.isConnected()) {
          resolve();
        } else {
          console.log("failed to connect! retrying");
          if (current >= retries) {
            console.log("failed to connect within the specified time interval");
            reject(new Error("Failed to connect"));
          } else {
            this.awaitConnection({ ...options, current: current + 1 });
          }
        }
      }, timeinterval);
    });
  };
}
