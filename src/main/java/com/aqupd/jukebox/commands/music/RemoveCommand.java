package com.aqupd.jukebox.commands.music;

import com.aqupd.jukebox.audio.QueueManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.utils;

public class RemoveCommand extends MusicCategory {

  public RemoveCommand() {
    this.name = "remove";
    this.help = "removes music from queue";
    this.arguments = "[position]";
    this.inVoice = true;
    this.guildOnly = true;
    this.playingMusic = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    String[] command = event.getMessage().getContentDisplay().split(" ", 2);
    QueueManager queue = utils.getQueueForGuild(event.getGuild().getId());
    AudioTrack track;
    if(command.length == 1) {
      track = queue.get().remove(0);
      event.getMessage().reply(String.format("skipped track %1$s", track.getInfo().title)).queue();
    } else {
      int removetrack;
      try {
        removetrack = Integer.parseInt(command[1]);
      } catch (NumberFormatException ex) {
        event.getMessage().reply("You need to type page number").queue();
        return;
      }
      if(removetrack <= queue.get().size() && removetrack > 0) {
        track = queue.get().remove(removetrack-1);
        event.getMessage().reply(String.format("skipped track %1$s", track.getInfo().title)).queue();
      } else {
        event.getMessage().reply("you need to type number from 1 to " + queue.get().size()).queue();
      }
    }
  }
}
