import vlc

# Line in
# vlc --intf rc --no-video dshow:// vdev="none" adev="Line In (USB AUDIO CODEC)" --sout="#chromecast{ip=192.168.86.31, port=8009}" --demux-filter=demux_chromecast
# From file
# vlc --intf rc --no-video /Downloads/sample.mp3 --sout="#chromecast{ip=192.168.86.27, port=32089}" --demux-filter=demux_chromecast
instance = None
player = None
mp3_file = "app/sample.mp3"

def play():
    m = vlc.Instance.media_new(get_instance(), mp3_file)
    stop()
    global player
    player = vlc.Media.player_new_from_media(m)
    vlc.MediaPlayer.play(player)
    player.play()

def play_to_chromecast(chromecast_ip, port):
    m = vlc.Instance.media_new(get_instance(), mp3_file)
    m.add_option(f":sout=#chromecast{{ip={chromecast_ip}, port={port}}}")
    m.add_option(f":demux-filter=demux_chromecast")
    stop()
    global player
    player = vlc.Media.player_new_from_media(m)
    vlc.MediaPlayer.play(player)
    
def test():
    print("This is a test")

def stop():
    global player
    if player:
        player.stop()
        player.release()
        player = None

def get_instance():
    global instance
    if instance is None:
        instance = vlc.Instance()
    return instance
