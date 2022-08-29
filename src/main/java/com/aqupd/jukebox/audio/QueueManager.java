package com.aqupd.jukebox.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import lavalink.client.player.IPlayer;
import lavalink.client.player.LavalinkPlayer;
import lavalink.client.player.event.PlayerEventListenerAdapter;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.LinkedList;

import static com.aqupd.jukebox.Main.LOGGER;
import static com.aqupd.jukebox.Main.lavalink;

public class QueueManager {

  private final String guild;

  public QueueManager(String guildId) {
    this.guild = guildId;
  }

  private LinkedList<AudioTrack> queue = new LinkedList<>();

  public void add(AudioTrack track, VoiceChannel vc) {
    LavalinkPlayer player = lavalink.getLink(guild).getPlayer();
    if(queue.isEmpty() && player.getPlayingTrack() == null) {
      player.addListener(listener);
      player.playTrack(track);
    } else {
      queue.addLast(track);
    }
    LOGGER.info(queue.toString());
  }

  public void remove(AudioTrack track) {
    if(!queue.isEmpty()) queue.remove(track);
  }

  public void remove(int index) {
    if(!queue.isEmpty()) queue.remove(index);
  }

  public LinkedList<AudioTrack> get() {
    return queue;
  }

  PlayerEventListenerAdapter listener = new PlayerEventListenerAdapter() {
    @Override
    public void onTrackEnd(IPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
      if(!queue.isEmpty()) {
        player.playTrack(queue.removeFirst());
      }
      super.onTrackEnd(player, track, endReason);
    }
  };
}
