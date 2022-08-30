package com.aqupd.jukebox.commands.music;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class StopCommand extends MusicCategory {

  public StopCommand() {
    this.name = "stop";
    this.help = "stops music";
    this.inVoice = true;
    this.guildOnly = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    event.getMessage().reply("stop command").queue();
  }
}
