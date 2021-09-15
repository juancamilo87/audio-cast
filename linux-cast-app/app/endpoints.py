from flask import Flask

from . import app, discover_devices, player


@app.route("/")
def home():
    services = discover_devices.discover_chromecasts()
    chromecasts = []

    for chromecast in services:
        chromecasts.append({
            "ip": chromecast.host,
            "port": chromecast.port,
            "uuid": str(chromecast.uuid),
            "type": chromecast.model_name,
            "name": chromecast.friendly_name
        })

    return {"devices": chromecasts}

@app.route("/cast/<ip>/<port>")
def cast(ip, port):
    return "Whatever"

@app.route("/play")
def play():
    player.play()
    return "All good"

@app.route("/stop")
def stop():
    player.stop()
    return "All good 1"
