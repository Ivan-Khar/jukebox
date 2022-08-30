package com.aqupd.jukebox.commands.general;

import com.aqupd.jukebox.commands.BasicCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class GeneralCategory extends BasicCommand {
  protected boolean guildOnly = false;

  @Override
  public String getCategory() {
    return "general";
  }

  @Override
  public void execute(MessageReceivedEvent event) {
    if(guildOnly && !event.isFromGuild()) {
      event.getMessage().reply("You need to be in a guild in order to use this command!").queue();
      return;
    }
    //other checks
    onCommand(event);
  }
}
