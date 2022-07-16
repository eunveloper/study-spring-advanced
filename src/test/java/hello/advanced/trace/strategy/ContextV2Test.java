package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        // 의존관계 주입 방식으로 미리 주입받는것이 아닌, 파라미터 방식으로 변경!
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    // 익명 내부 클래스 방식으로도 이상 없음
    @Test
    void strategyV2() {
        ContextV2 context = new ContextV2();
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직1 실행");
            }
        });
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직2 실행");
            }
        });
    }

    // 람다 방식으로도 이상 없음
    @Test
    void strategyV3() {
        // 의존주입 관계가 아니라 Context 를 생성할때 주입해서 변경이 필요할때마다 새로 만들어줬는데
        // 파라미터 방식이기 때문에 Context 를 하나만 생성하고 변경이 필요할때마다 호출 시에 파라미터로 넘겨주면 됨!
        // 두 가지 방식 모두 장,단점이 있으니 상황에 따라 잘 선택해서 사용해야 함!
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스 로직1 실행"));
        context.execute(() -> log.info("비즈니스 로직2 실행"));
        // 파라미터로 Strategy 를 넘기면서 이미 call() 함수를 호출은 했지만
        // 실제 실행은 코드를 넘겨준곳 context.execute(..) 에서 실행되는 이 구조가 콜백 구조이다!
        // Java8 이하에서는 익명 내부 클래스를 주로 사용했지만
        // Java8 이상에서는 람다를 주로 사용한다!
    }

}
