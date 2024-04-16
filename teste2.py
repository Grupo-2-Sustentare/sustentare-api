import cv2
from qreader import QReader
 
qreader = QReader()

while True:
    cap = cv2.VideoCapture(0)
    ret,frame = cap.read()

    decoded_text = qreader.detect_and_decode(image=frame)
    print(decoded_text)


    cv2.imshow('Câmera', frame)


    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()

cv2.destroyAllWindows()