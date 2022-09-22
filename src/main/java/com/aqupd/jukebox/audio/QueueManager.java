package com.aqupd.jukebox.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lavalink.client.player.IPlayer;
import lavalink.client.player.LavalinkPlayer;
import lavalink.client.player.event.PlayerEventListenerAdapter;

import java.util.ArrayList;

import static com.aqupd.jukebox.Main.*;

public class QueueManager {

  private final String guild;

  public QueueManager(String guildId) {
    this.guild = guildId;
  }

  private ArrayList<AudioTrack> queue = new ArrayList<>();

  public void add(AudioTrack track) {
    LavalinkPlayer player = lavaLink.getLink(guild).getPlayer();
    if(queue.isEmpty() && player.getPlayingTrack() == null) {
      player.addListener(listener);
      player.playTrack(track);
    } else {
      queue.add(track);
    }
  }

  public ArrayList<AudioTrack> get() {
    return queue;
  }

  PlayerEventListenerAdapter listener = new PlayerEventListenerAdapter() {
    @Override
    public void onTrackEnd(IPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
      if(!queue.isEmpty()) {
        String repeat = serverConfig.getGuildSetting(guild, "repeat");
        if(repeat != null && repeat.equals("single")) queue.add(0, track);
        player.playTrack(queue.remove(0));
        if(repeat != null && repeat.equals("on")) {
          queue.add(track);
        }
      } else {
        lavaLink.getLink(guild).destroy();
        queues.remove(guild);
      }

      super.onTrackEnd(player, track, endReason);
    }
  };
}
