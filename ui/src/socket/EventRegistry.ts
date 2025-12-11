import { Events } from "./Events";
import { UserOfflineEventFactory } from "./events/UserOfflineEvent";
import { UserOnlineEventFactory } from "./events/UserOnlineEvent";

export const EventRegistry = {
  [Events.UserOnline]: UserOnlineEventFactory,
  [Events.UserOffline]: UserOfflineEventFactory,
} as const;
