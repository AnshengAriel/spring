import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.event.CustomEvent;

/**
 * 容器事件发布以及监听
 * <a href='project\_20230421141647\src\test\java\J20230421190804.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230421190804 {

    @Test
    public void testEventListener() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext));

        // 或者applicationContext.close()主动关闭容器;
        applicationContext.registerShutdownHook();
    }

}
