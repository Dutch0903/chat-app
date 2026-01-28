import type { User } from "../../api";

function UserListItem(props: {
  user: User;
  isOnline: boolean;
  hasChat: boolean;
  onClick: () => void;
}) {
  return (
    <div
      onClick={props.onClick}
      className="flex items-center gap-2 p-2 cursor-pointer rounded transition-colors hover:bg-black/5"
    >
      <span
        className={`w-2 h-2 rounded-full ${
          props.isOnline ? "bg-green-500" : "bg-gray-500"
        }`}
      />
      <span className="flex-1">{props.user.username}</span>
    </div>
  );
}

export default UserListItem;
