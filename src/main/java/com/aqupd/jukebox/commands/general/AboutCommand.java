package com.aqupd.jukebox.commands.general;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AboutCommand extends GeneralCategory {

  public AboutCommand() {
    this.name = "about";
    this.help = "info about the bot";
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {

    event.getMessage().reply("").queue();
  }
}
