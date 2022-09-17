package com.aqupd.jukebox.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nullable;

public abstract class BasicCommand {
  protected String name = null;
  protected String help = null;
  @Nullable
  protected String arguments = null;

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
