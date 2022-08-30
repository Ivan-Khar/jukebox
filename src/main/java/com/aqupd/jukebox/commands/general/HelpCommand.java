package com.aqupd.jukebox.commands.general;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Config.getOwners;
import static com.aqupd.jukebox.Main.commandList;

public class HelpCommand extends GeneralCategory {

  public HelpCommand() {
    this.name = "help";
    this.help = "returns help";
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    StringBuilder sb = new StringBuilder();
    sb.append("Command list: \n");
    commandList.forEach(com ->
        sb.append(com.getCategory()).append(" ")
            .append(com.getName()).append(" ")
            .append(com.getHelp()).append("\n"));

    event.getAuthor().openPrivateChannel().flatMap(channel -> channel.sendMessage(sb)).queue();
  }
}
