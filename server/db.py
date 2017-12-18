#! .env/bin/python

from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session
from sqlalchemy.orm import sessionmaker
from config import DATABASE_URI

Session = sessionmaker(autocommit=False,
                       autoflush=False,
                       bind=create_engine(DATABASE_URI))
session = scoped_session(Session)