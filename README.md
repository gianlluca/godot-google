# GodotGoogle

A Android Module to add Google login functionaly for Godot Engine 3.2.2 or higher.

**NOTE**: this plugin is for anyone who wants to add Google login functionally without forcing users have the app Google Play Games installed.

Currently, this plugin can gets:
- User ID
- User First Name
- User Display Name
- User Picture Uri

## Setup

1. First of all you need to install and configure the [Android Build Template](https://docs.godotengine.org/en/stable/getting_started/workflow/export/android_custom_build.html)
2. Then you go to the [releases](), choose one and download
3. Extract the content of the ```plugin``` folder to ```res://android/plugins``` on your project directory.
4. Get the ```autoload/GodotGoogle.gd``` file to your project and add it as an Autoload or make your own script to use the Plugin.
    - If you want to make your own script you can get the Plugin like this ```var GooglePlugin = Engine.get_singleton("GodotGoogle")```
5. on Project > Export > Android > Options
    - check the ```Use Custom Build```
    - check the ```Godot Google```
6. the next step is configure your [project](https://developers.google.com/identity/sign-in/android/start-integrating?authuser=1#configure_a_project) on Google Api Console.
    - create or select a project and configure you Android OAuth client.
    - insert your android package name.
    - insert the [sha1 of your keystore](https://developers.google.com/android/guides/client-auth), create the OAuth client and you are ready to go.


## Demo

1. To use the demo open the ```demo``` directory on Godot, install and configure the [Android Build Template](https://docs.godotengine.org/en/stable/getting_started/workflow/export/android_custom_build.html)
2. Then you follow the Setup steps starting from the second and ignoring the fourth.

## Troubleshooting

- First of all make sure that you followed all the steps
- Try use the logcat command on a terminal to know whats is the issue.
```
adb logcat -s godot
```
- If you determine that the issue is with the Plugin feel free to open an issue or a pull request to fix.
