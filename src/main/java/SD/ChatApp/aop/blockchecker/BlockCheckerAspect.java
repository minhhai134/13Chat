package SD.ChatApp.aop.blockchecker;

import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.service.network.BlockService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class BlockCheckerAspect {
    @Autowired
    private BlockService blockService;
//    @Autowired
//    private HttpServletRequest request;

    @Around("@annotation(BlockCheck)")
    public Object blockChecker(ProceedingJoinPoint joinPoint) throws Throwable {

        // Extract the method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        BlockCheck blockCheck = method.getAnnotation(BlockCheck.class);

        Object[] parameterValues = joinPoint.getArgs();

        // Extract the custom limit and time window
        String user1 = blockCheck.user1();
        String user2 = blockCheck.user2();

        if(blockService.checkBlockstatus(user1, user2) || blockService.checkBlockstatus(user2, user1))
            throw new UserNotFoundException();

        return joinPoint.proceed();
    }

}
