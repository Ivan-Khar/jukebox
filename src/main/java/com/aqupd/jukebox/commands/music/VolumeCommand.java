package com.aqupd.jukebox.commands.music;

import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.*;

public class VolumeCommand extends MusicCategory {

  public VolumeCommand() {
    this.name = "volume";
    this.help = "changes volume";
    this.guildOnly = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    LavalinkPlayer player = lavaLink.getLink(event.getGuild()).getPlayer();
    String[] command = event.getMessage().getContentDisplay().split(" ", 2);
    if(command.length == 1) {

      return;
    }
    float vol;
    try {
      vol = Float.parseFloat(command[1]);
    } catch (NumberFormatException ex) {

      return;
    }
    if(vol < 0 || vol >= 2) {

      return;
    }
    serverConfig.setGuildSetting(event.getGuild().getId(), "volume", vol);
    player.getFilters().setVolume(vol).commit();
  }
}
