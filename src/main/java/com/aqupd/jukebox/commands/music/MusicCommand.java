package com.aqupd.jukebox.commands.music;

import com.aqupd.jukebox.commands.BasicCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MusicCommand extends BasicCommand {
  public MusicCommand() {
    this.name = "play";
    this.help = "plays music";
    this.category = "music";
  }

  @Override
  public void execute(MessageReceivedEvent event){
    event.getMessage().reply("play command").queue();
  }
}
