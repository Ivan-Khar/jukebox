package com.aqupd.jukebox.commands;

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
}
