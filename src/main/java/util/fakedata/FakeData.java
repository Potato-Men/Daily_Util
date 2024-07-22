package util.fakedata;

import com.github.javafaker.Faker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * 做假数据，更多api在下面的网址中寻找
 * https://github.com/DiUS/java-faker
 * https://github.com/DiUS/java-faker(备份)
 */
public class FakeData {
    public static void main(String[] args) {

        Faker faker = new Faker(Locale.CHINA);        //创建JavaFaker对象
        Random random = new Random();

        // 框定某个变量的范围
        List<String> storeID = Arrays.asList("1001", "1002", "1003", "Li", "Fang");
        List<String> firstNames = Arrays.asList("陈旭", "张三");

        for (int i = 0; i < 10; i++) {
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            String name = faker.name().fullName(); // Miss Samanta Schmidt
//            String firstName = faker.name().firstName(); // Emory
            String lastName = faker.name().lastName(); // Barton
            String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449
            int randomNumber = faker.number().numberBetween(10, 20);

            System.out.println("name = " + name + "  firstName = " + firstName + "  lastName = " + lastName + "  streetAddress = " + streetAddress + "  randomNumber = " + randomNumber);

            String csvLine = String.format("%s,%s,%s,%s,%d", name, firstName, lastName, streetAddress, randomNumber);

            // 将生成的随机结果写入CSV文件
//            try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\xchan\\Desktop\\fake_data.csv", true))) {
//                writer.write(csvLine);
//                writer.newLine();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    }
}
