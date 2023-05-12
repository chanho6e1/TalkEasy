import psycopg2
import os

from flask import Flask, request, jsonify
import datetime as dt

# from flask_sqlalchemy import SQLAlchemy
from shapely.wkb import loads

# db = SQLAlchemy()
app = Flask(__name__)


# app.config.from_envvar('APP_CONFIG_FILE')

@app.route('/')
def hello():
    return "hello"


@app.route('/location', methods=['GET'])
def get_location():
    # db.init_app(app)
    db = psycopg2.connect(dbname=os.getenv('DB_NAME'),
                          user=os.getenv('DB_USER'),
                          host=os.getenv('DB_HOST'),
                          password=os.getenv('DB_PASSWORD'),
                          port=5432)

    cur = db.cursor()
    id = request.args.get('id')

    now = dt.datetime.now()
    today = dt.datetime(now.year, now.month, now.day, 0, 0, 0)

    last_week = today - dt.timedelta(days=7)
    print(last_week)

    sql = "select * from location where user_id=%s and date_time>=%s"
    cur.execute(sql, (id, last_week))
    rows = cur.fetchall()
    length = len(rows)

    ids = []
    for row in rows:
        ids.append(row[0])

    flags = [False]*length
    locations = []
    for i in range(length):
        if flags[i]:
            continue
        flags[i] = True

        cur_id = ids[i]

        # 만약 flag 처리됐다면 pass
        sql = "select * from (select * from location where user_id = %s and date_time >= %s) as week_table where ST_DWithin(geom, %s,10)"
        cur.execute(sql, (id, last_week, rows[i][4]))
        cur_rows = cur.fetchall()

        # flag 처리
        for j in cur_rows:
            print(j)
        break;

    locations = [location.json() for location in locations]

    # 리턴
    # locations = []
    # for row in rows:
    #     location = Location(row[1], row[4])
    #     locations.append(location)
    #
    # locations = [location.json() for location in locations]

    return jsonify(locations)


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
