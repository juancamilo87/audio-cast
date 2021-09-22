import os
from os.path import expanduser
home = expanduser("~")
app_data = ".audio_cast"
app_data_dir = f"{home}/{app_data}"

def isWindows():
    return os.name == 'nt'

def readFile(name):
    return open(f"{app_data_dir}/{name}", "r")

def writeFile(name):
    return open(f"{app_data_dir}/{name}", "w")

def createAppDir():
    os.makedirs(app_data_dir, exist_ok=True)