<<<<<<< Updated upstream
=======
-- 테스트 할 때 테이블 생성 삭제 시 쿼리 문 같지만 시퀀스 생성 쿼리가 다른 듯 함
-- db에 해당 테이블있으면 확인 후 삭제
DROP TABLE member CASCADE;
DROP TABLE enrollment CASCADE;
DROP TABLE course CASCADE;
DROP TABLE lecture CASCADE;

CREATE TABLE member(
    id number primary key,
    email varchar2(100) not null,
    password varchar2(100) not null,
    name varchar2(30) not null,
    role varchar2(20),
    created_at date,
    updated_at date,
    CONSTRAINT role_ck CHECK (role IN('STUDENT', 'INSTRUCTOR'))
);
CREATE TABLE enrollment(
    id number primary key,
    created_at date,
    updated_at date,
    status varchar2(20),
    member_id number,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES member(id),
    CONSTRAINT enrollment_ck CHECK(status IN('ENROLLED', 'COMPLETED', 'WITHDRAWN', 'RETAKE'))
);

CREATE TABLE course(
    id number primary key,
    name varchar2(100),
    created_at date,
    updated_at date,
    enrollment_id number,
    CONSTRAINT fk_enrollment FOREIGN key (enrollment_id) REFERENCES enrollment(id)
);

CREATE TABLE lecture(
    id number primary key,
    title varchar2(100),
    video_url varchar2(1000) not null,
    created_at date,
    updated_at date,
    course_id number,
    CONSTRAINT fk_course FOREIGN key (course_id) REFERENCES course(id)
);

-- h2에서 테스트 시 주석해제
CREATE SEQUENCE IF NOT EXISTS member_seq START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE CACHE 1;
CREATE SEQUENCE IF NOT EXISTS enrollment_seq START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE CACHE 1;
CREATE SEQUENCE IF NOT EXISTS lecture_seq START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE CACHE 1;
CREATE SEQUENCE IF NOT EXISTS course_seq START WITH 1 INCREMENT BY 1 MINVALUE 1 NO MAXVALUE CACHE 1;

-- oracle 사용시 주석해제
--CREATE SEQUENCE member_seq START WITH 1 INCREMENT BY 1;
--CREATE SEQUENCE enrollment_seq START WITH 1 INCREMENT BY 1;
--CREATE SEQUENCE lecture_seq START WITH 1 INCREMENT BY 1;
--CREATE SEQUENCE course_seq START WITH 1 INCREMENT BY 1;
>>>>>>> Stashed changes
