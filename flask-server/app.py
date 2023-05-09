import psycopg2

from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()
app = Flask(__name__)
app.config.from_envvar('APP_CONFIG_FILE')

@app.route('/')
def hello():
    return "hello"


@app.route('/location', methods=['GET'])
def get_location():
    db.init_app(app)
    cur = db.cursor()
    email = request.args.get('email')
    print(type(email))
    sql = "select * from location where email=%s"
    cur.execute(sql, (email,))
    rows = cur.fetchall()

    result = []
    for row in rows:
        result.append(row)

    return jsonify(result)


if __name__ == '__main__':
    app.run(debug=True)
