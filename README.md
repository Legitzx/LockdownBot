# LockdownBot
Secures Discord Servers by requiring a password to view the channels. When you login, you will only have X amount of minutes to view the server before your authentication expires. Then you will have to relogin to view the server.

# How it works

## Step 1
Have an admin add you to the Authentication List. This will allow you to register with the LockdownBot.

## Step 2
Register with the LockdownBot. Direct message the LockdownBot ``!register <password>``. Replace the ``<password>`` with your desired password. 

## Step 3
Once you have registered with the bot, you are ready to login and view the content within the server. Once you have logged in, you will be given the *Authenticated* role and will be able to access channels that have that role added.

# Commands

## User Commands
 - ``!register <password>`` - Registers you with the bot. **[Must be sent in Direct Messages]**
 - ``!login <password>`` - Logs you in, **must be registered.** **[Must be sent in Direct Messages]**
 - ``!logout`` - Logs you out of the current server. **[Must be sent in Server Chat]**

## Admin Commands
 - ``!server add <@models>`` - Adds a models to the authentication list. This allows them to register and login. **[Must be sent in Server Chat]**
 - ``!server remove <@models>`` - Removes a models from the authentication list. This deletes their account and which prevents them from logging in. **[Must be sent in Server Chat]**
 - ``!server list`` - Lists all authenticated users and also shows if they are logged in or not. **[Must be sent in Server Chat]**
 - ``!server lock`` - Temporarily removes authentication from all users & prevents users from logging in until you unlock the server. **[Must be sent in Server Chat]**
 - ``!server unlock`` - Unlocks the server, users can now login again. **[Must be sent in Server Chat]**

# Q/A

> What happens if I forget my password? - An admin will have to remove and then add you back to the Authentication List.

> What happens if I am in a *secure* voice channel and my authentication expires? - As long as you are in a *secure* voice channel, your authentication will **not** expire. 
