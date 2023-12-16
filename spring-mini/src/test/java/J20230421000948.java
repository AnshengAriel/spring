import org.junit.Test;
import org.springframework.factory.BeanFactory;
import org.springframework.bean.HelloService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 最简单的bean容器
 * <a href='project\_20230421141647\src\test\java\J20230421000948.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230421000948 {

    @Test
    public void testGetBean() throws Exception {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBean("helloService", new HelloService());
        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        assertThat(helloService).isNotNull();
        assertThat(helloService.sayHello()).isEqualTo("hello");
    }

}
