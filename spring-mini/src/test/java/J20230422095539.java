import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.Car;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * PropertyPlaceholderConfigurer
 * <a href='project\_20230421141647\src\test\java\J20230422095539.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422095539 {

    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:property-placeholder-configurer.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }

}
