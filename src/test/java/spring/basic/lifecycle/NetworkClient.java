package spring.basic.lifecycle;

public class NetworkClient {

  private String url;

  public NetworkClient() {
    System.out.printf("생성자 호출, url = %s%n", url);
  }

  public void setUrl(String url) {
    this.url = url;
  }

  //서비스 시작 시 호출
  public void connect() {
    System.out.printf("connected: %s%n", url);
  }

  public void call(String message) {
    System.out.printf("call: %s, message: %s%n", url, message);
  }

  public void disconnect() {
    System.out.printf("closed: %s%n", url);
  }

  public void init() {
    System.out.println("Network.init()");
    connect();
    call("initialization success");
  }

  public void close() {
    disconnect();
  }
}
