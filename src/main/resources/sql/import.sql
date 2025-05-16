-- member
INSERT INTO member (id, email, password, name, role, created_at) VALUES (1,'test@email.com', 'acvcf@111!', '홍길동', 'STUDENT', now());
INSERT INTO member (id, email, password, name, role, created_at) VALUES (2,'test11@email.com', 'qwenvxc222', '김철수', 'STUDENT', now());
INSERT INTO member (id, email, password, name, role, created_at) VALUES (3,'test22@email.com', 'vbaefe333', '김영희', 'INSTRUCTOR', now());
INSERT INTO member (id, email, password, name, role, created_at) VALUES (4,'test33@email.com', 'tbnsrt444', '김돌돌', 'STUDENT', now());

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