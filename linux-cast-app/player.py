import vlc

# Line in
# vlc --intf rc --no-video dshow:// vdev="none" adev="Line In (USB AUDIO CODEC)" --sout="#chromecast{ip=192.168.86.31, port=8009}" --demux-filter=demux_chromecast
# From file
# vlc --intf rc --no-video /Downloads/sample.mp3 --sout="#chromecast{ip=192.168.86.27, port=32089}" --demux-filter=demux_chromecast
instance = None
player = None

def play():
    m = getInstance().media_new("sample.mp3")
    stop()
    global player
    player = m.player_new_from_media()
    player.play()

def stop():
    if player:
        player.stop()

def getInstance():
    global instance
    if instance is None:
        instance = vlc.Instance()
    return instance
    