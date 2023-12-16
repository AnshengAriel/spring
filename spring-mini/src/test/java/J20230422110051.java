import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.A;
import org.springframework.bean.B;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Spring解决循环依赖问题
 * <a href='project\_20230421141647\src\test\java\J20230422110051.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422110051 {

    @Test
    public void testCircularReference() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:circular-reference-without-proxy-bean.xml");
        A a = applicationContext.getBean("a", A.class);
        B b = applicationContext.getBean("b", B.class);
        assertThat(a.getB() == b).isTrue();
    }
}
