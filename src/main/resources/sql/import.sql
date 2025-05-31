-- DB 에만 보안 때문에 비번 이렇게 저장하는 거고 백엔드에서는 암호화 상태를 간주하고 파싱하기 때문에 이걸 그대로 넣어서 테스트하면 안됨
-- 암호화 쉽게 만드는 사이트 : https://bcrypt-generator.com/
-- [비번 목록]
-- 1: test11, 2: test22, 3: test33, 4: test44, test55, test66, test77,test88, test99, test1010, test1111, test1212, , ,


-- Insert Members
INSERT INTO member (id, role, email, password, name, created_at, updated_at, member_type) VALUES
(1, 'STUDENT', 'student1@test.com', '{bcrypt}$2a$12$TS6jp5rY/cJTlx6IrqBKf.O167DzF7XCbOjL2PezF.nmbDfCKnQbO', '학생1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'STUDENT'),
(2, 'STUDENT', 'student2@test.com', '{bcrypt}$2a$12$RzUhS6XS8VDkjrLWldZas.m1/3gg1TciPMeTTDPrCSczdRiDmFxnK', '학생2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'STUDENT'),
(3, 'EDUCATOR', 'educator1@test.com', '{bcrypt}$2a$12$fQBvxmSppjAh2CUsrSKHsuZ1K7jfexESNcYFaY.gvziktaYyzWtGu', '교수1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EDUCATOR'),
(4, 'EDUCATOR', 'educator2@test.com', '{bcrypt}$2a$12$/w4v.BmQrHt1qCLuQbY8DuyIO/Ug/zIqUqbaAHR/pvJtst4XeDkwm', '교수2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EDUCATOR'),
(5, 'ADMIN', 'admin1@test.com', '{bcrypt}$2a$12$ENDBvTiJb4KAj.IhFp6XAeLuL4n7fOkcKhIgOaIfcoDumgqIpTcC6', '관리자1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ADMIN'),
(6, 'STUDENT', 'student3@test.com', '{bcrypt})$2a$12$ZamGwkqByDXK0R4uVdqAGeNlCKoCCcAOFbi92wQnXMKwIr5On/ZOm', '학생1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'STUDENT');


-- 시퀀스 값 수동 지정
-- members 에 수동으로 id 값을 넣었기 때문
ALTER TABLE member ALTER COLUMN id RESTART WITH 7;

-- Insert Students
INSERT INTO student (id, student_number) VALUES
(1, 'S2025001'),
(2, 'S2025002'),
(6, 'S2025003');

-- Insert Educators
INSERT INTO educator (id, educator_number) VALUES
(3, 'E2025001'),
(4, 'E2025002');

-- Insert admin
INSERT INTO admin (id, admin_number) VALUES
(5, 'A2025001');

-- Insert Courses
INSERT INTO course (course_name, category, difficulty, max_enrollment, available_enrollment, educator_id, point, created_at, updated_at) VALUES
('Advanced Java', '프로그래밍', 'MEDIUM', 50, 50, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Network Fundamentals', '네트워크', 'EASY',   40, 40, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('AI Basics', 'AI', 'EASY', 30, 30, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SQL 고급 튜닝', '데이터베이스', 'HARD', 35, 35, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('웹 보안 기초', '보안', 'MEDIUM', 20, 20, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Enrollments
INSERT INTO enrollment (status, student_id, course_id, created_at, updated_at, version) VALUES
('ENROLLED', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('COMPLETED', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('ENROLLED', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('COMPLETED', 2, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

--Insert Lecture
INSERT INTO lecture (title, video_url, course_id, created_at, updated_at) VALUES
('1주차', 'https://www.youtube.com/watch?v=6KguK-Tm2uE&list=PLrNWr7uHHlkX8pAcaKmK6tHjqx87qtUfg&index=1', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('2주차', 'https://www.youtube.com/watch?v=m9mH7cAQoy8&list=PLrNWr7uHHlkX8pAcaKmK6tHjqx87qtUfg&index=2', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('3주차', 'https://www.youtube.com/watch?v=QnpDsUouHUk&list=PLrNWr7uHHlkX8pAcaKmK6tHjqx87qtUfg&index=3', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('4주차', 'https://www.youtube.com/watch?v=LdprWjUpzPU&list=PLrNWr7uHHlkX8pAcaKmK6tHjqx87qtUfg&index=4', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('5주차', 'https://www.youtube.com/watch?v=svboyFGioKM&list=PLrNWr7uHHlkX8pAcaKmK6tHjqx87qtUfg&index=5', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('6주차', 'https://www.youtube.com/watch?v=UttpjKulRHY&list=PLrNWr7uHHlkX8pAcaKmK6tHjqx87qtUfg&index=6', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('첫번째', 'https://www.youtube.com/watch?v=jdTsJzXmgU0&list=PLuHgQVnccGMCeAy-2-llhw3nWoQKUvQck&index=1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('두번째', 'https://www.youtube.com/watch?v=qR90tdW0Hbo&list=PLuHgQVnccGMCeAy-2-llhw3nWoQKUvQck&index=2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('세번째', 'https://www.youtube.com/watch?v=kyFrm3zKryE&list=PLuHgQVnccGMCeAy-2-llhw3nWoQKUvQck&index=3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('1-1', 'https://www.youtube.com/watch?v=PE6dA5ZnI9o', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('강의를 들어가기 전에1', 'https://www.youtube.com/watch?v=z9chRlD1tec', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('강의를 들어가기 전에2', 'https://www.youtube.com/watch?v=IAMdPn3YCG4', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
