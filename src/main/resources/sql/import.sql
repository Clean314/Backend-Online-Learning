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
INSERT INTO course (course_name, description, category, difficulty, max_enrollment, available_enrollment, educator_id, point, created_at, updated_at) VALUES
('Advanced Java', '자바 프로그래밍의 고급 주제를 다루는 강의입니다.', '프로그래밍', 'MEDIUM', 50, 47, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Network Fundamentals', '네트워크의 기본 개념과 프로토콜을 학습합니다.', '네트워크', 'EASY', 40, 39, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('AI Basics', '인공지능의 기초 개념과 머신러닝을 소개합니다.', 'AI', 'EASY', 30, 29, 3, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('SQL 고급 튜닝', '데이터베이스 성능 최적화를 위한 SQL 튜닝 기법을 학습합니다.', '데이터베이스', 'HARD', 35, 35, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('웹 보안 기초', '웹 애플리케이션의 기본적인 보안 개념과 방어 기법을 다룹니다.', '보안', 'MEDIUM', 20, 19, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('웹 개발 입문', 'HTML, CSS, JavaScript를 활용한 웹 개발의 기초를 배웁니다.', '프로그래밍', 'EASY', 100, 100, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('리액트 프로페셔널', 'React의 고급 기능과 상태 관리, 성능 최적화를 학습합니다.', '프로그래밍', 'MEDIUM', 80, 80, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('노드.js 서버 개발', 'Node.js를 활용한 서버 개발과 API 설계를 배웁니다.', '프로그래밍', 'MEDIUM', 75, 75, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('데이터베이스 튜닝', '대규모 데이터베이스의 성능 최적화와 관리 기법을 학습합니다.', '데이터베이스', 'HARD', 60, 60, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('머신러닝 기초', '머신러닝의 기본 알고리즘과 구현 방법을 배웁니다.', 'AI', 'EASY', 50, 50, 3, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('딥러닝 실전', '딥러닝 모델 설계와 학습, 최적화 기법을 실습합니다.', 'AI', 'MEDIUM', 45, 45, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('클라우드 아키텍처', 'AWS, Azure, GCP 등 클라우드 서비스의 아키텍처 설계를 학습합니다.', '클라우드', 'MEDIUM', 70, 70, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('DevOps 도구', 'CI/CD, 컨테이너화, 모니터링 등 DevOps 도구의 활용을 배웁니다.', 'DevOps', 'MEDIUM', 65, 65, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('모바일 앱 개발', 'iOS와 Android 앱 개발의 기초와 실전 프로젝트를 진행합니다.', '모바일', 'MEDIUM', 80, 80, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('웹 보안 심화', '고급 웹 보안 기법과 취약점 분석, 대응 방안을 학습합니다.', '보안', 'HARD', 40, 40, 4, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Enrollments
INSERT INTO enrollment (status, student_id, course_id, created_at, updated_at, version) VALUES
('ENROLLED', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('COMPLETED', 1, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('ENROLLED', 2, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('COMPLETED', 2, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('ENROLLED', 2, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0),
('ENROLLED', 6, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0);

INSERT INTO exam (id, course_id, title, description, start_time, end_time, status) VALUES
(1, 1, 'Java 중간고사', 'Java 기초 및 객체지향 개념 평가', '2025-06-19 17:00:00', '2025-06-19 23:00:00', 'PREPARING'),
(2, 1, '쪽지시험', '수업 이해도 확인을 위한 중간 평가', '2025-06-20 9:00:00', '2025-06-20 15:00:00', 'PREPARING'),
(3, 1, 'Java 기말고사', '멀티스레딩, 스트림, 람다식 등 고급 주제 평가', '2025-07-10 13:00:00', '2025-07-10 15:00:00', 'PREPARING'),
(4, 2, '네트워크 1차 평가', 'OSI 7계층 및 기본 프로토콜 시험', '2025-06-18 09:00:00', '2025-06-18 11:00:00', 'PREPARING'),
(5, 3, 'AI 기초 테스트', '기계학습 개요 및 분류/회귀 개념 확인', '2025-06-20 14:00:00', '2025-06-20 15:30:00', 'PREPARING'),
(6, 4, 'SQL 튜닝 중간고사', '인덱스, 실행계획, 조인 최적화 등 실습형 시험', '2025-06-22 10:00:00', '2025-06-22 12:00:00', 'PREPARING'),
(7, 5, '웹 보안 기초 시험', 'XSS, CSRF, SQL Injection 등 주요 보안 개념 테스트', '2025-06-25 15:00:00', '2025-06-25 16:30:00', 'PREPARING');

ALTER TABLE exam ALTER COLUMN id RESTART WITH 7;

-- 객관식 문제
INSERT INTO question (exam_id, number, content, answer, score, question_type) VALUES
(1, 1, 'Java의 기본 데이터 타입이 아닌 것은?', 'String', 5, 'CHOICE'),
(1, 2, '다음 중 Java에서 객체지향 프로그래밍의 4대 원칙이 아닌 것은?', '병렬성', 5, 'CHOICE'),
(2, 1, 'Java에서 멀티스레딩을 구현하기 위한 방법이 아닌 것은?', '인터페이스 상속', 5, 'CHOICE'),
(2, 2, 'Java Stream API의 특징이 아닌 것은?', '동기적 처리', 3, 'CHOICE'),
(3, 1, 'OSI 7계층에서 전송 계층에 해당하는 프로토콜은?', 'TCP', 2, 'CHOICE'),
(3, 2, '다음 중 네트워크 주소 변환(NAT)의 목적이 아닌 것은?', '데이터 암호화', 3, 'CHOICE'),
(4, 1, '기계학습에서 분류 문제의 예시가 아닌 것은?', '주식 가격 예측', 2, 'CHOICE'),
(4, 2, '다음 중 회귀 분석의 목적이 아닌 것은?', '클러스터링', 3, 'CHOICE'),
(5, 1, 'SQL에서 인덱스의 주된 목적은 무엇인가요?', '검색 성능 향상', 4, 'CHOICE'),
(5, 2, '다음 중 SQL 조인 방식이 아닌 것은?', '병렬 조인', 5, 'CHOICE'),
(6, 1, 'XSS 공격을 방어하기 위한 방법은 무엇인가요?', '입력값 검증', 5, 'CHOICE'),
(6, 2, 'CSRF 공격을 방어하기 위한 방법은 무엇인가요?', '토큰 기반 인증', 5, 'CHOICE');

-- 진위형 문제
INSERT INTO question (exam_id, number, content, answer, score, question_type) VALUES
(1, 3, 'Java는 플랫폼에 독립적인 언어이다.', '0', 2, 'TRUE_FALSE'),
(2, 3, '멀티스레딩은 Java에서 지원되지 않는다.', '1', 2, 'TRUE_FALSE'),
(3, 3, 'IP는 전송 계층 프로토콜이다.', '1', 2, 'TRUE_FALSE'),
(4, 3, '클러스터링은 지도 학습 기법이다.', '1', 2, 'TRUE_FALSE'),
(5, 3, 'SQL에서는 SELECT 문으로 데이터를 삭제할 수 있다.', '1', 2, 'TRUE_FALSE'),
(6, 3, 'XSS는 서버 측에서 발생하는 공격이다.', '1', 2, 'TRUE_FALSE');

INSERT INTO question_choices (question_id, choice) VALUES
(1, 'int'),
(1, 'double'),
(1, 'boolean'),
(1, 'String');

INSERT INTO question_choices (question_id, choice) VALUES
(2, '상속'),
(2, '캡슐화'),
(2, '다형성'),
(2, '병렬성');

INSERT INTO question_choices (question_id, choice) VALUES
(3, 'Thread 클래스 상속'),
(3, 'Runnable 구현'),
(3, 'Executors 사용'),
(3, '인터페이스 상속');

INSERT INTO question_choices (question_id, choice) VALUES
(4, '지연된 계산'),
(4, '함수형 스타일'),
(4, '동기적 처리'),
(4, '컬렉션 변환');

INSERT INTO question_choices (question_id, choice) VALUES
(5, 'TCP'),
(5, 'UDP'),
(5, 'IP'),
(5, 'HTTP');

INSERT INTO question_choices (question_id, choice) VALUES
(6, '사설 IP 사용'),
(6, 'IP 주소 절약'),
(6, '보안 강화'),
(6, '데이터 암호화');

INSERT INTO question_choices (question_id, choice) VALUES
(7, '이메일 스팸 분류'),
(7, '고객 이탈 예측'),
(7, '종양 악성 여부 판별'),
(7, '주식 가격 예측');

INSERT INTO question_choices (question_id, choice) VALUES
(8, '숫자 예측'),
(8, '연속형 변수 모델링'),
(8, '미래 값 추정'),
(8, '클러스터링');

INSERT INTO question_choices (question_id, choice) VALUES
(9, '데이터 정렬'),
(9, '검색 성능 향상'),
(9, '데이터 압축'),
(9, '트랜잭션 보장');

INSERT INTO question_choices (question_id, choice) VALUES
(10, 'INNER JOIN'),
(10, 'LEFT JOIN'),
(10, 'RIGHT JOIN'),
(10, '병렬 조인');

INSERT INTO question_choices (question_id, choice) VALUES
(11, '입력값 검증'),
(11, '출력 인코딩'),
(11, '쿠키 설정'),
(11, 'SQL 인젝션 방어');

INSERT INTO question_choices (question_id, choice) VALUES
(12, '토큰 기반 인증'),
(12, 'IP 필터링'),
(12, 'Referer 검증'),
(12, 'GET 요청 허용');

INSERT INTO question_choices (question_id, choice) VALUES
(13, '0'),
(13, '1');

INSERT INTO question_choices (question_id, choice) VALUES
(14, '0'),
(14, '1');

INSERT INTO question_choices (question_id, choice) VALUES
(15, '0'),
(15, '1');

INSERT INTO question_choices (question_id, choice) VALUES
(16, '0'),
(16, '1');

INSERT INTO question_choices (question_id, choice) VALUES
(17, '0'),
(17, '1');

INSERT INTO question_choices (question_id, choice) VALUES
(18, '0'),
(18, '1');

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

-- LectureHistory
INSERT INTO lecture_history (
    id, student_id, lecture_id, attendance, watched_time, created_at, updated_at) VALUES
    (1, 1, 1, FALSE, 64.8,  CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (2, 1, 2, TRUE, 152.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (3, 1, 3, TRUE, 40.7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (4, 1, 4, FALSE, 387.5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (5, 1, 5, TRUE, 298.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (6, 1, 6, TRUE, 564.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (7, 1, 7, TRUE, 79.1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (8, 1, 8, FALSE, 142.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (9, 1, 9, FALSE, 217.6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (10, 1, 10, TRUE, 593.5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (11, 1, 11, TRUE, 113.2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (12, 1, 12, TRUE, 573.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (13, 2, 1, TRUE, 609.9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (14, 2, 2, TRUE, 99.2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (15, 2, 3, FALSE, 288.1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (16, 2, 4, FALSE, 663.9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ( 17, 2, 5, TRUE, 556.9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (18, 2, 6, TRUE, 459.5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (19, 2, 7, FALSE, 166.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (20, 2, 8, FALSE, 527.5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (21, 2, 9, TRUE, 496.7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (22, 2, 10, FALSE, 336.4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (23, 2, 11, TRUE, 187.9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (24, 2, 12, TRUE, 83.1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (25, 6, 1, FALSE, 517.7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (26, 6, 2, TRUE, 129.6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (27, 6, 3, FALSE, 77.8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (28, 6, 4, FALSE, 410.5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (29, 6, 5, FALSE, 125.8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (30, 6, 6, TRUE, 244.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (31, 6, 7, FALSE, 518.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (32, 6, 8, TRUE, 474.3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (33, 6, 9, TRUE, 240.4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (34, 6, 10, FALSE, 263.2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (35, 6, 11, TRUE, 242.8, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (36, 6, 12, TRUE, 228.1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);