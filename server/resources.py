#! .env/bin/python

from models import Card
from db import session

from flask_restful import reqparse
from flask_restful import abort
from flask_restful import Resource
from flask_restful import fields
from flask_restful import marshal_with

card_fields = {
    'cardId': fields.String,
    'name': fields.String,
    'cardSet': fields.String,
    'type': fields.String,
    'rarity': fields.String,
    'cost': fields.Integer,
    'attack': fields.Integer,
    'health': fields.Integer,
    'text': fields.String,
    'flavor': fields.String,
    'artist': fields.String,
    'collectible': fields.Boolean,
    'elite': fields.Boolean,
    'playerClass': fields.String,
    'multiClassGroup': fields.String,
    'howToGet': fields.String,
    'howToGetGold': fields.String,
    'img': fields.String,
    'imgGold': fields.String,
    'locale': fields.String,
}

parser = reqparse.RequestParser()
parser.add_argument('card', type=str)

class CardResource(Resource):
    @marshal_with(card_fields)
    def get(self, cardId):
        card = session.query(Card).filter(Card.cardId == cardId).first()
        if not card:
            abort(404, message="Card {} doesn't exist".format(cardId))
        return card

class CardListResource(Resource):
    @marshal_with(card_fields)
    def get(self):
        cards = session.query(Card).all()
        return cards
