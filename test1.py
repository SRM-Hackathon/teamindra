"""
Basically we're going to receive the following from the data initially:

Database_Incoming ===> {name, age, gender, distance, interests, rating}  <=== young people

Database_Incoming ===> {location, interests} <=== old people

 >> gensim function has to be multi-threaded in order to prevent bottle-neck in the fact that initially 20 names has
                                                                                                        to be loaded

 >> once interests have been matched and the profile has common interests we'll move to the matching part

 STEP 1: We need to find the ppl with the required gender and location <======> glean all the matching entries
                                                                                                        within 20kms

 STEP 2: Pass through interest_matcher based on the profiles that are having the common interests, we'll get the
                    profiles that need to be matched <==== over here we can now use multi-threading

 STEP 3: build json of the profiles that have matched and post

"""

from flask import Flask, request
from flask_sqlalchemy import SQLAlchemy
import json
# from flask_marshmallow import Marshmallow
# import sqlite3
# from multiprocessing.dummy import Pool as ThreadPool
# import queue
# from api.util import most_similar
import numpy as np
import math
from gensim.models import KeyedVectors
from main import model
# global model
# from main import initialize_model
# import gensimtest
from __main__ import app
from models import db, Old, Young
#db_old = sqlite3.connect("old.db")  # database for old users
#cursor_old = db_old.cursor()
# global json_string
# global model    
def receive_old(user_id):

    global display_index
    # global imei
    # imei = request.json['imei']  # we need to assign userID to everyone as names may
# be common with other users
    # return json_string
    global json_string
    # global model
    # model = KeyedVectors.load_word2vec_format("glove.6B.50d.txt")
    # gensimtest.search_related_words(model)
    command_old_people = Old.query.filter_by(unique_id=user_id).first()
    old_user_id = command_old_people.unique_id
    old_gender_preference = command_old_people.gender_preference
    old_latitude = command_old_people.latitude
    old_longitude = command_old_people.longitude
    old_interests_list = command_old_people.interests
    # young_userID = [1,2,3,4,5,6,7,8,9,10,11,12]
    # young_gender = ['male','female','male','female','male','female','male','female','male','female','male','female']
    # young_interests = [['algorithm','baking','cooking'],['basketball','baseball','playing cricket'], ['c','c','c'],['d', 'd', 'd'], ['e', 'e', 'e'], ['f', 'f', 'f'], ['g'], ['h'], ['i'], ['j'], ['k'], ['l']]
    # young_ratings = [1,2,3,4,5,6,7,8,9,10,11,12]
    # young_name = ['a','b','c','d','e','f','g','h','i','j','k','l']
    # young_lat = [10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541]
    # young_long = [78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669]
    # young_profile_pic = ['a','b','c','d','e','f','g','h','i','j','k','l']
    young_userID = []
    young_gender = []
    young_interests = []
    young_ratings = []
    young_name = []
    young_lat = []
    young_long = []
    young_profile_pic = []
    for user in Young.query.all():
        if 6371 * math.acos(math.cos(math.radians(old_latitude))*math.cos(math.radians(user.latitude))*math.cos(math.radians(user.longitude) - math.radians(old_longitude))+math.sin(math.radians(old_latitude))*math.sin(math.radians(user.latitude))) < 20:
            young_userID.append(user.unique_id)
            young_gender.append(user.gender)
            young_interests.append(user.interests)
            young_ratings.append(user.ratings)
            young_name.append(user.name)
            young_lat.append(user.latitude)
            young_long.append(user.longitude)
            young_profile_pic.append(user.profile_image)

    display_index = []
    x = 0
    for i in young_gender:
        if i == old_gender_preference: 
            # making a nested list containing necessary items
            young_dist = math.sqrt(pow((old_latitude-young_lat[x]), 2) + pow((old_longitude-young_long[x]), 2))
            display_index.append([young_interests[x], young_userID[x], young_ratings[x], i, young_name[x],
                              young_dist, young_profile_pic[x]])
        # print(young_dist)
        x = x + 1
    # print(display_index)
    # pool = ThreadPool(8)
    interests_list = []
    for i in display_index:
    # print(i[0])
        interests_list.append([i[0], i[1]])  # inserting the interests and user_id of every person into a list
    # print(interests_list)
    # match_queue = queue.Queue(maxsize=20)  # this queue will store the final list of CEs to be displayed

    """Here on out the Gensim processing starts"""

    results = []
    # print(interests_list)
    for i in interests_list:
        results.append(interest_matcher(old_interests_list,old_user_id,i))  # multi-threading interest_matcher
    json_string = json.dumps(results)

    return(json_string)

def interest_matcher(old_interests,old_user_id,young_id_interests):
    """
    This function uses Gensim to find common interests

    Algorithm:
            >> Every individual has 10 interests.
                interests ===> stores the list of interests per individual
                interest ===> an individual interest in the 'interests' list
            >> An interest is considered to match if the
#         young_lat.append(user.latitude)
#         young_long.append(user.longitude)
#         young_profile_pic.append(user.profile_image)
 similarity is greater than or equal to 0.5
            >> if at least 3 interests match, the CE is added to the list
            >> the CEs are ordered in descending order, with most number of matches at the top of the list
            >> the number of matches will be stored in the 'display_index' list as 'score'
            >> the list will be sorted by 'score' and added to the queue
            >> the queue will be jsonified and sent to the app frontend
            >> add the distance and score to sort finally based upon the 2 considered together
    :param interests: list of interests of each individual Companionship Expert
    :return:
    """
    # print(interests)
    # matched_CE = {"user_id":[], "name":[], "gender":[], "distance":[], "profile_pic":[], "interests":[], "rating":[]}
    # index_tracker = 0
    # common_interests = []
    young_tuple = [] # Consists of [salience,young_id]
    for interest in old_interests:
        # print(interest)
        # index_tracker = index_tracker+1
        split_old_interest = interest.split(' ')
        print(split_old_interest)
        for i in young_id_interests:
            maxsmallresult = -1;
            for each_interest in i[0]:
                split_young_interest = each_interest.split(' ')
                result = model.n_similarity(split_old_interest,split_young_interest)
                if result > maxsmallresult:
                    maxsmallresult = result
            young_tuple.append([maxsmallresult,i[1]])
    young_tuple.sort(key=lambda x:x[0],reverse = True)
    result_of_young = []
    result_string = {}
    command_old_people = Old.query.filter_by(unique_id = old_user_id).first()
    for i in range(0,10):
        command_young_people = Young.query.filter_by(unique_id = young_tuple[i]).first()
        young_dist = math.sqrt(pow((command_old_people.latitude-command_young_people.latitude), 2) + pow((command_old_people.longitude-command_young_people.longitude), 2))
        result_string = {"user_id": young_tuple[i], "name": command_young_people.name, "gender": command_young_people.gender, "distance": distance, "profile_pic": command_young_people.profile_pic, "interests":command_young_people.interests, "rating": command_young_people.rating}
        return result_string
    
        
        # print("hello")
        # result = model.n_similarity(positive=split_interests) # need to import model from server once server is initialized inside server
        """
        # print("hey there")
        similarity_list = []
        # print(similarity_list)

        for i in range(0,10):
            similarity_list.append(result[i])
        summ = 0
        print("Similarity list: ",similarity_list)
        for item in similarity_list:
            if item[1] >= 0.5:
                summ = summ+1
        if summ >= 3:
            common_interests.append(interest)
        # print(display_index)
        matched_CE["user_id"].append(str(display_index[index_tracker][1]))
        matched_CE["name"].append(str(display_index[index_tracker][4]))
        matched_CE["gender"].append(str(display_index[index_tracker][3]))
        matched_CE["distance"].append(str(display_index[index_tracker][5]))
        matched_CE["profile_pic"].append(str(display_index[index_tracker][6]))
        matched_CE["interests"].append(str(common_interests))
        matched_CE["rating"].append(str(display_index[index_tracker][2]))
        index_tracker = index_tracker+1
   
    return matched_CE"""

# model = initialize_model.model
# command_old_people = Old.query.filter_by(unique_id=imei).first()

# I get a tuple here containing the required information based on the name received
# row = cursor_old.execute(command_old_people, [imei])

# now based on the old people location, gender_preference and interests we need to find the matching profiles
# old_gender_preference = 'male'
# old_latitude = 10.7673541
# old_longitude = 78.8136669
#db_young = sqlite3.connect("young.db")  # database for companionship experts
#cursor_young = db_young.cursor()

# distance = 6371 * math.acos(math.cos(math.radians(old_latitude))*math.cos(math.radians(latitude))*math.cos(math.radians(longitude) - math.radians(old_longitude))+math.sin(math.radians(old_latitude))*sin(radians(latitude)))
# command_young_people = ('''SELECT imei, gender, interests, rating, name, latitude, longitude, profile_pic
#                             id, (
#                             ) AS distance
#                             FROM Young
#                             HAVING distance<20;
#                             ORDER BY distance
#                             # LIMIT 0, 20;''')
# young_userID = []
# young_gender = []
# young_interests = []
# young_ratings = []
# young_name = []
# young_lat = []
# young_long = []
# young_profile_pic = []
# young_userID = [1,2,3,4,5,6,7,8,9,10,11,12]
# young_gender = ['male','female','male','female','male','female','male','female','male','female','male','female']
# young_interests = [['algos','baking','cooking'],['basketball','baseball','playing cricket'], ['c','c','c'],['d', 'd', 'd'], ['e', 'e', 'e'], ['f', 'f', 'f'], ['g'], ['h'], ['i'], ['j'], ['k'], ['l']]
# young_ratings = [1,2,3,4,5,6,7,8,9,10,11,12]
# young_name = ['a','b','c','d','e','f','g','h','i','j','k','l']
# young_lat = [10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541,10.7673541]
# young_long = [78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669,78.8136669]
# young_profile_pic = ['a','b','c','d','e','f','g','h','i','j','k','l']
# for user in Young.query.all():
#     if 6371 * math.acos(math.cos(math.radians(old_latitude))*math.cos(math.radians(user.latitude))*math.cos(math.radians(user.longitude) - math.radians(old_longitude))+math.sin(math.radians(old_latitude))*math.sin(math.radians(user.latitude))) < 20:
#         young_userID.append(user.unique_id)
#         young_gender.append(user.gender)
#         young_interests.append(user.interests)
#         young_ratings.append(user.ratings)
#         young_name.append(user.name)
#         young_lat.append(user.latitude)
#         young_long.append(user.longitude)
#         young_profile_pic.append(user.profile_image)

# row1 = cursor_young.execute(command_young_people, [old_latitude, old_longitude, old_latitude])
# display_index will store the indexes of the required profiles curated after comparing gender preferences
# display_index = []
# x = 0  # temporary variables to build display_index
# for i in young_gender:
#     if i == old_gender_preference:
#         # making a nested list containing necessary items
#         young_dist = math.sqrt(pow((old_latitude-young_lat[x]), 2) + pow((old_longitude-young_long[x]), 2))
#         display_index.append([young_interests[x], young_userID[x], young_ratings[x], i, young_name[x],
#                               young_dist, young_profile_pic[x]])
#         # print(young_dist)
#     x = x + 1

# pool = ThreadPool(8)
# interests_list = []
# for i in display_index:
#     # print(i[0])
#     interests_list.append([i[0], i[1]])  # inserting the interests and user_id of every person into a list
# # print(interests_list)
# # match_queue = queue.Queue(maxsize=20)  # this queue will store the final list of CEs to be displayed

# """Here on out the Gensim processing starts"""


# # print(interests_list)
# results = pool.map(interest_matcher, interests_list[0])  # multi-threading interest_matcher
# json_string = json.dumps(results)
# receive_old()