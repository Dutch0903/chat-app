import { MessageRegistry } from "../MessageRegistry";

export type UserOnlineEvent = {
  readonly userId: string;
};

export const UserOnlineEventFactory = (obj: {
  [key: string]: unknown;
}): UserOnlineEvent => {
  return {
    userId: obj.userId as string,
  };
};

// Self-register and export typed token
export const USER_ONLINE = MessageRegistry.register<UserOnlineEvent>(
  "USER_ONLINE",
  {
    topic: "/topic/users/online",
    factory: UserOnlineEventFactory,
  },
);
