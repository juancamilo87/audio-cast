import vlc

# Line in
# vlc --intf rc --no-video dshow:// vdev="none" adev="Line In (USB AUDIO CODEC)" --sout="#chromecast{ip=192.168.86.31, port=8009}" --demux-filter=demux_chromecast
# From file
# vlc --intf rc --no-video /Downloads/sample.mp3 --sout="#chromecast{ip=192.168.86.27, port=32089}" --demux-filter=demux_chromecast
_instance: vlc.Instance = None
player: vlc.MediaPlayer = None
mp3_file = "audio_cast/sample.mp3"

def play():
    m: vlc.Media = instance().media_new(mp3_file)
    stop()
    global player
    player = m.player_new_from_media()
    player.play()

def play_to_chromecast(chromecast_ip, port):
    m: vlc.Media = instance().media_new(mp3_file)
    m.add_option(f":sout=#chromecast{{ip={chromecast_ip}, port={port}}}")
    m.add_option(f":demux-filter=demux_chromecast")
    stop()
    global player
    player = m.player_new_from_media()
    player.play()

def play_to_chromecast2(input, chromecast_ip, port):
    m: vlc.Media = instance().media_new_location("dshow://")
    m.add_option(f":adev={input}")
    m.add_option(":dshow-vdev=None")
    m.add_option(f":sout=#chromecast{{ip={chromecast_ip}, port={port}}}")
    m.add_option(f":demux-filter=demux_chromecast")
    stop()
    global player
    player = m.player_new_from_media()
    player.play()
    
def test():
    print("This is a test")

def stop():
    global player
    if player:
        player.stop()
        player.release()
        player = None

def instance():
    global _instance
    if _instance is None:
        _instance = vlc.Instance()
    return _instance
