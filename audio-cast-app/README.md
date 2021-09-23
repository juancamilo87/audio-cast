# Application to cast streams to chromecast

*Note: Designed to work with Rpi 4. Any installation process works for the Rpi4.*

## Production installation

### Using pip (Windows/Linux/osX)

This requires to have `python3` installed and `pip` installed. Optionally also `venv`.

1. (Optional) Create virtual environment to avoid dependency issues.
   - Example using venv:
     - Create: `python3 -m venv <DIR>`
     - Start: linux - `source <DIR>/bin/activate` - windows - `<DIR>/Scripts/activate`
     - Stop: `deactivate`
     - To clear delete the directory and create again `rm -r <DIR>`
2. Install package from pip:
   - Linux/osX: `python3 -m pip install audio_cast`
   - Windows: `py -m pip install audio_cast`
3. Install required applications:
   1. Install vlc:
      - Linux/osX has 2 alternatives:
         - Install required libraries:
            - `sudo apt install vlc-bin`
            - `sudo apt install vlc-plugin-base`
         - Install VLC `sudo apt install vlc`
      - Windows: Install vlc from their website.
   2. (Only Linux/osX) Install `libportaudio2`: `sudo apt install libportaudio2`
4. Install a WSGI server.
   - Waitress: `pip install waitress`
5. Run WSGI server.
   - Example: `waitress-serve --call --host=localhost --port=5000 'audio_cast:create_app'`

### Using apt (Linux)

## Dev Usage

- When using VS Code, just run the debugger.
- Optional: Use a virtual environment.
- When starting from termimal.
  1. Set environment variable for `FLASK_APP` to `audio_cast`.
     - Linux/osX: `export set FLASK_APP=audio_cast`
     - Windows cmd: `set FLASK_APP=audio_cast`
     - Windows powershell: `$Env:FLASK_APP = "audio_cast"`
  2. Launch the server with `python -m flask run`.

### Cheatsheet

- To upload to pypi:
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
- Install packages from `requirements.txt` `pip install -r requirements.txt`.

## Next steps

- [ ] Run it in the Rpi
  - [ ] Test if using the full name of the input device returned by the endpoint it works.

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
7. Install PortAudio using apt `sudo apt install libportaudio2`.
