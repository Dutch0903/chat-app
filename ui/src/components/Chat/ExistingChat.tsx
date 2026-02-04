import { useEffect, useState } from "react";
import { getChatDetails, type ChatDetails } from "../../api";
import { useAuth } from "../../hooks/auth/use-auth";
import Messages from "./Messages";

interface ExistingChatProps {
  chatId: string;
}

export default function ExistingChat({ chatId }: ExistingChatProps) {
  const [chat, setChat] = useState<ChatDetails | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const { authenticatedUser } = useAuth();

  useEffect(() => {
    getChatDetails({
      path: { chatId },
    })
      .then((response) => {
        setChat(response.data);
      })
      .catch((error) => {
        console.error("Failed to load chat:", error);
      })
      .finally(() => {
        setIsLoading(false);
      });
  }, [chatId]);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-full">
        Loading chat...
      </div>
    );
  }

  if (!chat) {
    return (
      <div className="flex items-center justify-center h-full">
        Chat not found
      </div>
    );
  }

  return (
    <div className="flex flex-col h-full">
      {/* Header */}
      <div className="p-4 border-b border-gray-200 font-bold">
        {chat.name || "Private Chat"}
      </div>

      {/* Messages component */}
      {authenticatedUser && (
        <Messages chatId={chatId} currentUserId={authenticatedUser.id} />
      )}
    </div>
  );
}
