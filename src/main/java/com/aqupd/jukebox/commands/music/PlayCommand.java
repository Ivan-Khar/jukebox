package com.aqupd.jukebox.commands.music;

import com.aqupd.jukebox.audio.QueueManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kong.unirest.json.JSONObject;
import lavalink.client.LavalinkUtil;
import lavalink.client.io.jda.JdaLink;
import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.aqupd.jukebox.Main.*;

@SuppressWarnings("ConstantConditions")
public class PlayCommand extends MusicCategory {

  public PlayCommand() {
    this.name = "play";
    this.help = "plays music";
    this.inVoice = true;
    this.guildOnly = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    JdaLink link = lavaLink.getLink(event.getGuild());
    LavalinkPlayer player = link.getPlayer();
    String[] command = event.getMessage().getContentDisplay().split(" ", 2);
    String guID = event.getGuild().getId();
    if(command.length == 1) {
      if(player.getPlayingTrack() != null && player.isPaused()) {
        player.setPaused(false);
        event.getMessage().reply("resumed playing of the track").queue();
        return;
      }
      event.getMessage().reply("you need to type music url/name").queue();
      return;
    }

    boolean shuffle = serverConfig.getGuildSetting(guID, "shuffle") != null && serverConfig.getGuildSetting(guID, "shuffle").getAsBoolean();
    QueueManager queue = utils.getQueueForGuild(guID);
    link.connect(event.getMember().getVoiceState().getChannel());
    JSONObject results = utils.getAudioTrack(command[1]);

    switch(results.getString("loadType")) {
      case "NO_MATCHES" -> event.getMessage().reply("Nothing found").queue();
      case "SEARCH_RESULT", "TRACK_LOADED" -> {
        try {
          AudioTrack track = LavalinkUtil.toAudioTrack(results.getJSONArray("tracks").getJSONObject(0).getString("track"));
          if(shuffle) queue.addRandom(track);
          else queue.add(track);
          event.getMessage().reply(String.format("playing track %1$s", track.getInfo().title)).queue();
        } catch(IOException e) {
          event.getMessage().reply("Couldn't get track info").queue();
          LOGGER.error("Retrieving Audio Info error", e);
        }
      }
      case "PLAYLIST_LOADED" -> {
        List<AudioTrack> tracks = new ArrayList<>();
        results.getJSONArray("tracks").forEach(o -> {
          try {
            String trackInfo = ((JSONObject) o).getString("track");
            tracks.add(LavalinkUtil.toAudioTrack(trackInfo));
          } catch (IOException e) {
            LOGGER.error("Retrieving audio info error", e);
          }
        });

        if(shuffle) Collections.shuffle(tracks);
        tracks.forEach(queue::add);

        String playlistName = results.getJSONObject("playlistInfo").getString("name");
        int playlistCount = results.getJSONArray("tracks").length();
        event.getMessage().reply(String.format("Playing playlist %1$s with %2$d entries", playlistName, playlistCount)).queue();
      }
      case "LOAD_FAILED" -> {
        String exception = results.getJSONObject("exception").getString("message");
        event.getMessage().reply(String.format("Couldn't play this audio track. %1$s", exception)).queue();
        LOGGER.error(String.format("got error while trying to play audio track in guild \"%1$s\". arg: \"%2$s\", error: \"%3$s\"", guID, command[1], exception));
      }
    }
  }
}
