#! .env/bin/python

import os

basedir = os.path.abspath(os.path.dirname(__file__))
DATABASE_URI = 'sqlite:///' + os.path.join(basedir, 'data/app.sqlite')
