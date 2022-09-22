package com.aqupd.jukebox.commands.music;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.*;

public class RepeatCommand extends MusicCategory {

  public RepeatCommand() {
    this.name = "repeat";
    this.help = "repeats current track/current queue";
    this.guildOnly = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    String[] command = event.getMessage().getContentDisplay().split(" ", 2);

    if(command.length == 1) {
      if(serverConfig.getGuildSetting(event.getGuild().getId(), "repeat") == null) serverConfig.setGuildSetting(event.getGuild().getId(), "repeat", "off");
      event.getMessage().reply("repeat mode: " + serverConfig.getGuildSetting(event.getGuild().getId(), "repeat")).queue();
      return;
    }

    switch(command[1]) {
      case "off" -> {
        serverConfig.setGuildSetting(event.getGuild().getId(), "repeat", "off");
        event.getMessage().reply("set repeat mode to: off").queue();
      }
      case "on" -> {
        serverConfig.setGuildSetting(event.getGuild().getId(), "repeat", "on");
        event.getMessage().reply("set repeat mode to: on").queue();
      }
      case "single" -> {
        serverConfig.setGuildSetting(event.getGuild().getId(), "repeat", "single");
        event.getMessage().reply("set repeat mode to: single").queue();
      }
      default -> event.getMessage().reply("valid parameters are: on, off, single").queue();
    }
  }
}
