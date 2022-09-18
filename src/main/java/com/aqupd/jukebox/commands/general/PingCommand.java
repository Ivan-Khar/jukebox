package com.aqupd.jukebox.commands.general;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand extends GeneralCategory {

  public PingCommand() {
    this.name = "ping";
    this.help = "returns ping to other services";
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    int ping = -1;

    StringBuilder sb = new StringBuilder();
    //sb.append(String.format("Ping to lavalink node: %1$dms\n", ping));
    sb.append("Pong!");
    event.getMessage().reply(sb).queue();
  }
}
