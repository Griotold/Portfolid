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
# 시연 영상
https://www.youtube.com/watch?v=M0EddEJAT_Y

# 챗봇 링크
http://pf.kakao.com/_YvNLb/chat

# References
https://chatbot.kakao.com/docs/skill-build#%EC%8A%A4%ED%82%AC-%EC%84%9C%EB%B2%84%EB%9E%80
