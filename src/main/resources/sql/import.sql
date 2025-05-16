-- member
INSERT INTO member (email, password, name, role, created_at) VALUES ('test@email.com', '{bcrypt}$2a$12$YKw2mg1Uu7zr/Diw3NwVUe6UozIO179NKNbB0TH012sa4IHjAfYVi', '홍길동', 'STUDENT', now());
INSERT INTO member (email, password, name, role, created_at) VALUES ('test11@email.com', '{bcrypt}$2a$12$8SBxYG988lsn6SBS5NlD2e48AUB8YLIm12C/y4IBUzqbMjk89ivUS', '김철수', 'STUDENT', now());
INSERT INTO member (email, password, name, role, created_at) VALUES ('test22@email.com', '{bcrypt}$2a$12$M48gpxGahQ1dXIW0bicv4eallKaCkSKGjTQHzvdbQtv7iLIVSSSEa', '김영희', 'EDUCATOR', now());
INSERT INTO member (email, password, name, role, created_at) VALUES ('test33@email.com', '{bcrypt}$2a$12$yDElrWj8qwkfrBojYA1r.e2OWUBCtktcZekvhXLlCM2xnwjHdTMDe', '김돌돌', 'STUDENT', now());

-- enrollment
INSERT INTO enrollment(id, status, created_at, member_id) VALUES (1, 'ENROLLED', now(), 1);
INSERT INTO enrollment(id, status, created_at, member_id) VALUES (2, 'COMPLETED', now(), 2);
INSERT INTO enrollment(id, status, created_at, member_id) VALUES (3, 'ENROLLED', now(), 3);

-- course
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (1, 'SpringBoot', now(), 1);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (2, 'SpringBoot', now(), 3);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (3, 'startDB', now(), 1);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (4, 'Cloud', now(), 1);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (5, 'computer', now(), 3);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (6, 'music', now(), 1);