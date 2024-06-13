package spring.basic.singleton;

public class StatefulService {

  private int price; //상태를 유지하는 필드.
  //싱글톤 객체는 항상 무상태(Stateless)로 설계해야 한다.

  public void order(String name, int price) {
    System.out.printf("name = %s, price = %d%n", name, price);
    this.price = price; //여기가 문제
  }

  public int getPrice() {
    return price;
  }
}
