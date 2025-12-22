CREATE TABLE chat_participants(
    chat_id UUID NOT NULL,
    participant_id UUID NOT NULL,
    role VARCHAR(20) NOT NULL,

    PRIMARY KEY (chat_id, participant_id)
);