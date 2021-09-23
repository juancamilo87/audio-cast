import click
from audio_cast import discoverer_devices, discoverer_media, player
from prettytable import PrettyTable

@click.group()
def cli():
    """Application to cast audio from an audio input to a chromecast device/group."""

@cli.command()
def discover_chromecasts():
    discoverer_devices.discover_chromecasts()

@cli.command()
def discover_media():
    inputs = discoverer_media.discover_inputs()
    table = PrettyTable()
    table.field_names = ["Input name"]
    for input in inputs:
        table.add_row([input])
    click.echo(table)

@cli.command()
@click.option('--i', required=True, type=str, help='The input device name to cast. Use discover_media if needed.')
@click.option('--c', required=True, type=str, help='The ip of the chromecast. Use discover_chromecasts if needed.')
@click.option('--p', required=True, type=int, help='The port of the chromecast.')
def cast(i, c, p):
    player.play_media_to_chromecast(i, c, p)
    click.echo(f"Casting {i} to {c} on port {p}.")
    click.echo("It may take a couple of seconds until the sound can be heard due to buffering.")

@cli.command()
def stop():
    if player.stop():
        click.echo("Stopping")
    else:
        click.echo("Nothing playing")