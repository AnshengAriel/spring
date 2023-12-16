import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.WorldService;

/**
 * 动态代理融入Bean的生命周期
 * <a href='project\_20230421141647\src\test\java\J20230422010454.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422010454 {

    @Test
    public void testAutoProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
    }

}
