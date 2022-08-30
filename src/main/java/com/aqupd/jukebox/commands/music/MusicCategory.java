package com.aqupd.jukebox.commands.music;

import com.aqupd.jukebox.commands.BasicCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class MusicCategory extends BasicCommand {
  protected boolean inVoice = false;
  protected boolean guildOnly = false;

  @Override
  public String getCategory() {
    return "music";
  }

  @Override
  public void execute(MessageReceivedEvent event) {
    if(guildOnly && !event.isFromGuild()) {
      event.getMessage().reply("You need to be in a guild in order to use this command!").queue();
      return;
    }
    if(inVoice && !event.getMember().getVoiceState().inAudioChannel()) {
      event.getMessage().reply("You need to be in a voice channel in order to use this command!").queue();
      return;
    }
    //other checks
    onCommand(event);
  }
}
