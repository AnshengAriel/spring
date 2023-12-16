import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.bean.HelloService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * BeanDefinition和BeanDefinitionRegistry
 * <a href='project\_20230421141647\src\test\java\J20230421002240.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230421002240 {

    @Test
    public void testBeanFactory() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("helloService", new BeanDefinition(HelloService.class));

        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }

}
