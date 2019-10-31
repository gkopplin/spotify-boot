INSERT INTO user_role (role_name) VALUES ('ROLE_ADMIN');
INSERT INTO user_role (role_name) VALUES ('ROLE_USER');

INSERT INTO users (username, password, user_role_id) VALUES ('foo', 'bar', 1);
INSERT INTO users (username, password, user_role_id) VALUES ('foo2', 'bar', 2);

INSERT INTO song (title, length) VALUES ('Gangnam Style', 420);
INSERT INTO song (title, length) VALUES ('Mother Father Gentlemen', 690);

INSERT INTO user_song (user_id, song_id) VALUES (1,1);
INSERT INTO user_song (user_id, song_id) VALUES (1,2);
