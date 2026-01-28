import { useParams, useSearchParams, useLocation } from "react-router-dom";
import NewChatView from "./NewChatView";
import ExistingChatView from "./ExistingChatView";

export default function Chat() {
  const params = useParams();
  const [searchParams] = useSearchParams();
  const location = useLocation();

  // Check if we're on the "new chat" route
  const isNewChat = location.pathname === "/chats/new";

  if (isNewChat) {
    const userId = searchParams.get("userId");
    if (!userId) {
      return <div>Error: No user specified</div>;
    }
    return <NewChatView userId={userId} />;
  }

  // Existing chat
  const chatId = params.id;
  if (!chatId) {
    return <div>Error: No chat ID</div>;
  }

  return <ExistingChatView chatId={chatId} />;
}