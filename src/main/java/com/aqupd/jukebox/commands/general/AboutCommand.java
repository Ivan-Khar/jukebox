package com.aqupd.jukebox.commands.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

import static com.aqupd.jukebox.Main.*;

public class AboutCommand extends GeneralCategory {

  public AboutCommand() {
    this.name = "about";
    this.help = "info about the bot";
  }

  @Override
  public void onCommand(MessageReceivedEvent event) {
    String usedMem = utils.humanReadableByteCountBin(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());


    String stats1 = String.format("**%1$d** guilds\n**%2$s** ram", jda.getGuilds().size(), usedMem);
    String stats2 = String.format("**%1$d** users\n**%2$d** unique", jda.getGuilds().stream().mapToInt(g -> g.getMembers().size()).sum(), jda.getUsers().size());
    String stats3 = String.format("**%1$d** text\n**%2$d** voice", jda.getTextChannels().size(), jda.getVoiceChannels().size());

    EmbedBuilder builder = new EmbedBuilder();
    builder.setTitle("About me:")
        .setDescription("""
            I am jukebox. Music bot made by [this guy](https://github.com/Ivan-Khar).
            You can use `/help` or `!!help` for commands.
            Use `/lang` or `!!lang` in order to change bot's language.
            """)
        .setColor(Color.decode("#00BB9C"))
        .setTimestamp(Instant.now())
        .setImage("https://cdn.discordapp.com/attachments/906861038367019018/1021802283564011530/ezgif.com-gif-maker3.gif")
        .setFooter(jda.getSelfUser().getName(), jda.getSelfUser().getAvatarUrl())
        .addField("**Stats:**", stats1, true)
        .addField("**Users**", stats2, true)
        .addField("**Channels**", stats3, true);

    if(lavaLink.getNodes().get(0).getStats() != null) {
      String lavalinkUsedMem = utils.humanReadableByteCountBin(lavaLink.getNodes().get(0).getStats().getMemFree());
      String stats4 = String.format("**%1$,.2f**%% cpu\n**%2$s** ram", lavaLink.getNodes().get(0).getStats().getSystemLoad(), lavalinkUsedMem);
      builder.addField("**Lavalink node**", stats4, true);
    }
    event.getMessage().replyEmbeds(builder.build()).queue();
  }
}
