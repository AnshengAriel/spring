import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.WorldService;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 没有为代理bean设置属性
 * <a href='project\_20230421141647\src\test\java\J20230422101053.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422101053 {

    @Test
    public void testPopulateProxyBeanWithPropertyValues() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:populate-proxy-bean-with-property-values.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
        assertThat(worldService.getName()).isEqualTo("earth");
    }

}
