package com.aqupd.jukebox.commands.music;

import com.aqupd.jukebox.audio.QueueManager;
import lavalink.client.io.jda.JdaLink;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.*;
import static com.aqupd.jukebox.Utils.getQueueForGuild;

public class StopCommand extends MusicCategory {

  public StopCommand() {
    this.name = "stop";
    this.help = "stops music";
    this.inVoice = true;
    this.guildOnly = true;
    this.playingMusic = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    JdaLink link = lavaLink.getLink(event.getGuild());
    QueueManager queue = getQueueForGuild(event.getGuild().getId());
    queue.get().clear();
    link.getPlayer().stopTrack();
    event.getMessage().reply("Cleared the queue and left the voice chat").queue();
  }
}
