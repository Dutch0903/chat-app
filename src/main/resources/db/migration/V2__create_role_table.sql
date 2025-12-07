CREATE TABLE roles (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE user_roles (
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    role_id UUID REFERENCES roles(id) ON DELETE CASCADE
);

INSERT INTO
    roles (id, name)
VALUES
    (gen_random_uuid(), 'USER'),
    (gen_random_uuid(), 'ADMIN');

