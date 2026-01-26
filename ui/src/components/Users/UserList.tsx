import { type User } from "../../api";
import { useAuth } from "../../hooks/auth/use-auth";
import { useUsers } from "../../hooks/users";
import UserListItem from "./UserListItem";

function UserList() {
  const { users, isUserOnline } = useUsers();
  const { authenticatedUser } = useAuth();

  const otherUsers = users.filter((user) => user.id !== authenticatedUser?.id);

  return (
    <div>
      {otherUsers.map((user: User) => {
        return (
          <UserListItem
            key={user.id}
            user={user}
            isOnline={isUserOnline(user.id)}
          />
        );
      })}
    </div>
  );
}

export default UserList;
