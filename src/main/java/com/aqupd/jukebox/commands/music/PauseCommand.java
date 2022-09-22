package com.aqupd.jukebox.commands.music;

import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.lavaLink;

public class PauseCommand extends MusicCategory {

  public PauseCommand() {
    this.name = "pause";
    this.help = "pauses current track";
    this.guildOnly = true;
    this.playingMusic = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    LavalinkPlayer player = lavaLink.getLink(event.getGuild()).getPlayer();

    if(player.isPaused()) {
      event.getMessage().reply("Player already paused. Use !!play or /play to resume").queue();
      return;
    }

    player.setPaused(true);
    event.getMessage().reply("Player is paused").queue();
  }
}
