export interface UserOfflineEvent {
  readonly username: string;
}

export const UserOfflineEventFactory = (obj: {
  [key: string]: unknown;
}): UserOfflineEvent => {
  return {
    username: obj.username as string,
  };
};
