package com.aqupd.jukebox.commands.owner;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.*;

public class ShutdownCommand extends OwnerCommands {

  public ShutdownCommand() {
    this.name = "shutdown";
    this.help = "shutdowns the bot";
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    event.getMessage().reply("Goodbye!").complete();
    lavaLink.shutdown();
    jda.shutdown();
    System.exit(0);
  }
}
