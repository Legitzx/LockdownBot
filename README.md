# LockdownBot
Secures Discord Servers by requiring a password to view the content. When you login, you will only have X amount of minutes to view the server before your authentication expires. Then you will have to relogin to view the server.

# How it works

## Step 1
Register with the LockdownBot. Direct message the LockdownBot ``!register <password>``. Replace the ``<password>`` with your desired password. 

## Step 2
Once you have registered with the bot, you are ready to view the content within the server. You will be given the *Authenticated* role and will be able to access channels that have that role added.

# Commands

## User Commands
 - ``!register <password>`` - Registers you with the bot. [Must be sent in Direct Messages]
 - ``!login <password>`` - Logs you in, **must be registered.** [Must be sent in Direct Messages]
 - ``!logout`` - Logs you out of the current server. [Must be sent in Server Chat]

> Well what happens if I forgot my password? - IDK

## Admin Commands
 - ``!kill <@user>`` - Adds user to a blacklist, this restricts them from loging in. [Must be sent in Server Chat]
 - ``!revive <@user>`` - Removes user from blacklist. [Must be sent in Server Chat]
 - ``!server lock`` - Removes authentication from all users & prevents users from authenticating. [Must be sent in Server Chat]
 - ``!server unlock`` - Unlocks the server, users can now login again. [Must be sent in Server Chat]
