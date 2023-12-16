import org.junit.Test;
import org.springframework.bean.Color;
import org.springframework.bean.FactoryEnum;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.A;
import org.springframework.bean.B;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Spring解决代理对象的循环依赖问题
 * <a href='project\_20230421141647\src\test\java\J20230422110424.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422110424 {

    @Test
    public void testCircularReferenceOfProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:circular-reference-with-proxy-bean.xml");
        A a = applicationContext.getBean("a", A.class);
        B b = applicationContext.getBean("b", B.class);
        System.out.println(Color.a == a);

        assertThat(b.getA() == a).isTrue();
    }
}
