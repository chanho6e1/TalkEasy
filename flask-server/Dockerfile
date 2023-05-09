FROM python:3



WORKDIR /app
# 해당 디렉토리에 있는 모든 하위항목들을 '/app'으로 복사
COPY . /app


RUN pip install -r requirements.txt

CMD ["python3", "-m", "flask", "run", "--host=0.0.0.0"]