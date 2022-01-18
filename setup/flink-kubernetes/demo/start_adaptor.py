import json
import requests
import sys


idd = sys.argv[1]
dest = sys.argv[2]


if (dest == "server"):
    url = "http://adaptor.iudx.io:8080/adaptor/xx/start"
if (dest == "local"):
    url = "http://10.2.104.157/adaptor/xx/start"


url = url.replace("xx", idd)

headers = {
  'username': 'testuser',
  'password': 'testuserpassword',
  'Content-Type': 'application/json'
}


response = requests.request("POST", url, headers=headers)
print("Available adaptors are")
print(json.dumps(json.loads(response.text), indent=4))
