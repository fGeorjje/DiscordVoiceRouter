# Discord Voice Router

Voice capture tool for [Discord](https://discord.com/) that captures voice channel audio 
through a bot account and routes user audio to
different audio output lines on the locally running machine.

Mostly written for myself for (speedrunning) event production.
Might be useful for other people (sharing is caring), but use at your own risk.

### Requirements

- Java 11+
- Registered application with bot account from
  [Discord Developer Portal](https://discord.com/developers/applications)
- Additional audio output lines
  (I use [VAC](https://vac.muzychenko.net/en/download.htm), 
  USB hardware mixers may also work?)

### Usage

Launch with bot token as argument. Commands through stdin:

- `join [id]` - Join a discord voice channel by ID
- `leave` - Leave voice channel
- `route [user] [device name]` - Add a routed user.
  *\[user\]* supports both ID and display names.
  *\[device name\]* is matched on starting characters
  (you may want to rename your audio lines to a short prefix + number, e.g. Line1/2/etc.)
- `unroute [user]` - Remove a routed user.
  *\[user\]* supports both ID and display names.
- `shutdown` - shuts the bot down. (duh)

### Build Instructions

soon(tm). (maven clean+package)