package com.aqupd.jukebox;

import com.aqupd.jukebox.commands.BasicCommand;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;

import static com.aqupd.jukebox.Config.*;
import static com.aqupd.jukebox.Main.*;
import static com.aqupd.jukebox.ServerConfig.*;

@SuppressWarnings("SameParameterValue")
public class Listener extends ListenerAdapter {
  @Override
  public void onReady(@Nonnull ReadyEvent event) {
    lavaLink.setUserId(event.getJDA().getSelfUser().getId());
    try {
      lavaLink.addNode(new URI("ws://" + getHost() + ":" + getPort()), getHostPass());
    } catch (URISyntaxException e) {
      LOGGER.error("error while parsing host URI", e);
    }
  }

  @Override
  public void onMessageReceived(MessageReceivedEvent event) {
    String message = event.getMessage().getContentDisplay();
    if(message.startsWith(getPrefix())) {
      String[] args = message.split(" ", 2);
      args[0] = args[0].replace(getPrefix(), "");
      for(BasicCommand command: commandList) {
        if(command.getName().equals(args[0])) command.execute(event);
      }
    }
  }

  @Override
  public void onGuildJoin(@NotNull GuildJoinEvent event) {
    if(getGuildSetting(event.getGuild().getId(), "lang") == null) setGuildSetting(event.getGuild().getId(), "lang", "en");
  }

  @Override
  public void onShutdown(@Nonnull ShutdownEvent event) {}
}
