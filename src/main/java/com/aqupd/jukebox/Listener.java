package com.aqupd.jukebox;

import com.aqupd.jukebox.audio.QueueManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import lavalink.client.LavalinkUtil;
import lavalink.client.io.jda.JdaLink;
import lavalink.client.player.IPlayer;
import net.dv8tion.jda.api.entities.AudioChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.aqupd.jukebox.Config.*;
import static com.aqupd.jukebox.Main.*;

@SuppressWarnings("SameParameterValue")
public class Listener extends ListenerAdapter {
  @Override
  public void onReady(@Nonnull ReadyEvent event) {
    lavalink.setUserId(event.getJDA().getSelfUser().getId());
    try {
      lavalink.addNode(new URI("ws://" + getHost() + ":" + getPort()), getHostPass());
    } catch (URISyntaxException e) {
      LOGGER.error("error while parsing host URI", e);
    }
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    String message = event.getMessage().getContentDisplay();
    VoiceChannel vc = jda.getVoiceChannelById(getVC());
    JdaLink link = lavalink.getLink(vc.getGuild());
    link.connect(vc);
    IPlayer player = link.getPlayer();
    switch (message.substring(0, message.indexOf(" "))) {
      case "sht" -> {
        link.destroy();
        lavalink.shutdown();
        jda.shutdown();
        System.exit(0);
      }
      case "play" -> {
        if(event.getMember().getVoiceState().inAudioChannel()) {
          VoiceChannel playvoice = jda.getVoiceChannelById(event.getMember().getVoiceState().getChannel().getId());
          queueManager.addToQueue(getAudioTrack("ytsearch:" + message.substring(message.indexOf(" "))).get(0), playvoice);
        }
      }
      case "np" -> LOGGER.info(link.getPlayer().getTrackPosition());
    }
  }

  @Override
  public void onShutdown(@Nonnull ShutdownEvent event) {}

  private List<AudioTrack> getAudioTrack(String track) {
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
