import json
import requests
import sys



dest = sys.argv[1]


if (dest == "server"):
    url = "http://adaptor.iudx.io:8080/adaptor"
if (dest == "local"):
    url = "http://10.2.104.157/adaptor"


headers = {
  'username': 'testuser',
  'password': 'testuserpassword',
  'Content-Type': 'application/json'
}


response = requests.request("GET", url, headers=headers)
print("Available adaptors are")
print(json.dumps(json.loads(response.text), indent=4))

