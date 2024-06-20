package spring.basic.lombok;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HelloLombok {

  private String name = "Hello, lombok";
  private int age = 10;

  public static void main(String[] args) {
    System.out.println(new HelloLombok().getName());
    System.out.println(new HelloLombok().getAge());
    System.out.println(new HelloLombok());
  }
}
