package com.aqupd.jukebox.commands.music;

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
		serverConfig.setGuildSetting(event.getGuild().getId(), "shuffle", "off");
		
	}
}
