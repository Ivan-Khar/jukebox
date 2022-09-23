package com.aqupd.jukebox;

import com.aqupd.jukebox.audio.QueueManager;
import com.aqupd.jukebox.commands.BasicCommand;
import com.aqupd.jukebox.commands.general.AboutCommand;
import com.aqupd.jukebox.commands.general.HelpCommand;
import com.aqupd.jukebox.commands.general.PingCommand;
import com.aqupd.jukebox.commands.music.*;
import com.aqupd.jukebox.commands.owner.ShutdownCommand;
import com.aqupd.jukebox.commands.owner.TestCommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import lavalink.client.io.jda.JdaLavalink;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

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

  public static JDA jda;
  public static JdaLavalink lavaLink = new JdaLavalink(1, (a) -> jda);
  public static EventWaiter waiter = new EventWaiter();

  public static List<BasicCommand> commandList = new ArrayList<>();
  public static HashMap<String, QueueManager> queues = new HashMap<>();

  public static final Logger LOGGER = LogManager.getLogger("Jukebox");
  public static Config config = new Config();
  public static ServerConfig serverConfig = new ServerConfig();
  public static Utils utils = new Utils();


  public static void main(String[] args) {
    if(!System.getProperty("java.version").contains("18")) { LOGGER.info("Для использования данного бота вам нужно использовать Java 18"); }

    Collections.addAll(commandList,
        //General commands
        new HelpCommand(),
        new PingCommand(),
        new AboutCommand(),

        //Music commands
        new PlayCommand(),
        new StopCommand(),
        new SkipCommand(),
        new RemoveCommand(),
        new ListCommand(),
        new NowPlayingCommand(),
        new PauseCommand(),
        new RepeatCommand(),
        new ShuffleCommand(),
        new VolumeCommand(),

        //Owner commands
        new TestCommand(),
        new ShutdownCommand()
    );
    if(commandList.stream().anyMatch(command -> command.getName() == null || command.getCategory() == null || command.getHelp() == null)) {
      LOGGER.info("Некоторые команды не имеют названия/категории/помощи");
      System.exit(0);
    }
    try {
      jda = JDABuilder.createDefault(config.getToken())
          .enableIntents(Arrays.asList(INTENTS))
          .setVoiceDispatchInterceptor(lavaLink.getVoiceInterceptor())
          .setMemberCachePolicy(MemberCachePolicy.ALL)
          .setChunkingFilter(ChunkingFilter.ALL)
          .enableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE, CacheFlag.ONLINE_STATUS)
          .disableCache(CacheFlag.ACTIVITY)
          .setActivity(Activity.competing("a"))
          .setStatus(OnlineStatus.DO_NOT_DISTURB)
          .addEventListeners(lavaLink, new Listener(), waiter)
          .setBulkDeleteSplittingEnabled(true)
          .setEventPassthrough(true)
          .build()
          .awaitReady();
    } catch (Exception e) {
      LOGGER.trace("Exception! ", e);
    }
  }
}
