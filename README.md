# backend
member가 security의 기본 user 구현체이고 admin, student, educator가 이를 상속함<br>
student는 enrollment(수강) 기능을 사용할 수 있음<br>
educator는 course(강의) 기능을 사용할 수 있음<br>
student와 educator는 서로 구분되어 보안 제한이 있음<br>
admin은 권한 계층의 최상위로 설정해서 student, educator의 기능을 가지도록 했음

URI, 요청 형식, 예외처리 리팩토링<br>
문제 유형 추가<br>
채점 처리 추가<br>
DTO, Entity 일관성 수정<br>
