export interface UserOnlineEvent {
  readonly userId: string;
}

export const UserOnlineEventFactory = (obj: {
  [key: string]: unknown;
}): UserOnlineEvent => {
  return {
    userId: obj.userId as string,
  };
};
