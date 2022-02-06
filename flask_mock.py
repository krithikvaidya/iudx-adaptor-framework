from flask import Flask, jsonify
import random
from datetime import datetime

app = Flask(__name__)

a = 1

@app.get('/auth/simpleA')
def hello():

    global a
    a *= -1

    dt = datetime.now() 
    tt = dt.timetuple()
    t = ['0' for i in range(6)]
    for i in range(6):

        if (tt[i] < 10):
            t[i] = '0' + str(tt[i])
        else:
            t[i] = str(tt[i])

    time=str(t[0]) + '-' + str(t[1]) + '-' + str(t[2]) + 'T' + str(t[3]) + ':' + str(t[4]) + ':' + str(t[5]) + 'Z'

    if (a > 0):
        return jsonify(
            deviceId="abc-123",
            k1=random.randrange(1000),
            time=time
        )
    else:
        return jsonify(
            deviceId="abc-456",
            k1=random.randrange(1000),
            time=time
        )