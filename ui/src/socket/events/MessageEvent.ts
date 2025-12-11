export type MessageEvent = {
  content: string;
};

export const MessageEventFactory = (obj: {
  [key: string]: unknown;
}): MessageEvent => {
  return {
    content: obj.content as string,
  };
};
