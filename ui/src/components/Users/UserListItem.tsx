import type { User } from "../../api";

function UserListItem(props: { user: User; isOnline: boolean }) {
  return (
    <div style={{ display: "flex", alignItems: "center", gap: "8px" }}>
      <span
        style={{
          width: "8px",
          height: "8px",
          borderRadius: "50%",
          backgroundColor: props.isOnline ? "#22c55e" : "#6b7280",
        }}
      />
      {props.user.username}
    </div>
  );
}

export default UserListItem;
