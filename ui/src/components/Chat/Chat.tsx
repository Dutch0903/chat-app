import { useParams, useSearchParams } from "react-router-dom";
import NewChat from "./NewChat";
import ExistingChat from "./ExistingChat";

export default function Chat() {
  const params = useParams();
  const [searchParams] = useSearchParams();

  const isNewChat = searchParams.has('userId');


  if (isNewChat) {
    const userId = searchParams.get("userId");
    
    if (!userId) {
      return <div>Error: No user specified</div>;
    }
    return <NewChat userId={userId} />;
  }

  // Existing chat
  const chatId = params.id;
  if (!chatId) {
    return <div>Error: No chat ID</div>;
  }

  return <ExistingChat chatId={chatId} />;
}