package com.aqupd.jukebox;

import com.aqupd.jukebox.audio.QueueManager;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

import static com.aqupd.jukebox.Config.*;
import static com.aqupd.jukebox.Main.*;

public class Utils {

  public static JSONObject getAudioTrack(String track) {
    JSONObject UnirestJsonObject = Unirest.get("http" + (isHostSecure()?"s":"") + "://" + getHost() + ":" + getPort() + "/loadtracks?identifier=" + track)
        .header("Authorization", getHostPass()).asJson().getBody().getObject();
    LOGGER.info("first search: \n" + UnirestJsonObject.toString());

    if(UnirestJsonObject.getJSONArray("tracks").isEmpty()) {
      UnirestJsonObject = Unirest.get("http" + (isHostSecure()?"s":"") + "://" + getHost() + ":" + getPort() + "/loadtracks?identifier=ytsearch:" + track)
          .header("Authorization", getHostPass()).asJson().getBody().getObject();
      LOGGER.info("second search: \n" + UnirestJsonObject.toString());
    }

    return UnirestJsonObject;
  }

  public static QueueManager getQueueForGuild(String guildId) {
    if (!queues.containsKey(guildId)) {
      queues.put(guildId, new QueueManager(guildId));
    }
    return queues.get(guildId);
  }
}
