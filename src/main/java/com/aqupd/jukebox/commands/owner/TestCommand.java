package com.aqupd.jukebox.commands.owner;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestCommand extends OwnerCommands {

  public TestCommand() {
    this.name = "test";
    this.help = "test";
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
  }
}
