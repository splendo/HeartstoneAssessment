#! .env/bin/python

from sqlalchemy import Column
from sqlalchemy import Integer
from sqlalchemy import String
from sqlalchemy import Boolean
from sqlalchemy import ForeignKey
from sqlalchemy import Table
from sqlalchemy import ARRAY
from sqlalchemy.orm import relationship
from sqlalchemy.ext.declarative import declarative_base

import json
import random
from pprint import pprint

Base = declarative_base()

association_table = Table('association', Base.metadata,
    Column('left_id', Integer, ForeignKey('card.cardId')),
    Column('right_id', Integer, ForeignKey('cardmechanic.name'))
)

class CardMechanic(Base):
    __tablename__ = 'cardmechanic'
    
    name = Column(String(255), primary_key=True)

class Card(Base):
    __tablename__ = 'card'
    
    cardId = Column(String(255), primary_key=True)
    name = Column(String(255))
    cardSet = Column(String(255))
    type = Column(String(255))
    rarity = Column(String(255))
    cost = Column(Integer)
    attack = Column(Integer)
    health = Column(Integer)
    text = Column(String(255))
    flavor = Column(String(255))
    artist = Column(String(255))
    collectible = Column(Boolean)
    elite = Column(Boolean)
    playerClass = Column(String(255))
    howToGet = Column(String(255))
    howToGetGold = Column(String(255))
    multiClassGroup = Column(String(255))
    img = Column(String(255))
    imgGold = Column(String(255))
    locale = Column(String(10))
    mechanics = relationship('CardMechanic', secondary=association_table)

def create_mock_database():
    print ("Creating Mock Database")
    
    from sqlalchemy import create_engine
    from config import DATABASE_URI
    engine = create_engine(DATABASE_URI)
    Base.metadata.drop_all(engine)
    Base.metadata.create_all(engine)

    from db import session

    card_filenames = ['card1.json', 'card2.json']
    for idx, card_filename in enumerate(card_filenames):
        with open('examples/%s' % card_filename) as data_file:
            data = json.load(data_file)
            for x in range(20):
                card = Card(**data)
                card.cardId = idx * 20 + x
                session.add(card)
                session.commit()
            

if __name__ == '__main__':
	create_mock_database()
