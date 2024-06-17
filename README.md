### [과제] `point` 패키지의 TODO 와 테스트코드를 작성해주세요.

**요구 사항**  
- PATCH  `/point/{id}/charge` : 포인트를 충전한다.
- PATCH `/point/{id}/use` : 포인트를 사용한다.
- GET `/point/{id}` : 포인트를 조회한다.
- GET `/point/{id}/histories` : 포인트 내역을 조회한다.
- 잔고가 부족할 경우, 포인트 사용은 실패하여야 합니다.
- 동시에 여러 건의 포인트 충전, 이용 요청이 들어올 경우 순차적으로 처리되어야 합니다. (동시성)


### 동시성 처리
1. synchronized 사용   
여러 요청을 받지만 synchronized가 명시되어있는 메소드 or 코드 블럭 들은 Single Thread를 할당하기 때문에 여러 요청들을 순차적으로 처리가 가능하다   
장점: 간단한 사용법   
단점: Single Thread이기때문에 요청이 많이 몰렸을 경우 처리 속도가 저하될 수 있다.
