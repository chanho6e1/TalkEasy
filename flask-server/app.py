import psycopg2
import os

from flask import Flask, request, jsonify
import datetime as dt

from shapely.wkb import loads

app = Flask(__name__)


@app.route('/')
def hello():
    return "hello"


@app.route('/location', methods=['GET'])
def get_location():
    # postgres 연결
    db = psycopg2.connect(dbname=os.getenv('DB_NAME'),
                          user=os.getenv('DB_USER'),
                          host=os.getenv('DB_HOST'),
                          password=os.getenv('DB_PASSWORD'),
                          port=5432)

    cur = db.cursor()

    # DB 쿼리 매개변수
    user_id = request.args.get('id')
    last_week = calculate_one_week_ahead()

    # DB 쿼리 실행
    sql = "select * from location where user_id=%s and date_time>=%s"
    cur.execute(sql, (user_id, last_week))
    rows = cur.fetchall()

    # location id 자주 사용, 저장
    location_ids = []
    for row in rows:
        location_ids.append(row[0])

    flag_id_set = set([])   # flag 역할
    results = []             # 결과 담을 2차원 배열
    for i in range(len(rows)):
        if location_ids[i] in flag_id_set:
            continue

        flag_id_set.add(location_ids[i])   # 방문 체크

        sql = "select * from (select * from location where user_id = %s and date_time >= %s) as week_table where ST_DWithin(geom, %s, 10)"
        cur.execute(sql, (user_id, last_week, rows[i][4]))  # [4] : point
        near_rows = cur.fetchall()

        cnt = 0
        for row in near_rows:
            if row[0] not in flag_id_set:
                flag_id_set.add(row[0])     # 방문 체크
                cnt = cnt + 1               # 방문 안한 point count

        new_list = [i, location_ids[i], cnt]    # 리턴할 좌표
        results.append(new_list)                # result에 추가

    results.sort(key=lambda x: (-x[2], x[0]))   # cnt 내림차순, idx 오름차순으로 정렬
    print(results)

    return result_to_jsonify(rows, results)


class Location:
    def __init__(self, date_time, point):
        self.date_time = date_time
        self.point = loads(point, hex=True).wkt

    def json(self):
        return {
            'date_time': self.date_time,
            'point': self.point
        }


if __name__ == '__main__':
    app.run(debug=True)


def calculate_one_week_ahead():
    now = dt.datetime.now()
    today = dt.datetime(now.year, now.month, now.day, 0, 0, 0)
    return today - dt.timedelta(days=7)


def result_to_jsonify(rows, results):
    locations = []
    for result in results:
        row = rows[result[0]]
        location = Location(row[1], row[4])
        locations.append(location)

    return jsonify([location.json() for location in locations])
