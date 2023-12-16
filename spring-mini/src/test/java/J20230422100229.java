import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.Car;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Value和@Autowire
 * <a href='project\_20230421141647\src\test\java\J20230422100229.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422100229 {

    @Test
    public void testValueAnnotation() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:value-annotation.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }

}
