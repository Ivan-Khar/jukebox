package com.aqupd.jukebox;

import com.google.gson.*;

import java.io.*;

import static com.aqupd.jukebox.Main.LOGGER;

@SuppressWarnings({"FieldMayBeFinal", "ResultOfMethodCallIgnored", "FieldCanBeLocal", "unused"})
public class Config {

  private Config() {}
  public static final Config INSTANCE = new Config();

  Gson gson = new GsonBuilder().setPrettyPrinting().create();

  private String TOKEN = "bot_token";
  private long VC = 0L;
  private String HOST = "lavalink_node_ip";
  private String PORT = "lavalink_node_port";
  private String HOST_PASS = "lavalink_node_password";
  private String[] OWNERS = {"459442554623098882", "1"};
  private boolean HOST_SECURE = false;
  private String PREFIX = "!!";

  private final File confFile = new File("./config.json");

  public static String getToken() { return INSTANCE.TOKEN; }
  public static long getVC() { return INSTANCE.VC; }
  public static String getHost() { return INSTANCE.HOST; }
  public static String getPort() { return INSTANCE.PORT; }
  public static String getHostPass() { return INSTANCE.HOST_PASS; }
  public static boolean isHostSecure() { return INSTANCE.HOST_SECURE; }
  public static String getPrefix() { return INSTANCE.PREFIX; }
  public static String[] getOwners() { return INSTANCE.OWNERS;}

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
      if ((jE = jo.get("owners")) != null) OWNERS = gson.fromJson(jE.getAsJsonArray(), String[].class);
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
