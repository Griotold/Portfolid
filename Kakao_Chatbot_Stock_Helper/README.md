# Kakao_Chatbot_Stock_Helper
- 본 프로젝트는 카카오톡 오픈빌더를 이용하여 주식 입문자를 위한 챗봇을 구현한 것이다.
![image](https://user-images.githubusercontent.com/101307758/171578468-013ce4e2-e419-4390-8b80-fbb44dd72fc3.png)

# 목표
- 주식 입문자들에게 유용한 정보를 제공하고자 한다.
- 카카오톡 오픈빌더, HEROKU, PostgreSQL을 이용하여 카카오톡 스킬 서버 및 데이터베이스를 연동해본다. 

# 주요 서비스 내용
- 각 증권사 수수료 정보 제공
- 주식 용어 설명
- 실시간 주식 종목 시세 정보 제공
- 인기있는 종목 정보 제공
  - 거래 상위, 검색 상위
- 주식 관련 정보 제공
- 모의 주식 투자 체험
 
# 설명
- 인원 : 4명
- 언어 : Python
- 서버 : HEROKU
- DB : PostgreSQL
- 사용 라이브러리 : pandas, requests, BeautifulSoup, flask, jsonify, psycopg2
- 기간: 2022.05.20. ~ 2022.06.03.
- 담당업무 : 챗봇 전체 시나리오 기획, 웹크롤링을 통한 데이터 수집, 카카오톡 스킬 구현, DB 구축

# 중요 포인트
## 1. 스킬 서버

- 이번 프로젝트에서 가장 중요했다고 생각되는 것이 스킬 서버를 구현하는 것이었다. 우리 챗봇의 주요 서비스가 실시간 주식 시세를 제공하는 것이기 때문이다.
![image](https://user-images.githubusercontent.com/101307758/171761782-64c035d3-7eb6-485a-8678-2123d24984c8.png)

- HEROKU를 이용하여 스킬 서버를 만들었다. HEROKU 로그인 후 폴더 내에 해당 파일들이 모두 있는 상태에서 
![image](https://user-images.githubusercontent.com/101307758/171762093-84ada926-269a-4706-b919-92238d63ae0b.png)
- 깃으로 배포를 한다. 자세한 사항은 code폴더에 main.py를 확인.

```Python
git init
git add.
git commit -m "m"
git push heroku master

```
- 스킬 서버를 만든 후, 챗봇에 스킬을 등록한다.
![image](https://user-images.githubusercontent.com/101307758/171763370-f8cf0ea0-6836-4ebe-a5f6-0a47d962eb3b.png)
- 스킬이 정상 작동하는지 응답 미리보기 테스트를 한다.
![image](https://user-images.githubusercontent.com/101307758/171763453-f1426077-4ee4-49ba-9f66-865201f2377d.png)
- 시나리오 블록에 스킬을 설정한다.
- 봇 테스트를 진행한다.

![image](https://user-images.githubusercontent.com/101307758/171763641-d3caede9-ecec-4a76-b740-a2134861b073.png)
- 최종 배포를 한다.

## 2. 웹 크롤링
- 사용자들에게 제공할 데이터는 대부분 웹 크롤링으로 수집했다. 기획 단계에서는 Selenium 라이브러리를 이용하여 웹 크롤링을 하려 했으나, 카카오톡 스킬 서버에 연동하는 과정에서 카카오톡이 지정한 지연 시간 5초가 초과되는 상황 때문에 사용을 할 수가 없었다. 조사 결과 WebChromedriver 앱을 이용하는 것 때문인 것으로 확인 됐다. 결국 BeautifulSoup 으로 바꿔서 진행했다. AWS EC2로 배포하면 Selenium으로 스킬 서버 연동이 가능하나, 유료인 관계로 다음 기회에 하기로 한다. 자세한 사항은 code폴더에.

## 3. 데이터베이스
- 모의 주식 투자 체험, 사용자 평점 수집에 데이터베이스를 사용했다. 작업도구로 PostgreSQL을 사용했다.   


# 시연 영상
https://www.youtube.com/watch?v=M0EddEJAT_Y

# 챗봇 링크
http://pf.kakao.com/_YvNLb/chat

# References
https://chatbot.kakao.com/docs/skill-build#%EC%8A%A4%ED%82%AC-%EC%84%9C%EB%B2%84%EB%9E%80
