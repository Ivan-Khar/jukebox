package com.aqupd.jukebox.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static com.aqupd.jukebox.Main.*;

public class QueueManager {

  private HashMap<String, List<AudioTrack>> queue = new HashMap<>();

  public void addToQueue(AudioTrack track, VoiceChannel vc) {
    if(!queue.containsKey(vc.getId())) queue.put(vc.getId(), new LinkedList<>());
    queue.get(vc.getId()).add(track);
    LOGGER.info(queue.toString());
  }
}
