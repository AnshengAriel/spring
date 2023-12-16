import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.Person;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Autowire
 * <a href='project\_20230421141647\src\test\java\J20230422100610.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422100610 {

    @Test
    public void testAutowiredAnnotation() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:autowired-annotation.xml");

        Person person = applicationContext.getBean(Person.class);
        assertThat(person.getCar()).isNotNull();
    }

}
