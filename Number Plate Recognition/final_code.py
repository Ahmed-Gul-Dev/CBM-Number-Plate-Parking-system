import cv2
import numpy as np
import easyocr
import imutils


# vid = cv2.VideoCapture(1)
# # while (True):
# ret , img = vid.read()
# img = imutils.resize(img , width=640,height=480)
# #     cv2.imshow('frame', frame)
# #     if cv2.waitKey(1) & 0xFF == ord('q'):
# #         break
# # vid.release()
# # cv2.destroyAllWindows()

# # Read an Image
img = cv2.imread('detected_images-contour/5.jpg')
width, height = img.shape[1], img.shape[0]
print("Width : ", width)
print("Height : ", height)

if width > 660 and height > 500:
    img = cv2.resize(img, (640, 480))  # resizing (width , height)

# cv2.imwrite("test_images/1.png",img);
gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
# cv2.imshow("Original" , img)

# Filteration
bfilter = cv2.bilateralFilter(gray, 11, 17, 17) #Noise reduction
edged = cv2.Canny(bfilter, 30, 200) #Edge detection
cv2.imshow("Filter" , bfilter)

# Finding Contours
keypoints = cv2.findContours(edged.copy(), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
contours = imutils.grab_contours(keypoints)
contours = sorted(contours, key=cv2.contourArea, reverse=True)[:10]
location = None
for contour in contours:
    approx = cv2.approxPolyDP(contour, 10, True)
    if len(approx) == 4:
        location = approx
        break

# Masking
mask = np.zeros(gray.shape, np.uint8)
new_image = cv2.drawContours(mask, [location], 0,255, -1)
new_image = cv2.bitwise_and(img, img, mask=mask)
# cv2.imshow("Masked" , new_image)

(x,y) = np.where(mask==255)
(x1, y1) = (np.min(x), np.min(y))
(x2, y2) = (np.max(x), np.max(y))
cropped_image = gray[x1:x2+1, y1:y2+1]
cv2.imshow("Cropped" , cropped_image)

# Text Detection and Recognition
reader = easyocr.Reader(['en'])
result = reader.readtext(cropped_image)
print(result)

text = result[0][1]
print("Result ", text)

# font = cv2.FONT_HERSHEY_SIMPLEX
# res = cv2.putText(img, text=text, org=(approx[0][0][0], approx[1][0][1]+60), fontFace=font, fontScale=1, color=(0,255,0), thickness=2, lineType=cv2.LINE_AA)
res = cv2.rectangle(img, tuple(approx[0][0]), tuple(approx[2][0]), (0,255,0),3)
cv2.imshow("Original" , img)


cv2.waitKey(0)
cv2.destroyAllWindows()
