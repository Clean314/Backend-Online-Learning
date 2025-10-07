교육자와 학습자를 위한 온라인 평생학습 웹 백엔드 서버

교육자와 학습자를 위한 확장 가능하고 안전한 온라인 평생학습(온라인 교육) 플랫폼의 백엔드 서버입니다. 이 프로젝트는 사용 편의성과 유지보수성을 고려해 설계되었으며, 역할 기반 접근 제어와 콘텐츠·평가·커뮤니케이션 기능을 제공합니다.

# 프로젝트 개요

**Backend-Online-Learning**은 강의 관리, 수강 관리, 시험·과제, 게시판, 설문 등 온라인 교육 운영에 필요한 핵심 기능을 제공하는 서버 애플리케이션입니다. 관리자, 강사, 학생 등 다양한 사용자 역할에 따라 권한이 분리되어 있으며 JWT 기반의 인증으로 보안을 강화했습니다.

## 주요 기능

### 👥 사용자 관리
* 회원가입 / 로그인 (JWT 기반 인증)
* 권한 관리(관리자 / 강사 / 학생)
* 비밀번호 암호화

### 📚 강의 관리
* 강의 생성 / 수정 / 삭제
* 강의 목록 조회 및 상세 정보 제공

### 🎓 수강 신청 및 관리
* 강의 수강 신청 / 취소
* 수강생 목록 및 상태 관리

### 🧾 시험 및 과제 관리
* 시험/과제 생성, 배포, 제출 관리
* 자동 채점(일부 유형) 및 성적 조회

### 💬 게시판 및 Q&A
* 게시글 CRUD
* 댓글, 답변 기능

### 📊 설문조사
* 설문 템플릿 생성 및 응답 수집

### 🔐 보안
* JWT 기반 인증/인가
* 비밀번호 해시(권장: BCrypt)

## 기술 스택
* 언어: Java 21
* 프레임워크: Spring Boot
* DB: (예: MySQL / PostgreSQL) — 설정 파일에서 선택
* 인증: JWT
* 기타: Mapper(DAO), 자동화 테스트, 로깅

실제 사용 중인 라이브러리/버전은 build.gradle / pom.xml에서 확인할 수 있습니다.

## 빠른 시작
아래는 로컬에서 프로젝트를 실행하는 기본 안내입니다.
### 요구사항
* Java 21 이상
* 로컬/원격 DB (MySQL 또는 PostgreSQL)

### 저장소 클론
<code>$ git clone https://github.com/Clean314/Backend-Online-Learning.git
$ cd Backend-Online-Learning
$ ./gradlew bootRun   # (또는) mvn spring-boot:run</code>

### 환경 변수 (예시)
.env 또는 application.yml에 아래 항목을 설정하세요. <br>
<code>SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/yourdb
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=yourpassword
JWT_SECRET=your_jwt_secret</code>


## 🤝 기여자
* [Backend Ok-ChanMi](https://github.com/Ok-ChanMi)
* [Frontend yun0612](https://github.com/yun0612)
