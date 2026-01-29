import { useState } from "react";
import { startPrivateChat, getUsers, type User } from "../../api";
import { useNavigate } from "react-router-dom";

interface NewChatProps {
  userId: string;
}

export default function NewChat({ userId }: NewChatProps) {
  const [message, setMessage] = useState("");
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();

  // Fetch user details
  useState(() => {
    getUsers().then((response) => {
      const foundUser = response.data.find((u) => u.id === userId);
      if (foundUser) {
        setUser(foundUser);
      }
    });
  });

  const handleSendMessage = async () => {
    if (!message.trim()) return;

    setIsLoading(true);
    try {
      // Create the chat
      const response = await startPrivateChat({
        body: { participantId: userId },
      });

      // Navigate to the newly created chat
      // Assuming the response contains the chat details
      if (response.data && typeof response.data === "object" && "id" in response.data) {
        navigate(`/chats/${response.data.id}`);
      } else {
        // If response doesn't have chat ID, refresh chats and navigate
        window.location.reload();
      }
    } catch (error) {
      console.error("Failed to create chat:", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col h-full">
      {/* Header */}
      <div className="border-b border-gray-200 font-bold">
        New chat with {user?.username || "..."}
      </div>

      {/* Empty message area */}
      <div className="flex-1 flex items-center justify-center text-gray-500">
        No messages yet. Start the conversation!
      </div>

      {/* Input area */}
      <div className="border-t border-gray-200 flex gap-2">
        <input
          type="text"
          placeholder="Type a message..."
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyPress={(e) => {
            if (e.key === "Enter" && !isLoading) {
              handleSendMessage();
            }
          }}
          className="flex-1 px-3 py-2 border border-gray-300 rounded outline-none focus:ring-2 focus:ring-blue-500 disabled:bg-gray-100"
          disabled={isLoading}
        />
        <button
          onClick={handleSendMessage}
          disabled={!message.trim() || isLoading}
          className={`px-4 py-2 rounded text-white transition-colors ${
            message.trim() && !isLoading
              ? "bg-blue-500 hover:bg-blue-600 cursor-pointer"
              : "bg-gray-300 cursor-not-allowed"
          }`}
        >
          {isLoading ? "Sending..." : "Send"}
        </button>
      </div>
    </div>
  );
}
