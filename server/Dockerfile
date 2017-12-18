FROM python:3.6.2

ADD requirements.txt /app/
WORKDIR /app
RUN pip install -r requirements.txt

RUN python --version

ADD . /app
WORKDIR /app

RUN mkdir -p data
RUN python models.py
RUN ls data

EXPOSE 5000
ENTRYPOINT ["python"]
CMD [ "app.py" ]
