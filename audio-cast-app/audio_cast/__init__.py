import os

from flask import Flask

def create_app(test_config=None):
    app = Flask(__name__, instance_relative_config=True)

    try:
        os.makedirs(app.instance_path)
    except OSError:
        pass

    from audio_cast import endpoints

    app.register_blueprint(endpoints.bp)

    return app
