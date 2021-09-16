# Application to cast streams to chromecast

## Usage

- Start the server with `python -m flask run`
- It's possible that we need to set an environment variable. Here is the tutorial's instruction:
  1. Set an environment variable for `FLASK_APP`. On Linux and macOS, use `export set FLASK_APP=webapp`; on Windows use `set FLASK_APP=webapp`.
  2. Navigate into the `app` folder, then launch the program using `python -m flask run`.

Also now it only works from the debugger after setting the variable. We need to think how to fix this.
