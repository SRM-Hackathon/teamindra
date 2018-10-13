from flask import Flask
import sys
app = Flask(__name__)
from models import db
from gensim.models import KeyedVectors

app.config['SECRET_KEY'] = 'a05e08fbc2d904a43692e593a0f04431'  # to be kept secret
model=KeyedVectors.load_word2vec_format("glove.6B.50d.txt")

try:
    if sys.argv[1] == 'create':
        db.create_all()
except:
    pass

import routes

if __name__ == '__main__':
    app.run(debug=True,port=5000)
