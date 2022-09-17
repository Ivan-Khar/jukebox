package com.aqupd.jukebox.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lavalink.client.player.IPlayer;
import lavalink.client.player.LavalinkPlayer;
import lavalink.client.player.event.PlayerEventListenerAdapter;

import java.util.ArrayList;

import static com.aqupd.jukebox.Main.LOGGER;
import static com.aqupd.jukebox.Main.lavaLink;

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
    LOGGER.info(queue.toString());
  }

  public ArrayList<AudioTrack> get() {
    return queue;
  }

  PlayerEventListenerAdapter listener = new PlayerEventListenerAdapter() {
    @Override
    public void onTrackEnd(IPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
      if(!queue.isEmpty()) player.playTrack(queue.remove(0));
      else lavaLink.getLink(guild).destroy();

      super.onTrackEnd(player, track, endReason);
    }
  };
}
