# Script to stream from vlc to chromecast

TODO: Pending having a way of stopping the process.

* `--intf rc`: Use to disable GUI.
* `--no-video`: To stop video output.
* `--sout`: Stream out. For chromecast it needs at least the ip.
* `--demux-filter=demux_chromecast`: Filter needed for vlc to stream to chromecast.
* For chromecast groups, a different port is needed. Use the other script to get all possible casting devices.

## From usb line in using direct show

```
vlc --intf rc --no-video dshow:// vdev="none" adev="Line In (USB AUDIO CODEC)" --sout="#chromecast{ip=192.168.86.31, port=8009}" --demux-filter=demux_chromecast
```

## From file

```
vlc --intf rc --no-video /Downloads/sample.mp3 --sout="#chromecast{ip=192.168.86.27, port=32089}" --demux-filter=demux_chromecast
```
