package com.aqupd.jukebox;

import com.aqupd.jukebox.audio.QueueManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.aqupd.jukebox.Main.*;

public class Utils {

  public Utils() {}

  public JSONObject getAudioTrack(String track) {
    JSONObject UnirestJsonObject = Unirest.get("http" + (config.isHostSecure()?"s":"") + "://" + config.getHost() + ":" + config.getPort() + "/loadtracks?identifier=" + track)
        .header("Authorization", config.getHostPass()).asJson().getBody().getObject();
    LOGGER.info("first search: \n" + UnirestJsonObject.toString());

    if(UnirestJsonObject.getJSONArray("tracks").isEmpty()) {
      UnirestJsonObject = Unirest.get("http" + (config.isHostSecure()?"s":"") + "://" + config.getHost() + ":" + config.getPort() + "/loadtracks?identifier=ytsearch:" + track)
          .header("Authorization", config.getHostPass()).asJson().getBody().getObject();
      LOGGER.info("second search: \n" + UnirestJsonObject.toString());
    }

    return UnirestJsonObject;
  }

  public QueueManager getQueueForGuild(String guildId) {
    if (!queues.containsKey(guildId)) {
      queues.put(guildId, new QueueManager(guildId));
    }
    return queues.get(guildId);
  }

  public String humanReadableByteCountBin(long bytes) {
    long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
    if (absB < 1024) {
      return bytes + " B";
    }
    long value = absB;
    CharacterIterator ci = new StringCharacterIterator("KMGTPE");
    for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
      value >>= 10;
      ci.next();
    }
    value *= Long.signum(bytes);
    return String.format("%.1f %ciB", value / 1024.0, ci.current());
  }

  public String getTimeFromMS(long ms) {
    int seconds = (int) (ms / 1000) % 60 ;
    int minutes = (int) ((ms / (1000*60)) % 60);
    int hours   = (int) (ms / (1000*60*60));

    String time;
    if(hours > 0)        time = String.format("%d:%02d:%02d", hours, minutes, seconds);
    else if(minutes > 0) time = String.format("%02d:%02d", minutes, seconds);
    else                 time = String.format("00:%02d", seconds);
    return time;
  }

  public String getTrack(AudioTrack track) {
    return "**" + track.getInfo().title + "**";
  }

  public String getTrackWithURI(AudioTrack track) {
    return "[**" + track.getInfo().title + "**](" + track.getInfo().uri + ")";
  }

  public String getTrackWithTime(AudioTrack track) {
    return "`[" + getTimeFromMS(track.getInfo().length) + "]` **" + track.getInfo().title + " - " + track.getInfo().author + "**";
  }

  public String getTrackWithTimeAndURI(AudioTrack track) {
    return "`[" + getTimeFromMS(track.getInfo().length) + "]` [**" + track.getInfo().title + " - " + track.getInfo().author + "**](" + track.getInfo().uri + ")";
  }

  public HashMap<Integer, List<AudioTrack>> getPaginatedListOfTracks(List<AudioTrack> tracks, Integer numOfTracks) {
    HashMap<Integer, List<AudioTrack>> paginatedList = new HashMap<>();
    int tracknum = 0; int pagenum = 0;
    for (AudioTrack track: tracks) {
      if(tracknum % numOfTracks == 0) pagenum++;
      if(!paginatedList.containsKey(pagenum)) paginatedList.put(pagenum, new ArrayList<>());
      paginatedList.get(pagenum).add(track);
      tracknum++;
    }
    return paginatedList;
  }

  public String getTrackPage(List<AudioTrack> tracks, Integer page, Integer pageTracksNumber) {
    StringBuilder sb = new StringBuilder();
    int tracknum = pageTracksNumber * (page-1);
    for (AudioTrack track: tracks) {
      tracknum++;
      sb.append("`").append(tracknum).append(")` ").append(getTrackWithTimeAndURI(track)).append("\n");
    }
    return sb.toString();
  }
}
