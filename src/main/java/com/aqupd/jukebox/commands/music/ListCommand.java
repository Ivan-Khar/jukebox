package com.aqupd.jukebox.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.aqupd.jukebox.Main.*;

public class ListCommand extends MusicCategory {

  public ListCommand() {
    this.name = "list";
    this.help = "shows queue";
    this.guildOnly = true;
    this.playingMusic = true;
    this.hasQueue = true;
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    List<Button> buttons = new ArrayList<>();
    buttons.add(Button.primary("1", "previous").withEmoji(Emoji.fromFormatted("◀️")).withStyle(ButtonStyle.PRIMARY));
    buttons.add(Button.primary("stop", "stop").withEmoji(Emoji.fromFormatted("⏹️")).withStyle(ButtonStyle.DANGER));
    buttons.add(Button.primary("2", "next").withEmoji(Emoji.fromFormatted("▶️")).withStyle(ButtonStyle.PRIMARY));

    event.getMessage().reply("Loading...").addActionRow(buttons).queue(message -> {
      List<AudioTrack> tracks = queues.get(event.getGuild().getId()).get();
      HashMap<Integer, List<AudioTrack>> paginatedList = utils.getPaginatedListOfTracks(tracks, 10);
      final int[] page = {1};
      updateMessage(event.getGuild(), message, page[0], paginatedList);

      waiter.waitForEvent(ButtonInteractionEvent.class,
        e -> {
          if(e.getUser().isBot() && e.getMessage().getIdLong() != message.getIdLong()) return false;
          if(e.getButton().getId() == null) return false;
          if(e.getMessage().getIdLong() == message.getIdLong()) {
            e.deferEdit().queue();
            switch (e.getButton().getId()) {
              case "stop" -> {
                return true;
              }
              case "1" -> {
                if (page[0] > 1) page[0]--;
                updateMessage(event.getGuild(), message, page[0], paginatedList);
                return false;
              }
              case "2" -> {
                if (page[0] < paginatedList.size()) page[0]++;
                updateMessage(event.getGuild(), message, page[0], paginatedList);
                return false;
              }
            }
          }
          return false;
        },
        e -> e.getMessage().editMessageComponents(Collections.emptyList()).queue(),
        1, TimeUnit.MINUTES,
        () -> message.editMessageComponents(Collections.emptyList()).queue());
    });
  }

  private void updateMessage(Guild guild, Message message, Integer page, HashMap<Integer, List<AudioTrack>> paginatedList) {
    EmbedBuilder builder = new EmbedBuilder();
    builder.setDescription(utils.getTrackPage(paginatedList.get(page), page, 10));
    builder.setFooter("Page " + page + "/" + paginatedList.size());
    message.editMessageEmbeds(builder.build()).queue(response -> {
      String header = headerBuilder(guild);
      if(!response.getContentDisplay().equals(header)) message.editMessage(headerBuilder(guild)).queue();
    });
  }

  private String headerBuilder(Guild guild) {
    AudioTrack currentPlaying = lavaLink.getLink(guild).getPlayer().getPlayingTrack();
    if(currentPlaying == null) return "Not playing";
    return String.format("Playing: %s", utils.getTrackWithTime(currentPlaying));
  }
}
