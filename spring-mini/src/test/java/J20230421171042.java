import org.junit.Test;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.Car;
import org.springframework.bean.Person;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 应用上下文ApplicationContext
 * <a href='project\_20230421141647\src\test\java\J20230421171042.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230421171042 {

    public static final String PATH = "classpath:spring.xml";

    @Test
    public void testApplicationContext() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(PATH);

		Person person = applicationContext.getBean("person", Person.class);
		System.out.println(person);
		// name属性在CustomBeanFactoryPostProcessor中被修改为ivy
		assertThat(person.getName()).isEqualTo("ivy");

		Car car = applicationContext.getBean("car", Car.class);
		System.out.println(car);
		// brand属性在CustomerBeanPostProcessor中被修改为lamborghini
		assertThat(car.getBrand()).isEqualTo("lamborghini");
    }

    @Test
    public void testBeanFactory() {
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(PropertyValue.of("price", 100));

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("Car", new BeanDefinition(Car.class, propertyValues));
        System.out.println(factory.getBean("Car"));
    }

}
