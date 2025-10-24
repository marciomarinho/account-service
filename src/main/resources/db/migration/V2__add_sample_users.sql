-- Insert sample users
-- Note: These are hashed versions of 'Password123!' using bcrypt
-- In production, hash passwords properly using BCryptPasswordEncoder
INSERT INTO users (id, email, username, password_hash, first_name, last_name) VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'john.doe@example.com', 'johndoe', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8eW4/Z.LhJXqP1qZqLm7YhLPdJmOy', 'John', 'Doe'),
('b1ffcd99-9c0b-4ef8-bb6d-6bb9bd380a22', 'jane.smith@example.com', 'janesmith', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8eW4/Z.LhJXqP1qZqLm7YhLPdJmOy', 'Jane', 'Smith'),
('c2ggde99-9c0b-4ef8-bb6d-6bb9bd380a33', 'bob.wilson@example.com', 'bobwilson', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8eW4/Z.LhJXqP1qZqLm7YhLPdJmOy', 'Bob', 'Wilson'),
('d3hhef99-9c0b-4ef8-bb6d-6bb9bd380a44', 'alice.brown@example.com', 'alicebrown', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8eW4/Z.LhJXqP1qZqLm7YhLPdJmOy', 'Alice', 'Brown'),
('e4iifg99-9c0b-4ef8-bb6d-6bb9bd380a55', 'charlie.davis@example.com', 'charliedavis', '$2a$10$N9qo8uLOickgx2ZMRZoMye1J8eW4/Z.LhJXqP1qZqLm7YhLPdJmOy', 'Charlie', 'Davis');