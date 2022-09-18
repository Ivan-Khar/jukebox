package com.aqupd.jukebox.commands.music;

import com.aqupd.jukebox.audio.QueueManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import kong.unirest.json.JSONObject;
import lavalink.client.LavalinkUtil;
import lavalink.client.io.jda.JdaLink;
import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

import static com.aqupd.jukebox.Main.LOGGER;
import static com.aqupd.jukebox.Main.lavaLink;
import static com.aqupd.jukebox.Utils.getAudioTrack;
import static com.aqupd.jukebox.Utils.getQueueForGuild;
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
    if(command.length == 1) {
      event.getMessage().reply("you need to type music url/name").queue();
      return;
    }

    QueueManager queue = getQueueForGuild(event.getGuild().getId());

    link.connect(event.getMember().getVoiceState().getChannel());
    JSONObject results = getAudioTrack(command[1]);
    switch(results.getString("loadType")) {
      case "NO_MATCHES" -> event.getMessage().reply("Nothing found").queue();
      case "SEARCH_RESULT", "TRACK_LOADED" -> {
        try {
          AudioTrack track = LavalinkUtil.toAudioTrack(results.getJSONArray("tracks").getJSONObject(0).getString("track"));
          queue.add(track);
          event.getMessage().reply(String.format("playing track %1$s", track.getInfo().title)).queue();
        } catch(IOException e) {
          event.getMessage().reply("Couldn't get track info").queue();
          LOGGER.error("Retrieving Audio Info error", e);
        }
      }
      case "PLAYLIST_LOADED" -> {
        results.getJSONArray("tracks").forEach(jo -> {
          try {
            JSONObject trackInfo = ((JSONObject) jo);
            AudioTrack track = LavalinkUtil.toAudioTrack(trackInfo.getString("track"));
            queue.add(track);
          } catch(IOException e) {
            event.getMessage().reply("Couldn't get track info").queue();
            LOGGER.error("Retrieving audio info error", e);
          }
        });
        String playlistName = results.getJSONObject("playlistInfo").getString("name");
        int playlistCount = results.getJSONArray("tracks").length();
        event.getMessage().reply(String.format("Playing playlist %1$s with %2$d entries", playlistName, playlistCount)).queue();
      }
      case "LOAD_FAILED" -> {
        String exception = results.getJSONObject("exception").getString("message");
        event.getMessage().reply(String.format("Couldn't play this audio track. %1$s", exception)).queue();
        LOGGER.error(String.format("got error while trying to play audio track in guild \"%1$s\". arg: \"%2$s\", error: \"%3$s\"", event.getGuild().getId(), command[1], exception));
      }
    }
  }
}
