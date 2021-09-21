# Application to cast streams to chromecast

## Dev Usage

- When using VS Code, just run the debugger.
- Optional: Use a virtual environment.
- When starting from termimal.
  1. Set environment variable for `FLASK_APP` to `audio_cast`.
     - Linux/osX: `export set FLASK_APP=audio_cast`
     - Windows cmd: `set FLASK_APP=audio_cast`
     - Windows powershell: `$Env:FLASK_APP = "audio_cast"`
  2. Launch the server with `python -m flask run`.

## Production installation

1. (Optional) Create virtual environment to avoid dependency issues.
   - Example using venv:
     - Create: `python3 -m venv <DIR>`
     - Start: linux - `source <DIR>/bin/activate` - windows - `<DIR>/Scripts/activate`
     - Stop: `deactivate`
     - To clear delete the directory and create again `rm -r <DIR>`
2. Install package from pip:
   - Linux/osX: `python3 -m pip install audio_cast`
   - Windows: `py -m pip install audio_cast`
3. Install a WSGI server.
   - Waitress: `pip install waitress`
4. Run WSGI server.
   - Example: `waitress-serve --call --host=localhost --port=5000 'audio_cast:create_app'`

## Next steps

- [ ] Run it in the Rpi
  - [ ] Fix sample.mp3 for testing purposes. How to package it? How to access it?
  - [ ] Create directory for application data. Now it is stored where waitress is deployed.
- [ ] Check tutorial to upload package using apt.
  - [Package python for apt](https://monadical.com/posts/how-to-package-python-for-apt-deb.html)
- [ ] Enable CLI
  - [Enabling CLI demo flask project](https://flask.palletsprojects.com/en/2.0.x/cli/)
- [ ] Check how to get inputs in linux using Rpi.
- [ ] Handle instance folder properly. Where should `known_devices.txt` be stored? What about `sample.mp3`?
- [ ] Decouple from full vlc and maybe use libvlc already bundled in the package?

## Cheatsheet

- To upload:
  - Build: `py -m build`
  - Deploy: `py -m twine upload dist/*`
- To run locally:
  - Set environment variable `FLASK_APP=audio_cast`
- To run after installation from package:
  - Install waitress `python3 -m pip install waitress`
  - Run waitress `waitress-serve --call --host=localhost --port=5000 'audio_cast:create_app'`
- Virtual environment:
  - Create: `python3 -m venv <DIR>`
  - Start: linux - `source <DIR>/bin/activate` - windows - `<DIR>/Scripts/activate`
  - Stop: `deactivate`
  - To clear delete the directory and create again `rm -r <DIR>`
- Token for Pypi is in the local projects folder.
- Install packages from `requirements.txt` `pip install -r requirements.txt`.

## Rpi steps

1. Install pip using apt `sudo apt install python3-pip`
2. Install venv using apt `sudo apt install python3-venv`.
3. Create virtual environment and activate it.
4. Install waitress.
5. Run waitress, run it without host so it is exposed.
6. Install vlc `sudo apt install vlc`
   - Hopefully this can be changed to either incorporating the libvlc libraries or installing a part of vlc. Right now it's around 1 gb, which seems too much for the use needed.
   - Or install vlc-bin and vlc-plugin-base. `sudo apt install vlc-bin` and `sudo apt install vlc-plugin-base`. It's smaller but still big. Aroung 700MB.
   - Still there might be a better way.
