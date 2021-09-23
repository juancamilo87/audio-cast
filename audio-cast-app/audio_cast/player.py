import vlc

from . import utils

_instance: vlc.Instance = None
player: vlc.MediaPlayer = None

def play_media_to_chromecast(input, chromecast_ip, port):
    m: vlc.Media
    if utils.isWindows(): # Windows path uses dshow
        m = instance().media_new_location("dshow://")
        m.add_option(f":adev={input}")
        m.add_option(":dshow-vdev=None")
    else: # Linux path uses alsa
        alsa_input = utils.get_alsa_input(input)
        m = instance().media_new_location(f"alsa://plug{alsa_input}")
    m.add_option(f":sout=#chromecast{{ip={chromecast_ip}, port={port}}}")
    m.add_option(f":demux-filter=demux_chromecast")

    stop()
    global player
    player = m.player_new_from_media()
    player.play()

def pause():
    if player:
        player.pause()

def play():
    if player:
        player.play()

def stop():
    global player
    if player:
        player.stop()
        player.release()
        player = None
        return True
    else:
        return False

def instance():
    global _instance
    if _instance is None:
        # _instance = vlc.Instance("--verbose 9")
        _instance = vlc.Instance()
    return _instance
