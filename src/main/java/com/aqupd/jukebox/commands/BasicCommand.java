package com.aqupd.jukebox.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class BasicCommand {
  protected String name = "";
  protected String help = "";

  public String getName() {
    return name;
  }

  public String getHelp() {
    return help;
  }

  public abstract String getCategory();

  public abstract void execute(MessageReceivedEvent event);

  public abstract void onCommand(MessageReceivedEvent event);
}
