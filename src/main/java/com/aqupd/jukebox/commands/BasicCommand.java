package com.aqupd.jukebox.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class BasicCommand {
  protected String name; protected String help; protected String category;

  public String getName() {
    return name;
  }

  public String getHelp() {
    return help;
  }

  public String getCategory() {
    return category;
  }

  public void execute(MessageReceivedEvent event) {}
}
