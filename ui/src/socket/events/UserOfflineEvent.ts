import { MessageRegistry } from "../MessageRegistry";

export type UserOfflineEvent = {
  readonly userId: string;
};

export const UserOfflineEventFactory = (obj: {
  [key: string]: unknown;
}): UserOfflineEvent => {
  return {
    userId: obj.userId as string,
  };
};

// Self-register and export typed token
export const USER_OFFLINE = MessageRegistry.register<UserOfflineEvent>(
  "USER_OFFLINE",
  {
    topic: "/topic/users/offline",
    factory: UserOfflineEventFactory,
  },
);
