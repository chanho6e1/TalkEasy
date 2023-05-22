import psycopg2
import os

from flask import Flask, jsonify
import datetime as dt
from pymongo import MongoClient, InsertOne
from shapely.wkb import loads
from flask import Response

app = Flask(__name__)

# mongo
client = MongoClient(os.getenv('MONGO_DB_URL'))
mongodb = client.test

# postgres 연결
db = psycopg2.connect(dbname=os.getenv('DB_NAME'),
                      user=os.getenv('DB_USER'),
                      host=os.getenv('DB_HOST'),
                      password=os.getenv('DB_PASSWORD'),
                      port=5432)

cur = db.cursor()


@app.route('/')
def hello():
    return "hello"


@app.route('/location', methods=['GET'])
def get_location():
    all_members_id = mongodb.member.distinct("_id")
    last_week = calculate_one_week_ahead()

    mongodb.report.delete_many({})

    for member_id in all_members_id:
        member_id = str(member_id)
        sql = "select * from location where user_id=%s and date_time>=%s"
        cur.execute(sql, (member_id, last_week))
        rows = cur.fetchall()

        location_ids = []
        for row in rows:
            location_ids.append(row[0])

        flag_id_set = set([])  # flag 역할
        result_per_id = []  # 결과 담을 2차원 배열
        for i in range(len(rows)):
            if location_ids[i] in flag_id_set:
                continue

            flag_id_set.add(location_ids[i])  # 방문 체크

            sql = "select * from (select * from location where user_id = %s and date_time >= %s) as week_table where ST_DWithin(geom, %s, 10)"
            cur.execute(sql, (member_id, last_week, rows[i][4]))  # [4] : point
            near_rows = cur.fetchall()

            cnt = 0
            for row in near_rows:
                if row[0] not in flag_id_set:
                    flag_id_set.add(row[0])  # 방문 체크
                    cnt = cnt + 1  # 방문 안한 point count

            new_list = [i, location_ids[i], cnt]  # 리턴할 좌표
            result_per_id.append(new_list)  # result에 추가

        result_per_id.sort(key=lambda x: (-x[2], x[0]))  # cnt 내림차순, idx 오름차순으로 정렬

        if len(result_per_id) > 5:
            result_per_id = result_per_id[:5]

        if len(result_per_id) > 0:
            save_to_mongodb(rows, result_per_id)

    return Response(status=200, mimetype='application/json')


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


def save_to_mongodb(rows, result_per_id):


    bulk_operations = []
    for result in result_per_id:
        row = rows[result[0]]
        user_id = row[3]
        datetime = row[1]
        point = loads(row[4], hex=True)

        doc = {'userId': user_id, 'datetime': datetime, 'lat': point.x, 'lon': point.y}
        bulk_operations.append(InsertOne(doc))

    try:
        mongodb.report.bulk_write(bulk_operations)
        return "Data saved successfully", 200
    except:
        print("insert failed"), 500


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
