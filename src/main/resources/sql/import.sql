-- DB 에만 보안 때문에 비번 이렇게 저장하는 거고 백엔드에서는 암호화 상태를 간주하고 파싱하기 때문에 이걸 그대로 넣어서 테스트하면 안됨
-- 암호화 쉽게 만드는 사이트 : https://bcrypt-generator.com/
-- [비번 목록]
-- 1: test11, 2: test22, 3: test33, 4: test44, test55, test66, test77,test88, test99, test1010, test1111, test1212, , ,


-- Insert Members
INSERT INTO member (id, role, email, password, name, created_at, updated_at, member_type) VALUES
(1, 'STUDENT', 'student1@test.com', '{bcrypt}$2a$12$TS6jp5rY/cJTlx6IrqBKf.O167DzF7XCbOjL2PezF.nmbDfCKnQbO', '학생1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'STUDENT'),
(2, 'STUDENT', 'student2@test.com', '{bcrypt}$2a$12$RzUhS6XS8VDkjrLWldZas.m1/3gg1TciPMeTTDPrCSczdRiDmFxnK', '학생2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'STUDENT'),
(3, 'EDUCATOR', 'educator1@test.com', '{bcrypt}$2a$12$fQBvxmSppjAh2CUsrSKHsuZ1K7jfexESNcYFaY.gvziktaYyzWtGu', '교수1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EDUCATOR'),
(4, 'EDUCATOR', 'educator2@test.com', '{bcrypt}$2a$12$/w4v.BmQrHt1qCLuQbY8DuyIO/Ug/zIqUqbaAHR/pvJtst4XeDkwm', '교수2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'EDUCATOR');

-- Insert Students
INSERT INTO student (id, student_number) VALUES
(1, 'S2025001'),
(2, 'S2025002');

-- Insert Educators
INSERT INTO educator (id, educator_number) VALUES
(3, 'E2025001'),
(4, 'E2025002');

-- Insert Courses
INSERT INTO course (course_name, category, difficulty, max_enrollment, available_enrollment, educator_id, point, created_at, updated_at) VALUES
('Spring Framework', '프로그래밍', 'EASY', 30, 30, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Data Structures', '데이터베이스', 'EASY', 40, 40, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Enrollments
INSERT INTO enrollment (status, student_id, course_id, created_at, updated_at, version) VALUES
('ENROLLED', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('COMPLETED', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);
