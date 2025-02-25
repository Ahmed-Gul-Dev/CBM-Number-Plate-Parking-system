


def convert_png():
    readpath = 'test_images/'
    number_files = len(os.listdir(readpath))
    print(number_files)
    count = 11
    for filename in os.listdir(readpath):
        print("Image Name : ",filename)
        img = cv2.imread(readpath + filename, 1)  # original
        resized = cv2.resize(img, (640, 480))  # resizing (width , height)
        saved = cv2.imwrite('test_images/{}.png'.format(count), resized)
        count = count + 1

def debug():
    # Read in Image
    img = cv2.imread('test_images/resized_car-3.png')
    resized = cv2.resize(img, (480, 480))  # resizing (width , height)
    cv2.imwrite('test_images/resized_car-3.png', resized)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # Filteration
    bfilter = cv2.bilateralFilter(gray, 11, 17, 17) #Noise reduction
    edged = cv2.Canny(bfilter, 30, 200) #Edge detection

def detection(img):
    width, height = img.shape[1], img.shape[0]
    print("Width : ", width)
    print("Height : ", height)
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    # Filteration
    bfilter = cv2.bilateralFilter(gray, 11, 17, 17) #Noise reduction
    edged = cv2.Canny(bfilter, 30, 200) #Edge detection
    faces = face_cascade.detectMultiScale(bfilter, 1.3, 4)
    for (x,y,w,h) in faces:
            # To draw a rectangle in a face
            cv2.rectangle(img,(x,y),(x+w,y+h),(255,255,0),2)
            face = gray[y:y + h, x:x + w]
            return face

def text_recognition(image):
    # Text Recognition using EasyOCR
    reader = easyocr.Reader(['en'])
    result = reader.readtext(image)
    print(result)
    return result


