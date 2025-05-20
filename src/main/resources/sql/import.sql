-- DB 에만 보안 때문에 비번 이렇게 저장하는 거고 백엔드에서는 암호화 상태를 간주하고 파싱하기 때문에 이걸 그대로 넣어서 테스트하면 안됨
-- 암호화 쉽게 만드는 사이트 : https://bcrypt-generator.com/
-- [비번 목록]
-- 1: test11, 2: test22, 3: test33, 4: test44, test55

-- member 테이블 데이터
INSERT INTO member (id, role, email, password, name, created_at, updated_at, member_type)
VALUES (1, 'STUDENT', 'student1@test.com', '{bcrypt}$2a$12$Sn6EMdBg4ntsibOkFz94Tu5mhlILoukQ7Vk3UuqXjUPz5NwaTVUhK', '학생1', NOW(), NOW(), 'STUDENT'),
       (2, 'STUDENT', 'student2@test.com', '{bcrypt}$2a$12$HL0j5rqT3Vad9n.bwwXP1OhoKjNmoAY1t1H2SD2DYYs7PXEpk/fmm', '학생2', NOW(), NOW(), 'STUDENT'),
       (3, 'EDUCATOR', 'educator1@test.com', '{bcrypt}$2a$12$e9xH42muHuCdRUbGl.hvLeNg3bXbjJVZpZfAUvqFXxJdV6FpgmPoi', '교수1', NOW(), NOW(), 'EDUCATOR'),
       (4, 'EDUCATOR', 'educator2@test.com', '{bcrypt}$2a$12$8D1CAAdu5/0xSd2eycUvEuV.OYK6VsDTB0YqIk/i9iAeWKDd4lyHC', '교수2', NOW(), NOW(), 'EDUCATOR'),
       (5, 'ADMIN', 'admin1@test.com', '{bcrypt}$2a$12$3bOumwY90.hO.lcI276VEupxULSyLcXGe49BH7gkr6upyNBBcsFyS', '관리자1', NOW(), NOW(), 'ADMIN');

-- student 데이터
INSERT INTO student (id, student_number)
VALUES (1, 'S250520'),
       (2, 'S250519');

-- educator 데이터
INSERT INTO educator (id, educator_number)
VALUES (3, 'E250520'),
       (4, 'E250519');

-- admin 데이터
INSERT INTO admin (id)
VALUES (5);

/*-- enrollment
INSERT INTO enrollment(id, status, created_at, member_id) VALUES (1, 'ENROLLED', now(), 1);
INSERT INTO enrollment(id, status, created_at, member_id) VALUES (2, 'COMPLETED', now(), 2);
INSERT INTO enrollment(id, status, created_at, member_id) VALUES (3, 'ENROLLED', now(), 3);

-- course
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (1, 'SpringBoot', now(), 1);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (2, 'SpringBoot', now(), 3);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (3, 'startDB', now(), 1);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (4, 'Cloud', now(), 1);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (5, 'computer', now(), 3);
INSERT INTO course(id, name, created_at, enrollment_id) VALUES (6, 'music', now(), 1);*/
