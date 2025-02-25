# https://www.hackster.io/ahmedibrrahim/iot-using-raspberry-pi-and-firebase-and-android-dbe61d

import pyrebase

np = 'BQS-305'
config = {
  "apiKey": "AIzaSyDFmZc0gH_mUkb7hXrqDZ0Zsdai1AJq420",
  "authDomain": "anpr-fyp.firebaseapp.com",
  "databaseURL": "https://anpr-fyp-default-rtdb.firebaseio.com/",
  "storageBucket": "anpr-fyp.appspot.com"
}

firebase = pyrebase.initialize_app(config)
db = firebase.database()
db.child('FromPi')

# data = {'NumberPlate' : np , 'scanning':'stop'}
# db.set(data) #write to firebase
# print('Success')

value = db.child('scanning').get().val() #read from firebase
# value = value.val()
print(value)
