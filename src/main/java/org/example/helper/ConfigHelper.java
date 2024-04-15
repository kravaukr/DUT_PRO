package org.example.helper;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public abstract class ConfigHelper {
  public static final Config config = ConfigFactory.load();
  public static final String profile = config.getString("profiles.active");
  public static final Config appConfig = config.getConfig("profiles." + profile);
  public static final int port = config.getInt("akka.http.server.default-http-port");
}