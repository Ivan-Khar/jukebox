package com.aqupd.jukebox.commands.music;

import com.aqupd.jukebox.Main;
import com.aqupd.jukebox.Utils;
import lavalink.client.LavalinkUtil;
import lavalink.client.io.jda.JdaLavalink;
import lavalink.client.io.jda.JdaLink;
import lavalink.client.player.LavalinkPlayer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@SuppressWarnings("ConstantConditions")
public class PlayCommand extends MusicCategory {

  public PlayCommand() {
    this.name = "play";
    this.help = "plays music";
    this.inVoice = true;
    this.guildOnly = true;
  }

  JdaLavalink lavalink = Main.lavalink;
  @Override
  public void onCommand(MessageReceivedEvent event) {
    event.getMessage().reply("play command").queue();
    JdaLink link = lavalink.getLink(event.getGuild());
    LavalinkPlayer player = link.getPlayer();
    String[] command = event.getMessage().getContentDisplay().split(" ", 2);
    if(command[1].isEmpty()) {
      event.getMessage().reply("you need to type music url/name").queue();
      return;
    }
    link.connect(event.getMember().getVoiceState().getChannel());
    player.playTrack(Utils.getAudioTrack(command[1]).get(0));
  }
}
