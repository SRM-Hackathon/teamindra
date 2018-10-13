from __main__ import app, db
from flask import jsonify, request
from models import db, Old, Young
from test1 import receive_old
from background_checker import face_verifier

from werkzeug import generate_password_hash, check_password_hash
from random import randint


@app.route('/register/old', methods=['POST'])# endpoint for registration of old people
def register_old():
    name = request.json['name']
    password = request.json['password']
    hashed_password = generate_password_hash(password)
    gender = request.json['gender']
    gender_preference = request.json['gender_preference']
    contact = request.json['contact']
    latitude = request.json['latitude']
    longitude = request.json['longitude']
    interests = request.json['interests']
    rating = randint(1,5)
    unique_id = request.json['unique_id']
    profile_image = request.json['profile_image']
    print(profile_image)
    other_image = request.json['other_image']
    new_user = Old(name=name, password=hashed_password, gender=gender, gender_preference=gender_preference, contact=contact, latitude=latitude,longitude=longitude, interests=''.join(interests), unique_id=unique_id, profile_image=profile_image)
    db.session.add(new_user)
    print ("hello")
    db.session.commit()
    return jsonify({"message": "User registered"})


@app.route('/register/young', methods=['POST']) # TODO: birthdare to be added
 # endpoint for registration of young people
def register_young():
    name = request.json['name']
    password = request.json['password']
    hashed_password = generate_password_hash(password)
    gender = request.json['gender']
    contact = request.json['contact']
    latitude = request.json['latitude']
    longitude = request.json['longitude']
    interests = request.json['interests']
    unique_id = request.json['unique_id']
    profile_image = request.json['profile_image']
    rating = random.randint(1,5)
    check = face_verifier(profile_image,other_image)
    if(check==1):
        new_user = Young(name=name, password=hashed_password, gender=gender, contact=contact, latitude=latitude, longitude=longitude, interests=interests, unique_id=unique_id, profile_image=profile_image, rating=rating)
        db.session.add(new_user)
        db.session.commit()
        # print ("hello")
        return jsonify({"message": "User registered"})
    else :
        return jsonify({"message":"Verification failed"})    

@app.route('/login/old',methods=['POST'])
def login_old():
    contact=request.json['contact']
    user = Old.query.filter_by(contact=contact).first()
    #if (len(user.count())==1):
    if user is not None:
        password = request.json['password']
        if check_password_hash(user.password, password) == True :
            return jsonify({"message": "Login successful","unique_id":user.unique_id,"name":user.name,"gender":user.gender,
                           "gender_preference":user.gender_preference,"profile_image":user.profile_image,"interests":user.interests,
                           "rating":user.rating,"latitude":user.latitude,"longitude":user.longitude,"birthdate":"14-12-1998"})
        else :
            return jsonify({"message":""})

    else :
        return jsonify({"message":""})


@app.route('/appointment/set', methods=['POST'])  # endpoint for setting appointments
def set_appointment():
    appointment = '_'.join(request.json['appointment'].values())  # the json object sent should be like this
    unique_id = request.json['unique_id']
    user = Old.query.filter_by(unique_id=unique_id).first()  # {

    try:  # "appointment":{"name":"name here", "contact":"contact here, "time":"time here"},
        user.appointment += ' + ' + appointment  # "unique_id":"id here"

    except:  # }
        user.appointment = appointment  # maintaining the order of name, contact, and then time is important

    db.session.commit()

    return jsonify({"message": "Appointment set succesfully"})


@app.route('/appointment/get/', methods=['GET'])  # endpoint for retrieving appointments
def get_appointment():
    unique_id = request.args['unique_id']
    user = Old.query.filter_by(unique_id=unique_id).first()

    try:
        details = user.appointment.split(' + ')
        for detail in range(len(details)):
            details[detail] = details[detail].split('_')

        details_dict = {}
        for detail in range(1, len(details) + 1):
            details_dict[detail] = {"name": details[detail - 1][0], "contact": details[detail - 1][1],
                                    "time": details[detail - 1][2]}

        return jsonify(details_dict)

    except:
        details = user.appointment.split('_')

        return jsonify({"name": details[0], "contact": details[1], "time": details[2]})



@app.route('/location/old', methods=['POST'])# endpoint for registration of old people
def location_old():
    latitude = request.json['latitude']
    longitude = request.json['longitude']
    unique_id = request.json['unique_id']
    loc = Old.query.filter_by(unique_id=unique_id).first()
    loc.latitude = latitude
    loc.longitude = longitude
    loc.session.commit()

@app.route('/fetch',methods=['GET','POST'])
def fetch_from_database():
    print("hi")
    unique_id = request.args.get('unique_id')
    print(unique_id)
    bla =  receive_old(unique_id)
    print("hello")
    print(bla)
    return bla


@app.route('/matches', methods=['POST'])# endpoint for registration of old people
def matches_add():
    old_id = request.json['old_id']
    young_id = request.json['young_id']
    match = Matches(old_id=old_id,young_id=young_id)
    db.session.add(match)
    db.session.commit()
    return jsonify({'message':"added to matches list"})




