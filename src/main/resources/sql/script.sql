INSERT INTO member (role, email, name, password, createdAt, updatedAt) VALUES
('USER', 'john.doe@example.com', 'John Doe', 'hashed_password_1', NOW(), NOW()),
('ADMIN', 'admin@example.com', 'Admin User', 'hashed_password_2', NOW(), NOW()),
('USER', 'jane.smith@example.com', 'Jane Smith', 'hashed_password_3', NOW(), NOW());
