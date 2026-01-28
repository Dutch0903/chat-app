import { useNavigate } from "react-router-dom";
import { type User } from "../../api";
import { useAuth } from "../../hooks/auth/use-auth";
import { useUsers } from "../../hooks/users";
import { useChats } from "../../hooks/chats";
import UserListItem from "./UserListItem";

function UserList() {
  const navigate = useNavigate();
  const { users, isUserOnline } = useUsers();
  const { authenticatedUser } = useAuth();
  const { hasPrivateChatBetween, chats } = useChats();

  const otherUsers = users.filter((user) => user.id !== authenticatedUser?.id);

  const handleUserClick = (userId: string) => {
    // Find existing chat with this user
    const existingChat = chats.find(
      (chat) =>
        chat.type === "PRIVATE" &&
        chat.participants.includes(userId) &&
        chat.participants.includes(authenticatedUser?.id || "")
    );

    if (existingChat) {
      // Navigate to existing chat
      navigate(`/chats/${existingChat.id}`);
    } else {
      // Navigate to new chat with userId as query param
      navigate(`/chats/new?userId=${userId}`);
    }
  };

  return (
    <div>
      {otherUsers.map((user: User) => {
        const hasChat = hasPrivateChatBetween([
          authenticatedUser?.id || "",
          user.id || "",
        ]);
        return (
          <UserListItem
            key={user.id}
            user={user}
            isOnline={isUserOnline(user.id)}
            hasChat={hasChat}
            onClick={() => handleUserClick(user.id || "")}
          />
        );
      })}
    </div>
  );
}

export default UserList;
