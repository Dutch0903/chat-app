CREATE TABLE chat_participants(
    id UUID NOT NULL PRIMARY KEY,
    chat_id UUID NOT NULL,
    participant_id UUID NOT NULL,
    role VARCHAR(20) NOT NULL,

    FOREIGN KEY (chat_id) REFERENCES chats (id) ON DELETE CASCADE
);