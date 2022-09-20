package com.aqupd.jukebox.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

import static com.aqupd.jukebox.Main.*;
import static com.aqupd.jukebox.Utils.*;

public class ListCommand extends MusicCategory {

  public ListCommand() {
    this.name = "list";
    this.help = "shows queue";
    this.guildOnly = true;
    this.playingMusic = true;
    this.hasQueue = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    event.getMessage().reply("Loading...").queue(message -> {
      List<AudioTrack> tracks = queues.get(event.getGuild().getId()).get();
      AudioTrack currentPlaying = lavaLink.getLink(event.getGuild()).getPlayer().getPlayingTrack();

      message.editMessage(String.format("Playing: %s", getTrackWithTime(currentPlaying))).queue();
      EmbedBuilder builder = new EmbedBuilder();

      StringBuilder sb = new StringBuilder();
      int tracknum = 1;
      for (AudioTrack track: tracks) {
        if(tracknum > 10) break;
        sb.append(String.format("`%1$d)` %2$s\n", tracknum, getTrackWithTimeAndURI(track)));
        tracknum++;
      }
      builder.setDescription(sb);
      builder.setFooter("Page 1/1");
      message.editMessageEmbeds(builder.build()).queue();
          //tracks.size()
    });
  }
}
