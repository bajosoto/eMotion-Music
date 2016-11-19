from __future__ import print_function
import time 
import requests
import cv2
import operator
import numpy as np
import os


# Import library to display results
import matplotlib.pyplot as plt
#%matplotlib inline 
# Display images within Jupyter

# Variables

_url = 'https://api.projectoxford.ai/emotion/v1.0/recognize'
_key = '6f7ffca280934f8fbecc7df4a74e6556' #Here you have to paste your primary key
_maxNumRetries = 10

def processRequest( json, data, headers, params ):

    """
    Helper function to process the request to Project Oxford

    Parameters:
    json: Used when processing images from its URL. See API Documentation
    data: Used when processing image read from disk. See API Documentation
    headers: Used to pass the key information and the data type request
    """

    retries = 0
    result = None

    while True:

        response = requests.request( 'post', _url, json = json, data = data, headers = headers, params = params )

        if response.status_code == 429: 

            print( "Message: %s" % ( response.json()['error']['message'] ) )

            if retries <= _maxNumRetries: 
                time.sleep(1) 
                retries += 1
                continue
            else: 
                print( 'Error: failed after retrying!' )
                break

        elif response.status_code == 200 or response.status_code == 201:

            if 'content-length' in response.headers and int(response.headers['content-length']) == 0: 
                result = None 
            elif 'content-type' in response.headers and isinstance(response.headers['content-type'], str): 
                if 'application/json' in response.headers['content-type'].lower(): 
                    result = response.json() if response.content else None 
                elif 'image' in response.headers['content-type'].lower(): 
                    result = response.content
        else:
            print( "Error code: %d" % ( response.status_code ) )
            print( "Message: %s" % ( response.json()['error']['message'] ) )

        break
        
    return result

def renderResultOnImage( result, img ):
    
    """Display the obtained results onto the input image"""
    
    for currFace in result:
        faceRectangle = currFace['faceRectangle']
        cv2.rectangle( img,(faceRectangle['left'],faceRectangle['top']),
                           (faceRectangle['left']+faceRectangle['width'], faceRectangle['top'] + faceRectangle['height']),
                       color = (255,0,0), thickness = 5 )


    for currFace in result:
        faceRectangle = currFace['faceRectangle']
        currEmotion = max(currFace['scores'].items(), key=operator.itemgetter(1))[0]


        textToWrite = "%s" % ( currEmotion )
        cv2.putText( img, textToWrite, (faceRectangle['left'],faceRectangle['top']-10), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,0,0), 1 )

while True:
    # Camera 0 is the integrated web cam on my netbook
    camera_port = 0
     
    #Number of frames to throw away while the camera adjusts to light levels
    ramp_frames = 30
     
    # Now we can initialize the camera capture object with the cv2.VideoCapture class.
    # All it needs is the index to a camera port.
    camera = cv2.VideoCapture(camera_port)
     
    # Captures a single image from the camera and returns it in PIL format
    def get_image():
     # read is the easiest way to get a full image out of a VideoCapture object.
     retval, im = camera.read()
     return im
     
    # Ramp the camera - these frames will be discarded and are only used to allow v4l2
    # to adjust light levels, if necessary
    for i in xrange(ramp_frames):
     temp = get_image()
    #print("Taking image...")
    # Take the actual image we want to keep
    camera_capture = get_image()
    file = "/Users/Sergio/Desktop/photoTest.jpg"
    # A nice feature of the imwrite method is that it will automatically choose the
    # correct format based on the file extension you provide. Convenient!
    cv2.imwrite(file, camera_capture)
     
    # You'll want to release the camera, otherwise you won't be able to create a new
    # capture object until your script exits
    del(camera)

    # Load raw image file into memory
    pathToFileInDisk = r'/Users/Sergio/Desktop/photoTest.jpg'
    with open( pathToFileInDisk, 'rb' ) as f:
        data = f.read()
    
    headers = dict()
    headers['Ocp-Apim-Subscription-Key'] = _key
    headers['Content-Type'] = 'application/octet-stream'
    
    json = None
    params = None
    
    #print("Processing request")
    result = processRequest( json, data, headers, params )
    #print("Request processed")
    if result is not None:
        # Load the original image from disk
        data8uint = np.fromstring( data, np.uint8 ) # Convert string to an unsigned int array
        img = cv2.cvtColor( cv2.imdecode( data8uint, cv2.IMREAD_COLOR ), cv2.COLOR_BGR2RGB )
    
        renderResultOnImage( result, img )
    
#        i = 0
        for currFace in result:
            myVar = "%s" % ( max(currFace['scores'].items(), key=operator.itemgetter(1))[0] )
#            if i == 0: 
            name = "Sergio" 
#            else: 
#                name = "Philipp"
            print("%s seems to be experiencing %s" % (name, myVar))
            if myVar == 'happiness':
                os.system("""osascript ./../04_Spotify-scripts/playhappy.applescript""")
            if myVar == 'anger':
                os.system("""osascript ./../04_Spotify-scripts/playangry.applescript""")
            if myVar == 'sadness':
                os.system("""osascript ./../04_Spotify-scripts/playsad.applescript""")
            if myVar == 'surprise':
                os.system("""osascript ./../04_Spotify-scripts/playsurprise.applescript""")
#                os.system("""osascript ./../04_Spotify-scripts/sorin.applescript""")
            with open("Output.txt", "w") as text_file:
                text_file.write("%s" % myVar)
#            print(currFace)
#            i += 1
    
#        ig, ax = plt.subplots(figsize=(15, 20))
#        ax.imshow( img )

    