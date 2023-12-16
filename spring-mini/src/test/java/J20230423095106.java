import org.junit.Before;
import org.junit.Test;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.adapter.MethodAfterAdviceInterceptor;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.bean.WorldService;
import org.springframework.bean.WorldServiceImpl;

/**
 * After通知
 * <a href='project\_20230421141647\src\test\java\J20230423095106.java' style='color:green;font-weight:bold;'>运行一下</a>
 */
public class J20230423095106 {

    private AdvisedSupport advisedSupport = new AdvisedSupport();

    @Before
    public void setup() {
        WorldService worldService = new WorldServiceImpl();
        TargetSource targetSource = new TargetSource(worldService);
        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* org.springframework.bean.WorldService.explode(..))").getMethodMatcher();

        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodMatcher(methodMatcher);
    }

    @Test
    public void testAfterAdvice() throws Exception {
        WorldServiceAfterAdvice worldServiceAfterAdvice = new WorldServiceAfterAdvice();
        MethodAfterAdviceInterceptor interceptor = new MethodAfterAdviceInterceptor(worldServiceAfterAdvice);
        advisedSupport.setMethodInterceptor(interceptor);

        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
        System.out.println("------------------------------------------------------------");
        System.out.println(proxy.getName());
    }

}
