from flask import Flask, Blueprint, Response

from . import discover_devices, discover_media, player

bp = Blueprint('endpoints', __name__)

@bp.route("/discover/chromecasts")
def discover_chromecasts():
    services = discover_devices.discover_chromecasts()
    chromecasts = []

    for chromecast in services or []:
        chromecasts.append({
            "ip": chromecast.host,
            "port": chromecast.port,
            "uuid": str(chromecast.uuid),
            "type": chromecast.model_name,
            "name": chromecast.friendly_name
        })

    return {"devices": chromecasts}

@bp.route("/discover/media")
def discover_media():
    return {"inputs": discover_media.discover_inputs()}

@bp.route("/player/cast/<input>/<ip>/<port>")
def cast(input, ip, port):
    player.play_media_to_chromecast(input, ip, port)
    return Response(status=200)

@bp.route("/player/play")
def play():
    player.play()
    return Response(status=200)

@bp.route("/player/pause")
def play():
    player.pause()
    return Response(status=200)

@bp.route("/player/stop")
def stop():
    player.stop()
    return Response(status=200)