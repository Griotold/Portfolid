from flask import Flask, request, jsonify
import json
import requests
from bs4 import BeautifulSoup
import os
import pandas as pd
from tabulate import tabulate
import psycopg2
# import pandas_datareader.data as web
# from kospi import search_kospi


# 메인 로직!! 
def cals(opt_operator, number01, number02):
    if opt_operator == "addition":
        return number01 + number02
    elif opt_operator == "subtraction": 
        return number01 - number02
    elif opt_operator == "multiplication":
        return number01 * number02
    elif opt_operator == "division":
        return number01 / number02


app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello, Worlds24!'

# 카카오톡 텍스트형 응답
@app.route('/api/sayHello', methods=['POST'])
def sayHello():
    body = request.get_json() # 사용자가 입력한 데이터
    print(body)
    print(body['userRequest']['utterance'])

    responseBody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": "안녕 hello I'm Ryan"
                    }
                }
            ]
        }
    }

    return responseBody


# 카카오톡 이미지형 응답
@app.route('/api/showHello', methods=['POST'])
def showHello():
    body = request.get_json()
    print(body)
    print(body['userRequest']['utterance'])

    responseBody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleImage": {
                        "imageUrl": "https://t1.daumcdn.net/friends/prod/category/M001_friends_ryan2.jpg",
                        "altText": "hello I'm Ryan"
                    }
                }
            ]
        }
    }

    return responseBody


# 카카오톡 Calculator 계산기 응답
@app.route('/api/calCulator', methods=['POST'])
def calCulator():
    body = request.get_json()
    print(body)
    params_df = body['action']['params']
    print(type(params_df))
    opt_operator = params_df['operators']
    number01 = json.loads(params_df['sys_number01'])['amount']
    number02 = json.loads(params_df['sys_number02'])['amount']

    print(opt_operator, type(opt_operator), number01, type(number01))

    answer_text = str(cals(opt_operator, number01, number02))

    responseBody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": answer_text
                        # "text": "answer_text"
                    }
                }
            ]
        }
    }

    return responseBody

# 코스피
@app.route('/api/kospi4', methods=['POST'])
def sear_kospi():
    url = "https://finance.naver.com/sise/sise_index.naver?code=KOSPI"

    response = requests.get(url)

    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        goal = soup.select_one('#now_value')
        goal_str = str(goal.get_text())
        # print(goal.get_text())
        # print(type(goal_str))
    
        goal2 = soup.select_one('#change_value_and_rate')
        # print(goal2.get_text())
        # print(type(goal2))
        goal2_str = str(goal2.get_text())
        # print(goal2_str)
        # print(type(goal2_str))
        # goal2_str_split = goal2_str.split(" ")
        # goal3 = goal2_str_split.insert(0, goal)
        # print(goal3)
        final_goal = goal_str + ' 이고 '  + goal2_str
        print(final_goal)
    else:
        print(response.status_code)

    responseBody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": "현재 코스피 지수는 " + str(final_goal) + "했습니다."
                    }
                }
            ]
        }
    }
    return jsonify(responseBody)

# 코스닥
@app.route("/kosdaq4", methods=["post"])
def sear_kosdaq():
    url = "https://finance.naver.com/sise/sise_index.naver?code=KOSDAQ"

    response = requests.get(url)

    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        goal3 = soup.select_one('#now_value')
        goal3_str = str(goal3.get_text())
        # print(goal.get_text())
        # print(type(goal_str))
    
        goal4 = soup.select_one('#change_value_and_rate')
        # print(goal2.get_text())
        # print(type(goal2))
        goal4_str = str(goal4.get_text())
        # print(goal2_str)
        # print(type(goal2_str))
        # goal2_str_split = goal2_str.split(" ")
        # goal3 = goal2_str_split.insert(0, goal)
        # print(goal3)
        final_goal5 = goal3_str + ' 이고 '  + goal4_str
        print(final_goal5)
    else:
        print(response.status_code)
        
    responsebody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": "현재 코스닥 지수는 " + str(final_goal5) + "했습니다."
                    }
                }
            ]
        }
    }
    return jsonify(responsebody)

# top 5
@app.route("/top5", methods=["post"])
def top5():
    url = 'https://finance.naver.com/'

    response = requests.get(url)
    response.raise_for_status()
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    tbody = soup.select_one('#container > div.aside > div.group_aside > div.aside_area.aside_popular > table > tbody')
    trs = tbody.select('tr')
    datas = []
    for tr in trs:
        name = tr.select_one('th > a').get_text()
        current_price = tr.select_one('td').get_text() 
        change_direction = []
        if tr['class'][0] == "up":
            change_direction.append("▲")
        else:
            change_direction.append("▼")
        change_price = tr.select_one('td > span').get_text()
        datas.append([name, current_price, change_direction, change_price])

    # print(datas)
    df = pd.DataFrame(datas, columns=['종목명', '현재가', '등락', '전일대비' ], index=range(1, 6))
    df = str(df)
    print(df)
    responsebody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": df
                    }
                }
            ]
        }
    }
    return jsonify(responsebody)

# 거래 상위 10
@app.route("/trade10", methods=["post"])
def top30():
    url = "https://finance.naver.com/sise/sise_quant.naver"
    response = requests.get(url)
    html = response.text
    soup = BeautifulSoup(html, 'html.parser')
    # print("goo")

    # # col_list : 종목명 현재가 거래량
    title1 = soup.select('th')    
    col_list = []
    # print(len(title1)) # 12개

    # 컬럼명
    for i in range(len(title1)):
        if (title1[i].text == "종목명") or (title1[i].text == "현재가") or (title1[i].text == "거래량"):
            col_list.append(title1[i].text)
    # print("col_list ", len(col_list))
    # print(col_list)

    # title_list : 주식 종목 리스트
    title2 = soup.findAll("a", class_="tltle")
    title_list = []
    # print(len(title2)) # 100개
    for i in range(len(title2)):
        if len(title2[i].text) > 7:
            title_list.append(title2[i].text[0:7]+"...")
        else:
            title_list.append(title2[i].text)
        if len(title_list) == 10 :
            break
    # print(title_list)
    # print("title_list ", len(title_list)) # 10개

    # data_list : 현재가 , 거래량
    title3 = soup.find_all("td", class_="number")
    # print(len(title3)) # 1000개
    data_list = []

    for i in range(len(title3)):
        j = divmod(i, 10)
        if (j[1] == 0) or (j[1] == 3):
            data_list.append(title3[i].text)
            if len(data_list) == 20 :
                break
    # print(data_list)

    # title + data
    step01_list = []
    for i in range(len(title_list)):
        step01_list.append([])
        step01_list[i].append(title_list[i])
    # print(step01_list) # 10개
        for j in range(len(data_list)):
            k = divmod(j, 2)
            if k[0] == i :
                step01_list[i].append(data_list[j])
    print(step01_list)

    # print(tabulate(step01_list, tablefmt='pipe', stralign='right'))
    df = pd.DataFrame(step01_list, columns=['종목명', '현재가', '거래량'], index=range(1, 11))
    df = str(df)
    print(df)   
    responsebody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": df
                    }
                }
            ]
        }
    }
    return jsonify(responsebody)

# 코스피 상승주
@app.route("/kospirising", methods=["post"])
def kospi_rising():
    url = "https://finance.naver.com/sise/sise_rise.naver"
    response = requests.get(url)

    html = response.text
    soup = BeautifulSoup(html, 'html.parser')

    title1 = soup.select('th')
    col_list = []
    # print(len(title1))

    # 컬럼명 
    for i in range(len(title1)):
        if (title1[i].text == "종목명") or (title1[i].text == "현재가") or (title1[i].text == "등락률"):
            col_list.append(title1[i].text)
    print("col_list ", len(col_list))
    print(col_list)

    # title_list : 주식 종목 리스트
    title2 = soup.find_all("a", class_="tltle")
    title_list = []
    print(len(title2)) # 1054개

    for i in range(len(title2)):
        if len(title2[i].text) > 6:
            title_list.append(title2[i].text[0:6])
        else:
            title_list.append(title2[i].text)
        if len(title_list) == 10 :
            break
    # print(title_list)
    print("title_list ", len(title_list))

    # # data_list : 종목별 데이터
    title3 = soup.find_all("td", class_="number")
    # print(len(title3)) # 10000개 데이터
    data_list1 = []

    for i in range(len(title3)):
        j = divmod(i ,10)
        if (j[1] == 0):
            data_list1.append(title3[i].text)
        if len(data_list1) == 10 :
            break
    print(data_list1)

    title4 = soup.find_all("span", class_="tah p11 red01")
    print(len(title4)) # 1000개 데이터 
    data_list2 = []
    for i in range(len(title3)):
        data_list2.append(title4[i].text)
        if len(data_list2) == 10 :
            break
    strip_list2 = []
    for i in data_list2:
        i = i.strip()
        strip_list2.append(i)
    print(strip_list2)

    data_list = []
    for i in range(len(data_list1)):
        data_list.append(data_list1[i])
        data_list.append(strip_list2[i])
    print(data_list)
        
    # title + data
    step01_list = []
    for i in range(len(title_list)):
        step01_list.append([])
        step01_list[i].append(title_list[i])
    # print(step01_list)
        for j in range(len(data_list)):
            k = divmod(j, 2)
            if k[0] == i :
                step01_list[i].append(data_list[j])
    print(step01_list)
    print("step01 ", len(step01_list))

    df = pd.DataFrame(step01_list, columns=['종목명', '현재가', '등락률'], index=range(1, 11))
    df = str(df)
    print(df)

    responsebody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": df
                    }
                }
            ]
        }
    }
    return jsonify(responsebody)

# 코스닥 상승주
@app.route("/kosdaqrising", methods=["post"])
def kosdaq_rising():
    url = "https://finance.naver.com/sise/sise_rise.naver?sosok=1"
    response = requests.get(url)

    html = response.text
    soup = BeautifulSoup(html, 'html.parser')

    # print("good")
    title1 = soup.select('th')
    col_list = []
    # print(len(title1))

    # 컬럼명 
    for i in range(len(title1)):
        if (title1[i].text == "종목명") or (title1[i].text == "현재가") or (title1[i].text == "등락률"):
            col_list.append(title1[i].text)
    print("col_list ", len(col_list))
    print(col_list)

    # title_list : 주식 종목 리스트
    title2 = soup.find_all("a", class_="tltle")
    title_list = []
    print(len(title2)) # 1054개

    for i in range(len(title2)):
        if len(title2[i].text) > 6:
            title_list.append(title2[i].text[0:6])
        else:
            title_list.append(title2[i].text)
        if len(title_list) == 10 :
            break
    # print(title_list)
    print("title_list ", len(title_list))

    # # data_list : 종목별 데이터
    title3 = soup.find_all("td", class_="number")
    # print(len(title3)) # 10000개 데이터
    data_list1 = []

    for i in range(len(title3)):
        j = divmod(i ,10)
        if (j[1] == 0):
            data_list1.append(title3[i].text)
        if len(data_list1) == 10 :
            break
    print(data_list1)

    title4 = soup.find_all("span", class_="tah p11 red01")
    print(len(title4)) # 1000개 데이터 
    data_list2 = []
    for i in range(len(title3)):
        data_list2.append(title4[i].text)
        if len(data_list2) == 10 :
            break
    strip_list2 = []
    for i in data_list2:
        i = i.strip()
        strip_list2.append(i)
    print(strip_list2)

    data_list = []
    for i in range(len(data_list1)):
        data_list.append(data_list1[i])
        data_list.append(strip_list2[i])
    print(data_list)
        
    # title + data
    step01_list = []
    for i in range(len(title_list)):
        step01_list.append([])
        step01_list[i].append(title_list[i])
    # print(step01_list)
        for j in range(len(data_list)):
            k = divmod(j, 2)
            if k[0] == i :
                step01_list[i].append(data_list[j])
    print(step01_list)
    print("step01 ", len(step01_list))

    df = pd.DataFrame(step01_list, columns=['종목명', '현재가', '등락률'], index=range(1, 11))
    df = str(df)
    print(df)

    responsebody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": df
                    }
                }
            ]
        }
    }
    return jsonify(responsebody)


# 코스피 하락주 top30
@app.route('/api/deScend', methods=['POST'])
def deScend_search():
    
    url = "https://finance.naver.com/sise/sise_fall.naver?sosok=0"
    response = requests.get(url)
    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        # # col_list : 종목명  현재가 등락률
        title1 = soup.select('th')
        col_list = []
        for i in range(len(title1)):
            if (title1[i].text == "종목명") or (title1[i].text == "현재가") or (title1[i].text == "등락률"):
                col_list.append(title1[i].text)
        # print("col_list ", len(col_list))
        # print(col_list)
        # # title_list : 주식 종목 리스트
        title2 = soup.findAll("a", class_="tltle")
        title_list = []
        for i in range(len(title2)):
            if len(title2[i].text) > 6:
                title_list.append(title2[i].text[0:6]+"...")
            else:
                title_list.append(title2[i].text)
            if len(title_list) == 10 :
                break
        # print(title_list)
        # print("title_list " , len(title_list))
        # # data_list : 종목별 데이터
        title3 = soup.find_all("td", class_="number")
        data_list = []
        for i in range(len(title3)):
            j = divmod(i, 10)
            if (j[1] == 0) or (j[1] == 2):
                data_list.append(title3[i].text.strip())
            if len(data_list) == 40 :
                break
        # print(data_list)
        # print("data_list ", len(data_list))
        # # title + data
        step01_list = []
        for i in range(len(title_list)):
            step01_list.append([])
            step01_list[i].append(title_list[i])
            for j in range(len(data_list)):
                k = divmod(j, 2)
                if k[0] == i :
                    step01_list[i].append(data_list[j])
        # print("step01 ", len(step01_list))
        # print(step01_list)
        result = str(pd.DataFrame(step01_list, columns=col_list, index=range(1, 11)))
        print(result)
    else:
        print(response.status_code)
    responseBody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": result
                    }
                }
            ]
        }
    }
    return jsonify(responseBody)

# KOSDAQ 하락주 top30
@app.route('/api/kosDescend', methods=['POST'])
def kosDescend_search():
    url = "https://finance.naver.com/sise/sise_fall.naver?sosok=1"
    response = requests.get(url)
    if response.status_code == 200:
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        # # col_list : 종목명  현재가 등락률
        title1 = soup.select('th')
        col_list = []
        for i in range(len(title1)):
            if (title1[i].text == "종목명") or (title1[i].text == "현재가") or (title1[i].text == "등락률"):
                col_list.append(title1[i].text)
        # print("col_list ", len(col_list))
        # print(col_list)
        # # title_list : 주식 종목 리스트
        title2 = soup.findAll("a", class_="tltle")
        title_list = []
        for i in range(len(title2)):
            if len(title2[i].text) > 6:
                title_list.append(title2[i].text[0:6])
            else:
                title_list.append(title2[i].text)
            if len(title_list) == 10 :
                break
        # print(title_list)
        # print("title_list " , len(title_list))
        # # data_list : 종목별 데이터
        title3 = soup.find_all("td", class_="number")
        data_list = []
        for i in range(len(title3)):
            j = divmod(i, 10)
            if (j[1] == 0) or (j[1] == 2):
                data_list.append(title3[i].text.strip())
            if len(data_list) == 40 :
                break
        # print(data_list)
        # print("data_list ", len(data_list))
        # # title + data
        step01_list = []
        for i in range(len(title_list)):
            step01_list.append([])
            step01_list[i].append(title_list[i])
            for j in range(len(data_list)):
                k = divmod(j, 2)
                if k[0] == i :
                    step01_list[i].append(data_list[j])
        # print("step01 ", len(step01_list))
        # print(step01_list)
        result = str(pd.DataFrame(step01_list, columns=col_list, index=range(1, 11)))
        print(result)
    else:
        print(response.status_code)
    responseBody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text":result
                    }
                }
            ]
        }
    }
    return jsonify(responseBody)

# 남은 잔액
@app.route('/api/balance', methods=['POST'])
def balance():
    body = request.get_json() # 사용자가 입력한 데이터
    user1 = "'%s'" %str(body['userRequest']['user']['id']) 

    passwd = 'b55d94be7d8dbef24e28a72a0dcb228fb48d1595665100e8da2cd1aafbe8bbbc'
    db = psycopg2.connect(host='ec2-54-204-56-171.compute-1.amazonaws.com', dbname='d2p5j2up8o05rg',user='dywzgxybcyjnzu',password= passwd,port=5432)

    cur=db.cursor()



    # cur.execute("SELECT * FROM game WHERE userid = %s;"% (user))



    # cur.execute("SELECT money FROM game WHERE userid=%s AND money IS NOT null;"% (user1)) 
    # rows = cur.fetchone()
    # str(rows)
    # print(rows)
    #     # cur.execute("INSERT INTO game (userid, item, shares) VALUES (%s, %s, %s);"% (user, item, many) )

    # db.commit()

    cur.execute("SELECT * FROM game WHERE userid=%s AND money IS NOT null;"% (user1)) 
    rows = cur.fetchone()
    # str(rows)
    target = rows[1]
    target = str(target)
    print(target)

    responseBody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": "현재 남은 잔액은 " + target + "원입니다."
                    }
                }
            ]
        }
    }
    return jsonify(responseBody)

# 현재 보유 주식
@app.route('/api/holdingstock', methods=['POST'])
def holding_stock():

    body = request.get_json() # 사용자가 입력한 데이터
    user1 = "'%s'" %str(body['userRequest']['user']['id'])  


    passwd = 'b55d94be7d8dbef24e28a72a0dcb228fb48d1595665100e8da2cd1aafbe8bbbc'
    db = psycopg2.connect(host='ec2-54-204-56-171.compute-1.amazonaws.com', dbname='d2p5j2up8o05rg',user='dywzgxybcyjnzu',password= passwd,port=5432)

    cur=db.cursor()



    cur.execute("SELECT * FROM game WHERE userid=%s AND money IS null;"% (user1)) 
    rows = cur.fetchall()
    print(rows)
    # print(len(rows))
    stock_list = []
    for i in range(len(rows)):
        stock_list.append(rows[i][2])
    print(stock_list)

    many_list = []
    for i in range(len(rows)):
        many_list.append(rows[i][3])
    print(many_list)

    final_list = []
    for i in range(len(stock_list)):
        final_list.append([])
        final_list[i].append(stock_list[i])
        for j in range(len(many_list)):
            if i == j:

                final_list[i].append(many_list[i])
    # print(final_list)

    df = str(pd.DataFrame(final_list, columns=["종목명", "주식수"], index=range(1, len(stock_list)+1)))
    print(df)

    responseBody = {
        "version": "2.0",
        "template": {
            "outputs": [
                {
                    "simpleText": {
                        "text": df
                    }
                }
            ]
        }
    }
    return jsonify(responseBody)