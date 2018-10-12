from __main__ import app
from flask_sqlalchemy import SQLAlchemy
app.config['SQLALCHEMY_DATABASE_URI'] = 'sqlite:///old.db'   # to tell the program that a file named site.db exists on the relative path
app.config['SQLALCHEMY_BINDS'] = {'young': 'sqlite:///young.db'}
db = SQLAlchemy(app)

class Old(db.Model):     # database for old people
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100))
    gender = db.Column(db.String(1))
    gender_preference = db.Column(db.String(1))
    contact = db.Column(db.String(10))
    latitude = db.Column(db.Numeric(10,2))
    longitude = db.Column(db.Numeric(10,2))
    interests = db.Column(db.Text)
    unique_id = db.Column(db.String(50))
    profile_image = db.Column(db.String(200), nullable=True)
    appointment = db.Column(db.Text, nullable=True)
    password = db.Column(db.Text)

class Young(db.Model):    # database for young people
    __bind_key__ = 'young'
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(100))
    gender = db.Column(db.String(1))
    contact = db.Column(db.String(10))
    latitude = db.Column(db.Numeric(10,2))
    longitude = db.Column(db.Numeric(10,2))
    interests = db.Column(db.Text)
    unique_id = db.Column(db.String(50), unique=True)
    profile_image = db.Column(db.String(200), nullable=True)
    rating = db.Column(db.Integer)
