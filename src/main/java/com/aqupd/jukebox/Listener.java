package com.aqupd.jukebox;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.ShutdownEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;

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
    String[] args = message.split(" ", 2);

    if(message.startsWith(getPrefix())) {
      event.getMessage().reply("prefixed message no way!!!").queue();
    }
  }

  @Override
  public void onShutdown(@Nonnull ShutdownEvent event) {}
}
