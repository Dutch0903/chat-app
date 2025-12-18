CREATE TABLE chat_messages (
    id UUID PRIMARY KEY,
    chat_id UUID,
    sender_id UUID,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (chat_id) REFERENCES chats(id)
);