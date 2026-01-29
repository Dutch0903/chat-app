import { useEffect, useState } from "react";
import { getChatDetails, type ChatDetails } from "../../api";

interface ExistingChatProps {
  chatId: string;
}

export default function ExistingChat({ chatId }: ExistingChatProps) {
  const [chat, setChat] = useState<ChatDetails | null>(null);
  const [message, setMessage] = useState("");
  const [isLoading, setIsLoading] = useState(true);

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

  const handleSendMessage = () => {
    if (!message.trim()) return;

    // TODO: Implement send message via WebSocket or API
    console.log("Sending message:", message);
    setMessage("");
  };

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

      {/* Messages area */}
      <div className="flex-1 p-4 overflow-y-auto">
        {/* TODO: Render messages */}
        <div className="text-gray-500 text-center">
          Messages will appear here
        </div>
      </div>

      {/* Input area */}
      <div className="p-4 border-t border-gray-200 flex gap-2">
        <input
          type="text"
          placeholder="Type a message..."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyPress={(e) => {
            if (e.key === "Enter") {
              handleSendMessage();
            }
          }}
          className="flex-1 px-3 py-2 border border-gray-300 rounded outline-none focus:ring-2 focus:ring-blue-500"
        />
        <button
          onClick={handleSendMessage}
          disabled={!message.trim()}
          className={`px-4 py-2 rounded text-white transition-colors ${
            message.trim()
              ? "bg-blue-500 hover:bg-blue-600 cursor-pointer"
              : "bg-gray-300 cursor-not-allowed"
          }`}
        >
          Send
        </button>
      </div>
    </div>
  );
}
