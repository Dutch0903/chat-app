CREATE TABLE chat_participants(
    id UUID PRIMARY KEY,
    chat_id UUID NOT NULL,
    participant_id UUID NOT NULL,
    role VARCHAR(20) NOT NULL
);