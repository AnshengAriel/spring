import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用上下文ApplicationContext
 * <a href='project\_20230421141647\src\test\java\J20230421173029.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230421173029 {

    @Test
    public void testInitAndDestroyMethod() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:init-and-destroy-method.xml");
        // 或者手动关闭 applicationContext.close();
        applicationContext.registerShutdownHook();
    }
}
