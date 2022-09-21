package com.aqupd.jukebox;

import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.aqupd.jukebox.Main.LOGGER;

@SuppressWarnings({"FieldMayBeFinal", "ResultOfMethodCallIgnored", "FieldCanBeLocal", "unused"})
public class Config {

  public Config() {}

  Gson gson = new GsonBuilder().setPrettyPrinting().create();

  private String TOKEN = "bot_token";
  private long VC = 0L;
  private String HOST = "lavalink_node_ip";
  private String PORT = "lavalink_node_port";
  private String HOST_PASS = "lavalink_node_password";
  private List<String> OWNERS = new ArrayList<>() {{add("459442554623098882"); add("1");}};
  private boolean HOST_SECURE = false;
  private String PREFIX = "!!";

  private final File confFile = new File("./config.json");

  public String getToken() { return TOKEN; }
  public long getVC() { return VC; }
  public String getHost() { return HOST; }
  public String getPort() { return PORT; }
  public String getHostPass() { return HOST_PASS; }
  public boolean isHostSecure() { return HOST_SECURE; }
  public String getPrefix() { return PREFIX; }
  public List<String> getOwners() { return OWNERS;}

  public void load() {
    if (!confFile.exists() || confFile.length() == 0) save();
    try {
      JsonObject jo = gson.fromJson(new FileReader(confFile), JsonObject.class);
      JsonElement jE;
      if ((jE = jo.get("token")) != null) TOKEN = jE.getAsString();
      if ((jE = jo.get("voice_chat")) != null) VC = jE.getAsLong();
      if ((jE = jo.get("host")) != null) HOST = jE.getAsString();
      if ((jE = jo.get("port")) != null) PORT = jE.getAsString();
      if ((jE = jo.get("host_pass")) != null) HOST_PASS = jE.getAsString();
      if ((jE = jo.get("host_secure")) != null) HOST_SECURE = jE.getAsBoolean();
      if ((jE = jo.get("prefix")) != null) PREFIX = jE.getAsString();
      if ((jE = jo.get("owners")) != null) {
        List<String> newOWNERS = new ArrayList<>();
        jE.getAsJsonArray().forEach(bl -> newOWNERS.add(bl.getAsString().toLowerCase()));
        OWNERS = newOWNERS;
      }
      save();
    } catch (FileNotFoundException ex) {
      LOGGER.trace("Conf. file not found (strange)", ex);
    }
  }

  public void save() {
    try {
      if (!confFile.exists()) confFile.createNewFile();
      if (confFile.exists()) {
        JsonObject jo = new JsonObject();
        jo.add("token", new JsonPrimitive(TOKEN));
        jo.add("voice_chat", new JsonPrimitive(VC));
        jo.add("host", new JsonPrimitive(HOST));
        jo.add("port", new JsonPrimitive(PORT));
        jo.add("host_pass", new JsonPrimitive(HOST_PASS));
        jo.add("host_secure", new JsonPrimitive(HOST_SECURE));
        jo.add("prefix", new JsonPrimitive(PREFIX));

        JsonArray owners = new JsonArray();
        for(String owner: OWNERS) { owners.add(owner); }
        jo.add("owners", owners);

        PrintWriter printwriter = new PrintWriter(new FileWriter(confFile));
        printwriter.print(gson.toJson(jo));
        printwriter.close();
      }
    } catch (IOException ex) {
      LOGGER.trace("Got problems saving conf. file", ex);
    }
  }
}
