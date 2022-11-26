# InjectionClient

This project was used to learn injecting a "working" hacked client into minecraft

### Only supports 1.18 vanilla version of the game, but feel free to push your changes regarding compatibility

# Working modules
- ESP (Click "O" when injected)
- Hitbox (Click "K" when injected - it is bugged af causes movement problems)

# Commands
- ,help - Sends command list
- ,hitbox - lets you set the hitbox width and height
- ,toggle - toggles a module
- ,panic - disabling client

# How to run
- Compile the project into jar file including all dependencies
- Run your minecraft client (1.18 vanilla only supported)
- Run the jar file using java -jar (jdk 17 or newer)
- Select your minecraft client in the list that was displayed, enter a number containing "net.minecraft.client.main.Main" and click enter
- After 3 - 5 seconds you should see a "InjectClient v0" text in the left upper corner of the game
