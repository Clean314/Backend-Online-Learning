-- member
-- DB 에만 보안 때문에 비번 이렇게 저장하는 거고 백엔드에서는 암호화 상태를 간주하고 파싱하기 때문에 이걸 그대로 넣어서 테스트하면 안됨
-- 암호화 쉽게 만드는 사이트 : https://bcrypt-generator.com/
-- [비번 목록]
-- 1: test111, 2: test222, 3: test333, 4: test444
INSERT INTO member (email, password, name, role, created_at) VALUES ('test@email.com', '{bcrypt}$2a$12$YKw2mg1Uu7zr/Diw3NwVUe6UozIO179NKNbB0TH012sa4IHjAfYVi', '홍길동', 'STUDENT', now());
INSERT INTO member (email, password, name, role, created_at) VALUES ('test11@email.com', '{bcrypt}$2a$12$ITtrxyKDNz36rsAMu1NnsebOJWsdMvLZsYmE0tqh0mbC5pNYoSGaK', '김철수', 'STUDENT', now());
INSERT INTO member (email, password, name, role, created_at) VALUES ('test22@email.com', '{bcrypt}$2a$12$kWIMlnfu.0vBbK4FQbBoHOnVVjZ9cz5qB3W021aW4yB17lyTJkoRu', '김영희', 'EDUCATOR', now());
INSERT INTO member (email, password, name, role, created_at) VALUES ('test33@email.com', '{bcrypt}$2a$12$YX5PLN9pswbMEoO/XnS4QeZctpYl3Sx9rUPGTF.JrW0WpjCjjWgzK', '김돌돌', 'STUDENT', now());

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

