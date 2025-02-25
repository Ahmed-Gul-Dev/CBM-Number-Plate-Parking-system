# Import Libraries
import cv2
import os
import numpy as np
import easyocr
import imutils

print(' *******  Number Plate Detection and Recognition.  *******')
face_cascade= cv2.CascadeClassifier("haarcascade_russian_plate_number.xml")

# # Video Streaming
# vid = cv2.VideoCapture(0)
# # vid.set(640)
# # vid.set(480)
#
# while (True):
#     ret, frame = vid.read()
#     frame = imutils.resize(frame , width=640,height=480)
#     cv2.imshow('frame', frame)
#     if cv2.waitKey(1) & 0xFF == ord('q'):
#         break
# vid.release()
# cv2.destroyAllWindows()
# detected_images-cascade


# Read in Image
img = cv2.imread('test_images/1.png')
# # resized = cv2.resize(img, (480, 480))  # resizing (width , height)
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
#
# # Filteration
# width, height = img.shape[1], img.shape[0]
# print("Width : ", width)
# print("Height : ", height)
# bfilter = cv2.bilateralFilter(gray, 11, 17, 17) #Noise reduction
# edged = cv2.Canny(bfilter, 30, 200) #Edge detection
#
# Detection
faces = face_cascade.detectMultiScale(img, 1.1, 4)
for (x,y,w,h) in faces:
    # To draw a rectangle in a face
    cv2.rectangle(img,(x,y),(x+w,y+h),(255,255,0),2)
    face = gray[y:y + h, x:x + w]

cv2.imshow("Detected" , img)

#
# # Text Recognition using EasyOCR
# # reader = easyocr.Reader(['en'])
# # result = reader.readtext(img)
# # print(result)
# #
# # text = result[0][-2]
# # print(text)
#
cv2.waitKey(0)
cv2.destroyAllWindows()