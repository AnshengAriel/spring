import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.Car;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * FactoryBean
 * <a href='project\_20230421141647\src\test\java\J20230421185545.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230421185545 {

    @Test
    public void testFactoryBean() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:factory-bean.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getBrand()).isEqualTo("porsche");
    }

}
