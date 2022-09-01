package com.aqupd.jukebox.commands.owner;

import com.aqupd.jukebox.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class ShutdownCommand extends OwnerCommands {

  public ShutdownCommand() {
    this.name = "shutdown";
    this.help = "shutdowns the bot";
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    event.getMessage().reply("Goodbye!").delay(1, TimeUnit.SECONDS).flatMap(f -> {
      Main.lavalink.shutdown();
      Main.jda.shutdown();
      System.exit(0);
      return null;
    }).queue();
  }
}
