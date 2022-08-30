package com.aqupd.jukebox.commands.music;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PlayCommand extends MusicCategory {

  public PlayCommand() {
    this.name = "play";
    this.help = "plays music";
    this.inVoice = true;
    this.guildOnly = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    event.getMessage().reply("play command").queue();
  }
}
