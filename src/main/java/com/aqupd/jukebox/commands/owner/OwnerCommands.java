package com.aqupd.jukebox.commands.owner;

import com.aqupd.jukebox.commands.BasicCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

import static com.aqupd.jukebox.Config.getOwners;

public abstract class OwnerCommands extends BasicCommand {

  @Override
  public String getCategory() {
    return "owner";
  }

  @Override
  public void execute(MessageReceivedEvent event) {
    if(Arrays.asList(getOwners()).contains(event.getAuthor().getName())) {
      event.getMessage().reply("you are owner");
    } else {
      event.getMessage().reply("you are not owner");
    }
  }
}