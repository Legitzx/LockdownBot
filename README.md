# LockdownBot
Secures Discord Servers by requiring a password to view the content. X amount of minutes after loging in, your authentication will be revoked. At this point, you must login again. 

# How it works

## Step 1
Register with the LockdownBot. Direct message the LockdownBot ``!register <password>``. Replace the ``<password>`` with your desired password. 

## Step 2
Once you have registered with the bot, you are ready to view the content within the server. You will be given the *Authenticated* role and will be able to access channels that have that role added.

# User Commands
 - ``!register <password`` - Registers you with the bot. [Must be sent in Direct Messages]
 - ``!logout`` - Logs you out of the current server. [Must be sent in Server Chat]

# Admin Commands
 - ``!list`` - Lists all the users who are currently authenticated. [Must be sent in Server Chat]
 - ``!deauth`` <@user> - Removes authentication from a user. [Must be sent in Server Chat]
 - ``!selfdestruct`` - Removes authentication from all users & prevents users from authenticating. [Must be sent in Server Chat]
