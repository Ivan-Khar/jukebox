package com.aqupd.jukebox;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import lavalink.client.LavalinkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.aqupd.jukebox.Config.*;
import static com.aqupd.jukebox.Main.LOGGER;

public class Utils {
  public static List<AudioTrack> getAudioTrack(String track) {
    JSONObject UnirestJsonObject = Unirest.get("http" + (isHostSecure()?"s":"") + "://" + getHost() + ":" + getPort() + "/loadtracks?identifier=" + track)
        .header("Authorization", getHostPass()).asJson().getBody().getObject();

    LOGGER.info(UnirestJsonObject.toString());
    ArrayList<AudioTrack> list = new ArrayList<>();
    UnirestJsonObject.getJSONArray("tracks").forEach(je -> {
      JSONObject jo = (JSONObject) je;
      try {
        list.add(LavalinkUtil.toAudioTrack(jo.getString("track")));
      } catch (IOException e) {
        LOGGER.trace("Exception while getting track info", e);
      }
    });
    return list;
  }
}
