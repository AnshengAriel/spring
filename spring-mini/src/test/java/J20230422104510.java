import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.Car;

import java.time.LocalDate;

/**
 * Spring注入转换器
 * <a href='project\_20230421141647\src\test\java\J20230422104510.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422104510 {

    @Test
    public void testConversionService() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:type-conversion-second-part.xml");

        Car car = applicationContext.getBean("car", Car.class);
        Assertions.assertThat(car.getPrice()).isEqualTo(1000000);
        Assertions.assertThat(car.getProduceDate()).isEqualTo(LocalDate.of(2021, 1, 1));
    }
}
