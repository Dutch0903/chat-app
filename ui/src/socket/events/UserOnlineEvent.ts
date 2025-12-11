export interface UserOnlineEvent {
  readonly username: string;
}

export const UserOnlineEventFactory = (obj: {
  [key: string]: unknown;
}): UserOnlineEvent => {
  return {
    username: obj.username as string,
  };
};
