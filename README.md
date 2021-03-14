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
 - ``!server add <@user>`` - Adds a user to the authentication list. This allows them to register and login. **[Must be sent in Server Chat]**
 - ``!server remove <@user>`` - Removes a user from the authentication list. This deletes their account and which prevents them from logging in. **[Must be sent in Server Chat]**
 - ``!server list`` - Lists all authenticated users and also shows if they are logged in or not. **[Must be sent in Server Chat]**
 - ``!server lock`` [IN PROGRESS] - Temporarily removes authentication from all users & prevents users from logging in until you unlock the server. **[Must be sent in Server Chat]**
 - ``!server unlock`` [IN PROGRESS] - Unlocks the server, users can now login again. **[Must be sent in Server Chat]**
 - ``!server set logchannel`` [IN PROGRESS] - Sets the servers log channel, all user events (such as logins/logouts) will be logged here. Execute this command in the text channel that you want set. **[Must be sent in Server Chat]**
 - ``!server set role <@role>`` [IN PROGRESS] - Sets the servers secure role. This is the role that will be given/revoked from users when they login/logout. **[Must be sent in Server Chat]**

# Q/A

> What happens if I forget my password? - An admin will have to remove and then add you back to the Authentication List.

> What happens if I am in a *secure* voice channel and my authentication expires? - As long as you are in a *secure* voice channel, your authentication will **not** expire. 

# Configuration

LockdownBot uses .env files to help configure the bot.
 - ``TOKEN``: Token for the discord bot.
 - ``URI``: MongoDB URI.
 - ``DATABASE``: Database name
 - ``SERVER_COLLECTION``: Collection for the server information.
 - ``USER_COLLECTION``: Collection for the user information.
 - ``LOGOUT_DELAY``: The amount of minutes that the bot will wait to log a user out.
 - ``PREFIX``: Prefix for the bot, default is ``!``.
