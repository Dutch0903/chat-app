import { Events } from "./Events";
import { MessageEventFactory } from "./events/MessageEvent";
import { UserOfflineEventFactory } from "./events/UserOfflineEvent";
import { UserOnlineEventFactory } from "./events/UserOnlineEvent";

export const EventRegistry = {
  [Events.Chat]: MessageEventFactory,
  [Events.UserOnline]: UserOnlineEventFactory,
  [Events.UserOffline]: UserOfflineEventFactory,
} as const;
