package github.com.farukonder.mule4.log4m;


/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class LoggingConnection {

  private final String id;

  public LoggingConnection(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void invalidate() {
    // do something to invalidate this connection!
  }
}
