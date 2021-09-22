import sounddevice as sd

def discover_inputs():
    devices = sd.query_devices()
    input_devices = [device for device in devices if device['max_input_channels'] > 0]
    input_names = list(set(map(lambda d: d['name'], input_devices)))
    return input_names
