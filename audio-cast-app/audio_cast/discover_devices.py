import pychromecast
from prettytable import PrettyTable
from . import utils

utils.createAppDir()

known_devices_name = "known_devices.txt"

def discover_chromecasts():
    # Fetch known devices
    known_devices = ""
    try:
        file = utils.readFile(known_devices_name)
        known_devices = file.read().strip()
        file.close()
    except FileNotFoundError:
        print('No previously known chromecasts')
    known_devices_array = []
    if known_devices:
        known_devices_array = known_devices.split(',')
        print('Previosly known chromecasts:')
        ip_table = PrettyTable()
        ip_table.field_names = ["ip"]
        for device in known_devices_array:
            ip_table.add_row([device])
        print(ip_table)
    # Discover devices
    print('Searching chromecasts...')
    if len(known_devices_array) > 0:
        services, browser = pychromecast.discovery.discover_chromecasts(known_hosts=known_devices_array)
    else:
        services, browser = pychromecast.discovery.discover_chromecasts()
    # Stop discovery
    pychromecast.discovery.stop_discovery(browser)
    # Print chromecasts
    if services:
        ips = []
        chromecast_count = len(services)
        print('Found ' + str(chromecast_count) + ' chromecasts.')
        
        table = PrettyTable()
        table.field_names = ["name", "ip", "port", "uuid", "type"]
        for chromecast in services:
            ip = chromecast.host
            port = chromecast.port
            uuid = str(chromecast.uuid)
            c_type = chromecast.model_name
            name = chromecast.friendly_name
            ips.append(ip)
            table.add_row([name, ip, port, uuid, c_type])

        file = utils.writeFile(known_devices_name)
        file.write(','.join(ips))
        file.close()
        print(table)
        return services
    else:
        print('No devices found')
        return None
