package com.aqupd.jukebox.commands.music;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static com.aqupd.jukebox.Main.*;

public class ShuffleCommand extends MusicCategory {
	
	public ShuffleCommand() {
		this.name = "shuffle";
		this.help = "toggles shuffle mode";
		this.guildOnly = true;
	}

	@Override
	public void onCommand(MessageReceivedEvent event) {
    String guID = event.getGuild().getId();
    Message msg = event.getMessage();
    boolean shuffle = serverConfig.getGuildSetting(guID, "shuffle") != null && serverConfig.getGuildSetting(guID, "shuffle").getAsBoolean();
    if(!shuffle) {
      serverConfig.setGuildSetting(guID, "shuffle", true);
      msg.reply("Shuffle mode: on").queue();
    } else {
      serverConfig.setGuildSetting(guID, "shuffle", false);
	    msg.reply("Shuffle mode: off").queue();
    }
  }
}
