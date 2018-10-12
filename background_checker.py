"""
This file performs the facial recognition and verification of users at the login to ensure user identity
This file works using the Microsoft Azure AI + Cognition facial detect and verify API
"""
import requests
import sqlite3
from __main__ import app  # ignore error. Will be resolved at the time of server creation


@app.route('/', methods=['POST'])
def face_verifier():
    # Replace <Subscription Key> with your valid subscription key.
    subscription_key = "1724da272538448f9047f4bd20d3c8e0"
    assert subscription_key
    """
    The following code is to detect faces in input images and to assign facial IDs to the images
    """
    face_api_url_detect = 'https://southeastasia.api.cognitive.microsoft.com/face/v1.0/detect'
    headers_detect = {'Content-Type': 'application/json', 'Ocp-Apim-Subscription-Key': subscription_key}
    params_detect = {
        'returnFaceId': 'true',
        'returnFaceLandmarks': 'false',
        'returnFaceAttributes': 'age,gender,headPose,smile,facialHair,glasses,' +
                                'emotion,hair,makeup,occlusion,accessories,blur,exposure,noise'
    }
    data_detect1 = {'url': 'https://imgur.com/JpzgQaQ.jpg'}
    response_detect1 = requests.post(face_api_url_detect, params=params_detect, headers=headers_detect,
                                     json=data_detect1)
    face_1 = response_detect1.json()
    for face in face_1:
        faceId1 = face['faceId']

    data_detect2 = {'url': 'https://imgur.com/p5gykcX.jpg'}
    response_detect2 = requests.post(face_api_url_detect, params=params_detect, headers=headers_detect,
                                     json=data_detect2)
    face_2 = response_detect2.json()
    for face in face_2:
        faceId2 = face['faceId']
    """
    The following code performs the final verification
    """
    face_api_url = 'https://southeastasia.api.cognitive.microsoft.com/face/v1.0/verify'
    headers = {'Content-Type': 'application/json', 'Ocp-Apim-Subscription-Key': subscription_key}
    data = {
        'faceId1': faceId1,
        'faceId2': faceId2
    }

    response = requests.post(face_api_url, headers=headers, json=data)
    faces = response.json()
    return faces['isIdentical']
