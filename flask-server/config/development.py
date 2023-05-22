from config.default import *

SQLALCHEMY_DATABASE_URI = 'postgresql+psycopg2://{user}:{pw}@{url}/{db}'.format(
    user=os.getenv('DB_USER'),
    pw=os.getenv('DB_PASSWORD'),
    url=os.getenv('DB_HOST'),
    db=os.getenv('DB_NAME'))

SQLALCHEMY_TRACK_MODIFICATIONS = False
SECRET_KEY = b'\xc54\x15\xfe\x7f\x92\xc3#\xef\x1a\xeb\xd7}\x96\xac\xc0'
