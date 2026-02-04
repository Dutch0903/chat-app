import { Client, type IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client/dist/sockjs";
import { MessageRegistry, type MessageTypeToken } from "./MessageRegistry";
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

  /**
   * Subscribe to a message type with parameters
   */
  subscribe<T, P extends Record<string, string>>(
    messageToken: MessageTypeToken<T, P>,
    params: P,
    callback: (data: T) => void,
  ): ReturnType<Client["subscribe"]>;

  /**
   * Subscribe to a message type without parameters
   */
  subscribe<T>(
    messageToken: MessageTypeToken<T, Record<string, never>>,
    callback: (data: T) => void,
  ): ReturnType<Client["subscribe"]>;

  /**
   * Implementation
   */
  subscribe<T, P extends Record<string, string>>(
    messageToken: MessageTypeToken<T, P>,
    paramsOrCallback: P | ((data: T) => void),
    callback?: (data: T) => void,
  ) {
    // Determine if params were provided
    const hasParams = typeof paramsOrCallback !== "function";
    const params = hasParams ? paramsOrCallback : undefined;
    const cb = hasParams ? callback! : (paramsOrCallback as (data: T) => void);

    const topic = MessageRegistry.getTopic(messageToken.__messageType, params);
    if (!topic) {
      throw new Error(
        `Cannot subscribe: message type "${messageToken.__messageType}" is not registered`,
      );
    }

    const factory = MessageRegistry.getFactory<T>(messageToken.__messageType);
    if (!factory) {
      throw new Error(
        `Cannot subscribe: no factory found for message type "${messageToken.__messageType}"`,
      );
    }

    return this.client.subscribe(topic, (message: IMessage) => {
      console.log(message);
      const body = JSON.parse(message.body) as Record<string, unknown>;
      const typedData = factory(body);
      console.log(body, typedData);
      cb(typedData);
    });
  }

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
