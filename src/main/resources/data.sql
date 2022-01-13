INSERT INTO test.user (login, name, password) VALUES ('admin', 'admin', 'password');
SET @user_id = LAST_INSERT_ID();

INSERT IGNORE INTO test.role (name) VALUES ('ROLE_ADMIN');
SET @role_id = LAST_INSERT_ID();
INSERT INTO test.user_role (user_id, roles_id) VALUES (@user_id, @role_id);

INSERT IGNORE INTO test.role (name) VALUES ('ROLE_USER');
SET @role_id = LAST_INSERT_ID();
INSERT INTO test.user_role (user_id, roles_id) VALUES (@user_id, @role_id);