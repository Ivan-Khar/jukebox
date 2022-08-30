package com.aqupd.jukebox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.*;

import static com.aqupd.jukebox.Main.LOGGER;

public class ServerConfig {

  private ServerConfig() {}
  public static final ServerConfig INSTANCE = new ServerConfig();
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private final File confFile = new File("./serversettings.json");
  private JsonObject settings = new JsonObject();

  public static String getGuildSetting(String guildId, String setting) {
    try {
      return INSTANCE.settings.get(guildId).getAsJsonObject().get(setting).getAsString();
    } catch(NullPointerException ex) {
      return null;
    }
  }

  public static void setGuildSetting(String guildId, String setting, String value) {
    if(!INSTANCE.settings.has(guildId)) INSTANCE.settings.add(guildId, new JsonObject());
    INSTANCE.settings.get(guildId).getAsJsonObject().add(setting, new JsonPrimitive(value));
    INSTANCE.save();
  }

  public void load() {
    if (!confFile.exists() || confFile.length() == 0) save();
    try {
      settings = gson.fromJson(new FileReader(confFile), JsonObject.class);
      save();
    } catch (FileNotFoundException ex) {
      LOGGER.trace("Conf. file not found (strange)", ex);
    }
  }

  public void save() {
    try {
      if (!confFile.exists()) confFile.createNewFile();
      if (confFile.exists()) {

        PrintWriter printwriter = new PrintWriter(new FileWriter(confFile));
        printwriter.print(gson.toJson(settings));
        printwriter.close();
      }
    } catch (IOException ex) {
      LOGGER.trace("Got problems saving conf. file", ex);
    }
  }
}
