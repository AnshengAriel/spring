import org.assertj.core.api.Java6Assertions;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.bean.Car;
import org.springframework.util.ObserverUtil;

/**
 * 包扫描
 * <a href='project\_20230421141647\src\test\java\J20230422095937.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230422095937 {

    @Test
    public void testScanPackage() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:package-scan.xml");

        Car car = applicationContext.getBean("car", Car.class);
        ObserverUtil.append("ClassPathXmlApplicationContext#getBean");
        Java6Assertions.assertThat(car).isNotNull();
    }

}
