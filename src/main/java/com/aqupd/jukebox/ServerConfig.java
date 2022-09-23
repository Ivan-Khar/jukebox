package com.aqupd.jukebox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import javax.annotation.Nullable;
import java.io.*;

import static com.aqupd.jukebox.Main.LOGGER;

public class ServerConfig {

  public ServerConfig() {
    load();
  }

  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private final File confFile = new File("./serversettings.json");
  private JsonObject settings = new JsonObject();

  @Nullable
  public JsonPrimitive getGuildSetting(String guildId, String setting) {
    try {
      return settings.get(guildId).getAsJsonObject().get(setting).getAsJsonPrimitive();
    } catch(NullPointerException ex) {
      return null;
    }
  }

  public void setGuildSetting(String guildId, String setting, String value) {
    if(!settings.has(guildId)) settings.add(guildId, new JsonObject());
    settings.get(guildId).getAsJsonObject().add(setting, new JsonPrimitive(value));
    save();
  }
  public void setGuildSetting(String guildId, String setting, Boolean value) {
    if(!settings.has(guildId)) settings.add(guildId, new JsonObject());
    settings.get(guildId).getAsJsonObject().add(setting, new JsonPrimitive(value));
    save();
  }
  public void setGuildSetting(String guildId, String setting, Number value) {
    if(!settings.has(guildId)) settings.add(guildId, new JsonObject());
    settings.get(guildId).getAsJsonObject().add(setting, new JsonPrimitive(value));
    save();
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
