import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.HelloService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Aware接口
 * <a href='project\_20230421141647\src\test\java\J20230421174932.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230421174932 {

    @Test
    public void testAware() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        assertThat(helloService.getApplicationContext()).isNotNull();
        assertThat(helloService.getBeanFactory()).isNotNull();
    }
}
