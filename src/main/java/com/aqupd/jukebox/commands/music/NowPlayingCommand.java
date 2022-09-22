package com.aqupd.jukebox.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.*;

public class NowPlayingCommand extends MusicCategory {

  public NowPlayingCommand() {
    this.name = "nowplaying";
    this.help = "shows current track";
    this.guildOnly = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    LavalinkPlayer player = lavaLink.getLink(event.getGuild()).getPlayer();
    AudioTrack track = player.getPlayingTrack();
    EmbedBuilder eb = new EmbedBuilder();
    if(track == null) {
      eb.setTitle("No music playing");
      eb.setDescription("⏹`▐━━━━━━━━━━━━━━━━━━━━━━━━━▌`"); //┿
      event.getMessage().replyEmbeds(eb.build()).queue();
      return;
    }

    long totalTime = track.getDuration();
    long currentTime = player.getTrackPosition();
    float sliderPos = (float) (currentTime * 100 / totalTime) / 4;
    StringBuilder progressSlider = new StringBuilder("━━━━━━━━━━━━━━━━━━━━━━━━━");
    progressSlider.setCharAt((int) sliderPos, '┿');

    eb.setTitle(utils.getTrack(track), track.getInfo().uri);
    eb.setDescription((player.isPaused()?"⏸️":"▶️") + " `" + utils.getTimeFromMS(currentTime) + "▐" + progressSlider + "▌" + utils.getTimeFromMS(totalTime)  + "`");

    event.getMessage().replyEmbeds(eb.build()).queue();
  }
}
