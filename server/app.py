#! .env/bin/python

from flask import Flask
from flask_restful import Api
import time

app = Flask(__name__)
api = Api(app, catch_all_404s=True)

from resources import CardResource
from resources import CardListResource

api.add_resource(CardListResource, '/cards', endpoint='cards')
api.add_resource(CardResource, '/card/<string:cardId>', endpoint='card')

if __name__ == '__main__':
    from models import create_mock_database
    print ("Starting Server")
    app.run(host='0.0.0.0')