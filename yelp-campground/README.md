# yelp-campground
## 옐프(Yelp)의 캠핑 장소 버젼. 캠핑장 리뷰 포럼 서비스.
- 사이트 주소 : https://yelp-campground.onrender.com/
- 깃허브 : https://github.com/Griotold/Yelp-Camp
- 사용 툴
  - Node.js, express.js
  - MongoDB, mongoose
  - Bootstrap, ejs
  - Github
  - Render : 웹 호스팅 서버
  - VScode
- 사용 API
  - 이미지 저장 : Cloudinary https://cloudinary.com/
  - 지도 조작 : Mapbox https://www.mapbox.com/
- 사용 언어
  - HTML, CSS, Javascript
- 인원 : 1명.
- 기간 : 2023.01.18~2023.03.16
## 프로젝트 목적
- 웹 개발 언어, HTML, CSS, Javascript의 기초 공부를 위해 
- 추가적으로, Node.js, express, MongoDB, 웹 사이트 배포 공부
- udemy 강의 : The Web Developer 부트캠프 2023의 내용을 배우며 실습한 결과물
- https://www.udemy.com/course/the-web-developer-bootcamp-2021-korea/
- 다루는 내용
![image](https://user-images.githubusercontent.com/101307758/225553651-01f5d8fe-23f0-4cfb-9a73-adf5c537a731.png)
- HTML, CSS, Javascript 뿐만 아니라 NoSQL의 하나인 MongoDB, Restful API, Terminal, Security, Deployment, Mongo Cloud Atals, Cookies & Session, Image Upload, Geocoding & Map 등 웹 개발의 전반적인 내용을 학습했다.
## 프로젝트 수행과정
- HTML, CSS, Javascript 복습 : 2023.01.01~2023.01.17
  - 각각의 중요한 부분들을 복습하였다. 몰랐던 부분들도 꽤 있어서 의미 있는 시간이었다.
- 프로젝트 진행 2023.01.18~2023.03.16
## 중요 포인트
### 1. Node.js
- Node.js를 사용하여 웹 개발을 처음으로 해보았다. 한국에서는 백엔드 언어로 Java가 대세지만, 세계적으로는 Node.js 가 우세하다. 취업전에 한 번쯤 경험해보는 것도 나쁘지 않다고 생각했다. 물론, 내가 가장 좋아하는 언어는 Java이고 Spring 프레임워크을 활용하여 최종 프로젝트를 만들 계획이다.  하지만, [colt steele](https://www.udemy.com/user/coltsteele/) 이라는 좋은 개발자 밑에서 웹 개발을 배울 수 있었던 것은 좋은 경험이었다고 생각한다.

### 2. REST'ful Routing
![image](https://user-images.githubusercontent.com/101307758/225558266-0a8267ee-66b4-4387-8edf-7a0f1e96cee0.png)
- 꼭 이렇게 라우팅할 필요는 없지만, 많은 개발자들이 이런 식으로 라우팅 주소를 잡는다고 배웠다. 체계적이고 참고할만 팁이라고 생각한다.

### 3. Mongo DB
- 관계형 데이터베이스만 사용했었는데, NoSQL을 배울 수 있어서 좋았다. Node.js, express 프레임워크로 개발할 때 Mongo DB를 많이들 선택한다고 한다. 처음 웹 개발에 입문하는 사람들에게 SQL을 따로 학습하지 않아도 되니 좋은 선택지가 되는 것이다. 아무튼 좋은 경험이었다.
- [Mongo DB Atlas](https://www.mongodb.com/docs/atlas/)를 사용하여 로컬 환경이 아닌 클라우드 환경에 DB를 사용할 수 있도록 구현했다.
- Java Spring 프레임 워크의 표준 ORM은 JPA이다. MongoDB의 ORM(ODM)은 Mongoose인데, 둘이 비슷하다고 느껴져서 배우는 데 수월했다. 메소드 명도 비슷한게 많았고(findById, save 등등), update라는 메소드를 호출하는게 아니라 dirty checking(변경 감지)을 통하여 객체를 수정하는 등등이 그랬다.

### 4. 미들웨어
- node와 express에서는 미들웨어를 사용한다. 프로젝트가 끝난 시점인 지금도 아직 정확하게 개념을 파악하지 못했다. express 미들웨어도 따로 있고, mongoose 미들웨어도 따로 있고 해서 혼란스러웠다. 예외 처리할 때 필요하고 플래쉬 기능을 추가할 때도 사용했었고 아무튼 다양한 곳에서 미들웨어를 사용했지만... 좀 더 공부가 필요한 영역이다. 사실, node.js 전문가가 될 것은 아니라 언제 다시 볼지는 모르겠다. 

### 5. 쿠키와 세션 + 인증과 인가
- 이번 프로젝트를 하면서 쿠키와 세션을 처음으로 구경했다. 쿠키와 세션을 활용하여 인증과 인가 기능을 추가했다.
- 로그인 한 경우 logout만 보이게끔 하고, 로그아웃 상태에서는 login과 register가 보이도록 구현했다.
- 캠핑장과 댓글은 해당 사용자만 수정, 삭제가 가능하도록 했다.
- 현재 spring security를 공부하고 있는데 이 때 경험한 것이 적잖이 도움 되고 있다. 

### 6. 이미지 업로드
![image](https://user-images.githubusercontent.com/101307758/225565791-9c331fc9-0425-45f7-a3e6-00a587c0ed08.png)
- cloudinay API를 사용하여 이미지를 cloudinay에 저장하고, 이미지 URL을 MongoDB에 저장하여 이미지 업로드 기능을 구현했다. .env파일 안에 API 키를 저장해두고 소스 파일에 불러오는 방식으로 깃허브에 민감한 정보가 업로드 되지 않도록 코드를 짜본 것은 흥미로웠다.

### 7. Geocoding
![image](https://user-images.githubusercontent.com/101307758/225564222-899fa9cb-d421-4f87-8e9f-68cf2a024327.png)
- Mapbox API를 사용하여 캠핑장의 위치를 지도에 표시했고, 클러스터 기능을 구현했다. 10곳이 뭉쳐 있으면 하나의 큰 원을 형성하는 기능이 클러스터 기능이다. 10곳, 20곳을 기점으로 원이 더 커지도록 구현했다.
- 지도에 점을 클릭하면 팝업창이 뜨고, 링크를 타면 해당 캠핑장 상세 정보 페이지로 접근하도록 했다. 

### 8. Security
- SQL Injection, XSS 공격에 대해서 배웠고, 해당 공격을 방어하도록 웹사이트를 설계했다.
- Helmet NPM 패키지를 활용하여 content security policy를 설정하려고 시도했으나 실패했다. 다음 기회에 구현해보도록 하자.

### 9. Deployment : Render
- 원래 계획은 Heroku 서버를 활용하여 웹사이트를 배포하는 것이었다. 하지만, 작년 말에 Heroku 측에서 프리티어를 전면 폐지하는 정책을 펴는 바람에 Heroku를 사용하려면 돈이 드는 상황이 발생했다. 돈이 드는 건 원치 않았기 때문에 다른 대안을 찾다가 [Render](https://render.com/) 를 발견했다. Heroku와 비슷했고, 관련 자료도 많아 배포를 성공할 수 있었다. 첫 배포라 불안했지만, 잘 끝나서 기쁘다. 
- https://yelp-campground.onrender.com/

## 앞으로 계획
- 본격적으로 Java와 스프링에 매진해서 최종 프로젝트를 만들어보자 
