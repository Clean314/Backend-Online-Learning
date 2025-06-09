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
('Advanced Java', '프로그래밍', 'MEDIUM', 50, 49, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Network Fundamentals', '네트워크', 'EASY',   40, 39, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('AI Basics', 'AI', 'EASY', 30, 29, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SQL 고급 튜닝', '데이터베이스', 'HARD', 35, 35, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('웹 보안 기초', '보안', 'MEDIUM', 20, 19, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('웹 개발 입문', '프로그래밍', 'EASY', 100, 100, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('리액트 프로페셔널', '프로그래밍', 'MEDIUM', 80, 80, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('노드.js 서버 개발', '프로그래밍', 'MEDIUM', 75, 75, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('데이터베이스 튜닝', '데이터베이스', 'HARD', 60, 60, 4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( '머신러닝 기초', 'AI', 'EASY', 50, 50, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( '딥러닝 실전', 'AI', 'MEDIUM', 45, 45, 3, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('클라우드 아키텍처', '클라우드', 'MEDIUM', 70, 70, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('DevOps 도구', 'DevOps', 'MEDIUM', 65, 65, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
( '모바일 앱 개발', '모바일', 'MEDIUM', 80, 80, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('웹 보안 심화', '보안', 'HARD', 40, 40, 4, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Enrollments
INSERT INTO enrollment (status, student_id, course_id, created_at, updated_at, version) VALUES
('ENROLLED', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('COMPLETED', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('ENROLLED', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('COMPLETED', 2, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

INSERT INTO exam (id, course_id, title, description, start_time, end_time) VALUES
(1, 1, 'Java 중간고사', 'Java 기초 및 객체지향 개념 평가', '2025-06-15 10:00:00', '2025-06-15 12:00:00'),
(2, 1, 'Java 기말고사', '멀티스레딩, 스트림, 람다식 등 고급 주제 평가', '2025-07-10 13:00:00', '2025-07-10 15:00:00'),
(3, 2, '네트워크 1차 평가', 'OSI 7계층 및 기본 프로토콜 시험', '2025-06-18 09:00:00', '2025-06-18 11:00:00'),
(4, 3, 'AI 기초 테스트', '기계학습 개요 및 분류/회귀 개념 확인', '2025-06-20 14:00:00', '2025-06-20 15:30:00'),
(5, 4, 'SQL 튜닝 중간고사', '인덱스, 실행계획, 조인 최적화 등 실습형 시험', '2025-06-22 10:00:00', '2025-06-22 12:00:00'),
(6, 5, '웹 보안 기초 시험', 'XSS, CSRF, SQL Injection 등 주요 보안 개념 테스트', '2025-06-25 15:00:00', '2025-06-25 16:30:00');

ALTER TABLE exam ALTER COLUMN id RESTART WITH 7;

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
('강의를 들어가기 전에2', 'https://www.youtube.com/watch?v=IAMdPn3YCG4', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('7주차', 'https://www.youtube.com/watch?v=dQw4w9WgXcQ', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('8주차', 'https://www.youtube.com/watch?v=J---aiyznGQ', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('네트워크 기초 1', 'https://www.youtube.com/watch?v=c62qssA4hYI', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('네트워크 기초 2', 'https://www.youtube.com/watch?v=3QhU9jd03a0', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('AI 예제 프로젝트', 'https://www.youtube.com/watch?v=aircAruvnKk', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SQL 최적화 실습', 'https://www.youtube.com/watch?v=9Pzj7Aj25lw', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('HTML & CSS 기초', 'https://www.youtube.com/watch?v=mU6anWqZJcc', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('JavaScript 문법 완벽 이해', 'https://www.youtube.com/watch?v=W6NZfCO5SIk', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('DOM 조작과 이벤트', 'https://www.youtube.com/watch?v=0ik6X4DJKCc', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('배포를 위한 Git/GitHub 활용','https://www.youtube.com/watch?v=HVsySz-h9r4', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('리액트 Hooks 완전 정복', 'https://www.youtube.com/watch?v=f687hBjwFcM',   7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('상태 관리 라이브러리 비교', 'https://www.youtube.com/watch?v=35lXWvCuM8o',  7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('React Router 실전', 'https://www.youtube.com/watch?v=Law7wfdg_ls', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('테스트와 성능 최적화', 'https://www.youtube.com/watch?v=Uh2ebFW8OYM', 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Node.js 기본 구조 이해', 'https://www.youtube.com/watch?v=TlB_eWDSMt4', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Express 라우팅과 미들웨어', 'https://www.youtube.com/watch?v=L72fhGm1tfE',  8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('JWT 인증 구현', 'https://www.youtube.com/watch?v=7Q17ubqLfaM',  8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Socket.io를 활용한 실시간 통신','https://www.youtube.com/watch?v=vQjiN8Qgs3c',8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('에러 핸들링과 로깅', 'https://www.youtube.com/watch?v=Lr9WUkeYSA8', 8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('인덱스 이해와 활용', 'https://www.youtube.com/watch?v=Ct8s-fwPIoQ', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('쿼리 최적화 기법', 'https://www.youtube.com/watch?v=7BLQ4p1NThg', 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('파이썬 데이터 전처리', 'https://www.youtube.com/watch?v=LHBE6Q9XlzI', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('분류 모델 실습', 'https://www.youtube.com/watch?v=Cr6VqTRO1v0', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('모델 평가 지표', 'https://www.youtube.com/watch?v=85dtiMz9tSo', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('TensorFlow 기본', 'https://www.youtube.com/watch?v=tPYj3fFJGjk', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CNN 개념 및 구현', 'https://www.youtube.com/watch?v=YRhxdVk_sIs', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('RNN/LSTM 실습', 'https://www.youtube.com/watch?v=UNmqTiOnRfg', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('전이 학습 활용', 'https://www.youtube.com/watch?v=MpqHpT84Lfo', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('AWS EC2와 S3', 'https://www.youtube.com/watch?v=Ia-UEYYR44s', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CloudFormation 인프라 코드', 'https://www.youtube.com/watch?v=ATHJqouigwA', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('CI/CD 파이프라인 구축', 'https://www.youtube.com/watch?v=1hHMwLxN6EM', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Docker 컨테이너 이해', 'https://www.youtube.com/watch?v=pTFZFxd4hOI', 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Docker Compose 실습', 'https://www.youtube.com/watch?v=Qw9zlE3t8Ko', 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Kubernetes 기초', 'https://www.youtube.com/watch?v=X48VuDVv0do', 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Helm Chart 제작', 'https://www.youtube.com/watch?v=VJCsBturfyU', 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('GitHub Actions 활용', 'https://www.youtube.com/watch?v=R8_veQiYBjI', 13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('모니터링: Prometheus & Grafana','https://www.youtube.com/watch?v=z2REuIM7RfQ',13, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('React Native 시작하기', 'https://www.youtube.com/watch?v=0-S5a0eXPoc', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Flutter 위젯 심화', 'https://www.youtube.com/watch?v=lkF0TQJO0bA', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('네이티브 모듈 연동', 'https://www.youtube.com/watch?v=u_MC5U1RK3Y', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('앱 배포 및 테스트', 'https://www.youtube.com/watch?v=GPOv72Awo68', 14, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('XSS & CSRF 방어 기법', 'https://www.youtube.com/watch?v=oY7fNbN8dZg', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SQL 인젝션 대응', 'https://www.youtube.com/watch?v=ciNHn38EyRc', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('OAuth2 인증 흐름', 'https://www.youtube.com/watch?v=996OiexHze0', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('HTTPS/TLS 이해', 'https://www.youtube.com/watch?v=4cXOPjgyWmk', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('보안 모니터링', 'https://www.youtube.com/watch?v=ObnNBhDhrUU', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);