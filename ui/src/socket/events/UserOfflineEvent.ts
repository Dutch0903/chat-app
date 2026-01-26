export interface UserOfflineEvent {
  readonly userId: string;
}

export const UserOfflineEventFactory = (obj: {
  [key: string]: unknown;
}): UserOfflineEvent => {
  return {
    userId: obj.userId as string,
  };
};
