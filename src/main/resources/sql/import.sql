-- DB 에만 보안 때문에 비번 이렇게 저장하는 거고 백엔드에서는 암호화 상태를 간주하고 파싱하기 때문에 이걸 그대로 넣어서 테스트하면 안됨
-- 암호화 쉽게 만드는 사이트 : https://bcrypt-generator.com/
-- [비번 목록]
-- 1: test11, 2: test22, 3: test33, 4: test44, test55, test66, test77,test88, test99, test1010, test1111, test1212, , ,



-- Insert Members (Discriminator: STUDENT and EDUCATOR)
INSERT INTO member (id, role, email, password, name, created_at, updated_at, member_type) VALUES
(1, 'STUDENT', 'student1@test.com', '{bcrypt}$2a$12$YE5.5u8Dqcz.aBlUc.2u9.BS/fy8syAZ.HsZhsIc0GY8rdWZH8Yh2', '학생1', now(), now(), 'STUDENT'),
(2, 'STUDENT', 'student2@test.com', '{bcrypt}$2a$12$mY/6mHlcHjC5VCDgh7IqCe6Y3WTjdHXvjetKq14fflcVF8Rqdixpu', '학생2',  now(), now(), 'STUDENT'),
(3, 'EDUCATOR', 'educator1@test.com', '{bcrypt}$2a$12$qBibhD/ms7yKDT1XTtmg8uCPXB4677gPMRAFeOJeuM/zuh.rJfqsO', '교수1',  now(), now(), 'EDUCATOR'),
(4, 'EDUCATOR', 'educator2@test.com', '{bcrypt}$2a$12$cRfRapBcbyPGzHOtv0xS..Y7ULgqC.RDvJkst8COc.bNYNr.ZhsZy', '교수2',  now(), now(), 'EDUCATOR'),
(5, 'ADMIN', 'admin@test.com', '{bcrypt}$2a$12$GoIYCEHvgq4TIpth9fQ0rejpGcdKYKH3iBL73pRKlIwTTnxTs24IO', '교수2',  now(), now(), 'ADMIN');

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
('Data Structures', '데이터베이스', 'EASY', 40, 40, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Advanced Java', '프로그래밍', 'MEDIUM', 50, 50, 5, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Database Optimization', '데이터베이스','MEDIUM', 25, 25, 6, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Network Security', '보안', 'HARD', 20, 20, 7, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Enrollments
INSERT INTO enrollment
(id, status, student_id, course_id, created_at, updated_at, version)
VALUES
(1, 'ENROLLED', 1, 1, now(), now(), 0),
(1, 'COMPLETED', 1, 2, now(), now(), 0),
(2, 'ENROLLED', 2, 2, now(), now(), 0),
(2, 'COMPLETED', 2, 3, now(), now(), 0);
