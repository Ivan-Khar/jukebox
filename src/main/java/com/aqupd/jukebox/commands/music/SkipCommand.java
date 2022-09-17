package com.aqupd.jukebox.commands.music;

import lavalink.client.io.jda.JdaLink;
import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.lavaLink;

public class SkipCommand extends MusicCategory {

  public SkipCommand() {
    this.name = "skip";
    this.help = "skips current music";
    this.inVoice = true;
    this.guildOnly = true;
    this.playingMusic = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    JdaLink link = lavaLink.getLink(event.getGuild());
    LavalinkPlayer player = link.getPlayer();
    //String[] command = event.getMessage().getContentDisplay().split(" ", 2);
    String musicName = link.getPlayer().getPlayingTrack().getInfo().title;
    event.getMessage().reply(String.format("skipped track %s", musicName)).queue();
    link.getPlayer().stopTrack();
  }
}
