import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.Car;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bean的作用域Prototype
 * <a href='project\_20230421141647\src\test\java\J20230421180855.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230421180855 {

    @Test
    public void testPrototype() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:prototype-bean.xml");

        Car car1 = applicationContext.getBean("car", Car.class);
        Car car2 = applicationContext.getBean("car", Car.class);
        assertThat(car1 != car2).isTrue();
    }

}
