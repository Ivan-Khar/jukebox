package com.aqupd.jukebox.commands.music;

import com.aqupd.jukebox.commands.BasicCommand;

public class MusicCommand extends BasicCommand {
  public MusicCommand() {
    this.name = "play";
    this.help = "plays music";
    this.category = "music";
  }
}
