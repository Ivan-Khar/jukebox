package com.aqupd.jukebox;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  public static final Permission[] RECOMMENDED_PERMS = {
      Permission.MESSAGE_SEND,
      Permission.MESSAGE_SEND_IN_THREADS,
      Permission.CREATE_PUBLIC_THREADS,
      Permission.MESSAGE_HISTORY,
      Permission.MESSAGE_ADD_REACTION,
      Permission.MESSAGE_EMBED_LINKS,
      Permission.MESSAGE_ATTACH_FILES,
      Permission.MESSAGE_MANAGE,
      Permission.MESSAGE_EXT_EMOJI,
      Permission.MANAGE_CHANNEL,
      Permission.VOICE_CONNECT,
      Permission.VOICE_SPEAK,
      Permission.NICKNAME_CHANGE
  };
  public static final GatewayIntent[] INTENTS = {
      GatewayIntent.MESSAGE_CONTENT,
      GatewayIntent.GUILD_PRESENCES,
      GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
      GatewayIntent.DIRECT_MESSAGES,
      GatewayIntent.GUILD_MESSAGES,
      GatewayIntent.GUILD_MESSAGE_REACTIONS,
      GatewayIntent.GUILD_VOICE_STATES,
      GatewayIntent.GUILD_MEMBERS
  };

  public static final Logger LOGGER = LogManager.getLogger("Jukebox");

  public static void main(String[] args) {
    if(!System.getProperty("java.version").equals("17")) {
      LOGGER.info("Для использования данного бота вам нужно использовать Java 17");
    }
  }
}