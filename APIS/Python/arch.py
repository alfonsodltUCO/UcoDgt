import cv2
import numpy as np
import easyocr
from flask import Flask, request, jsonify
import base64
from flask import abort
from PIL import Image
from io import BytesIO
import os
import re
import imutils

app = Flask(__name__)

# Ruta para recibir la imagen por POST
@app.route('/checkImage', methods=['POST'])
def detect_plate():
    # Verificar si se recibió un archivo
    data = request.json
    encoded_image = data['image']
    image_data = base64.b64decode(encoded_image)
    # Convertir la imagen de datos en una imagen de PIL
    image_file = Image.open(BytesIO(image_data))
    # Guardar el archivo en disco
    image_path = "temp_img.jpg"
    image_file.save(image_path)

    # Rotar la imagen si es necesario
    image_path_rotated = rotar_imagen(image_path)
    if image_path_rotated:
        image_path = image_path_rotated

    # Leer la imagen de entrada
    img = cv2.imread(image_path)

    # Convertir la imagen de entrada a escala de grises
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    # Leer el archivo de haarcascade para la detección de matrículas
    cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_russian_plate_number.xml')

    # Detectar matrículas
    plates = cascade.detectMultiScale(gray, 1.2, 5)
    print('Número de matrículas detectadas:', len(plates))
    if len(plates) == 0:
        return jsonify({'plate_text': ""})
    # Loop sobre todas las matrículas detectadas
    for (x,y,w,h) in plates:
       
        # Dibujar un rectángulo alrededor de la matrícula
        cv2.rectangle(img, (x,y), (x+w, y+h), (0,255,0), 2)
        gray_plate = gray[y:y+h, x:x+w]
        color_plate = img[y:y+h, x:x+w]

        # Guardar la matrícula detectada
        cv2.imwrite('Matricula.jpg', gray_plate)

        # Crear el objeto Reader de easyOCR
        reader = easyocr.Reader(['en'])

        # Leer el texto en la matrícula
        result = reader.readtext(gray_plate)

        # Iterar sobre los resultados y extraer el texto
        plate_text = ""
        for detection in result:
            plate_text += detection[1] + " "
   
    plate_text = process_plate_text(plate_text)

    if not plate_text[:4].isdigit():
        return jsonify({'plate_text': ""})
    
    if not plate_text[4:].isalpha() or not plate_text[4:].isupper():
        return jsonify({'plate_text': ""})
    
    # Devolver el texto de la matrícula
    return jsonify({'plate_text': plate_text})


# Ruta para recibir la imagen por POST
@app.route('/checkImage2', methods=['POST'])
def detect2_plate():
    data = request.json
    encoded_image = data['image']
    image_data = base64.b64decode(encoded_image)
    # Convertir la imagen de datos en una imagen de PIL
    image_file = Image.open(BytesIO(image_data))
    image_path = "temp_img.jpg"
    image_file.save(image_path)

    # Leer la imagen de entrada
    img = cv2.imread(image_path)

    # Convertir la imagen de entrada a escala de grises
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    # Leer el archivo de haarcascade para la detección de matrículas
    cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_russian_plate_number.xml')

    # Detectar matrículas
    plates = cascade.detectMultiScale(gray, 1.2, 5)
    print('Número de matrículas detectadas:', len(plates))
    if len(plates) == 0:
         abort(400)
   

    # Loop sobre todas las matrículas detectadas
    for (x,y,w,h) in plates:
       
       # Dibujar un rectángulo alrededor de la matrícula
       cv2.rectangle(img, (x,y), (x+w, y+h), (0,255,0), 2)
       gray_plate = gray[y:y+h, x:x+w]
       color_plate = img[y:y+h, x:x+w]

       # Guardar la matrícula detectada
       cv2.imwrite('Matricula.jpg', gray_plate)

       # Crear el objeto Reader de easyOCR
       reader = easyocr.Reader(['en'])

       # Leer el texto en la matrícula
       result = reader.readtext(gray_plate)

       # Iterar sobre los resultados y extraer el texto
       plate_text = ""
       for detection in result:
           plate_text += detection[1] + " "
   
    plate_text = process_plate_text(plate_text)

    if not plate_text[:4].isdigit():
        abort(400)
    
   
    if not plate_text[4:].isalpha() or not plate_text[4:].isupper():
        abort(400)
    
    # Devolver el texto de la matrícula
    return jsonify({'plate_text': plate_text})


@app.route('/checkDni', methods=['POST'])
def detect_dni():
    # Verificar si se recibió un archivo
    data = request.json
    encoded_image = data['image']
    image_data = base64.b64decode(encoded_image)
    # Convertir la imagen de datos en una imagen de PIL
    image_file = Image.open(BytesIO(image_data))
    # Guardar el archivo en disco
    image_path = "dni_temp_img.jpg"
    image_file.save(image_path)

    # Leer la imagen de entrada
    img = cv2.imread(image_path)

    # Convertir la imagen de entrada a escala de grises
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)

    # Inicializar EasyOCR
    reader = easyocr.Reader(['es'])

    # Reconocer texto en la imagen del DNI
    resultados = reader.readtext(gray)
    print(resultados)
    # Expresión regular para encontrar el formato especificado (8 números seguidos de una letra mayúscula)
    regex = re.compile(r'\d{8}[A-Z]')

    # Extraer el texto reconocido y filtrar según la expresión regular
    texto_dni = ""
    for resultado in resultados:
        texto = resultado[1]
        coincidencias = regex.findall(texto)
        if coincidencias:
            texto_dni += coincidencias[0] + " "
    print(texto_dni)
    if not texto_dni:  # Si la cadena está vacía (es decir, tiene longitud cero)
        abort(400)
    else:
        return jsonify({'dni_client': texto_dni.strip()})

def process_plate_text(plate_text2):
    
    patron = re.compile(r'^[A-Za-z]{3}\d{4}$')  # Patrón regex para tres letras seguidas de cuatro números
    text = plate_text2.strip()
    caracteres_a_eliminar = "!@#$%^&*()[]¨}{-_+E"  # Caracteres no deseados
    for caracter in caracteres_a_eliminar:
        text = text.replace(caracter, '')
    text = text.replace(' ', '')  # Elimina los espacios en blanco
    print(text)
    # Verifica si el texto coincide con el patrón regex
    if patron.match(text):
        numeros = text[3:]
        letras = text[:3]
        text = numeros + letras
        return text

    numberOfNumbers = 0
    numberOfLetters = 0

    # Cuenta la cantidad de números y letras en el texto
    for i in text:
        if i.isdigit():
            numberOfNumbers += 1
        if i.isalpha():
            numberOfLetters += 1

    # Si hay 4 números y 3 letras, se considera correcto
    if numberOfNumbers == 4 and numberOfLetters == 3:
        return text
    else:
        # Si hay un número entre las tres últimas posiciones, se cambia por la letra similar
       
        last_three = text[-3:]
        if any(char.isdigit() for char in last_three):
            cad=cambiar_numero_por_letra(last_three)
            text=text[:-3]+cad
            
        numberOfNumbers = 0
        numberOfLetters = 0

        for i in text:
            if i.isdigit():
                numberOfNumbers += 1
            if i.isalpha():
                numberOfLetters += 1
                
        
        if numberOfNumbers >= 5 or (numberOfLetters > 0 and text[0].isalpha()):
            text = text[1:]

    return text



def cambiar_numero_por_letra(texto):
    # Define un diccionario de mapeo de números a letras similares
    numero_a_letra = {
        '0': 'O',
        '1': 'I',
        '2': 'Z',
        '3': 'E',
        '4': 'A',
        '5': 'S',
        '6': 'G',
        '7': 'T',
        '8': 'B',
        '9': 'P'
    }
    
    # Realiza el reemplazo de cada número por la letra correspondiente si existe en el diccionario
    texto_corregido = ''.join(numero_a_letra.get(char, char) for char in texto)
    
    return texto_corregido

def rotar_imagen(image_path):
    # Leer la imagen
    img = cv2.imread(image_path)

    # Convertir la imagen a escala de grises
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    # Detección de bordes en la imagen
    edges = cv2.Canny(gray, 50, 150, apertureSize=3)

    # Detección de líneas utilizando la transformada de Hough
    lines = cv2.HoughLines(edges, 1, np.pi / 180, 100)

    # Calcular el ángulo de rotación
    angulo_rotacion = 0
    if lines is not None:
        for rho, theta in lines[:, 0]:
            if np.degrees(theta) < 90:
                angulo_rotacion = np.degrees(theta) - 90
                break

    # Rotar la imagen
    if angulo_rotacion != 0:
        (h, w) = img.shape[:2]
        centro = (w // 2, h // 2)
        M = cv2.getRotationMatrix2D(centro, angulo_rotacion, 1.0)
        img_rotada = cv2.warpAffine(img, M, (w, h), flags=cv2.INTER_CUBIC, borderMode=cv2.BORDER_REPLICATE)
        
        # Guardar la imagen rotada
        image_path_rotated = 'imagen_rotada.jpg'
        cv2.imwrite(image_path_rotated, img_rotada)
        
        return image_path_rotated
    else:
        return None

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)



