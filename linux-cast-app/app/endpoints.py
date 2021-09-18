from flask import Flask

from . import app, discover_devices, player


@app.route("/discover")
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
    player.play_to_chromecast(ip, port)
    return f"Success to {ip} and {port}??"

@app.route("/test")
def teste():
    player.test()
    return "Something"

@app.route("/play")
def play():
    player.play()
    return "All good"

@app.route("/stop")
def stop():
    player.stop()
    return "All good 1"
