import { useParams, useSearchParams } from "react-router-dom";
import ExistingChat from "./ExistingChat";
import NewChat from "./NewChat";

export default function Chat() {
  const params = useParams();
  const [searchParams] = useSearchParams();

  const isNewChat = searchParams.has("userId");

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
