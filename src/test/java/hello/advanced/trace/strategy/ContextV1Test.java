package hello.advanced.trace.strategy;

import hello.advanced.trace.strategy.code.strategy.ContextV1;
import hello.advanced.trace.strategy.code.strategy.Strategy;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic1;
import hello.advanced.trace.strategy.code.strategy.StrategyLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    // 변하지 않는 부분을 Context 라는 곳에 두고,
    // 변하는 부분을 Strategy 라는 인터페이스를 만들고 해당 인터페이스를 구현하도록 해서 문제를 해결한다.
    // 상속이 아니라 위임으로 문제를 해결

    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }
    
    @Test
    void strategyV1() {
        // 원하는 모양으로 조립을 완료하고 난 다음에 context1.execute() 를 호출해서 context 를 실행한다.
        // 로직을 구현하는 클래스가 인터페이스에만 의존하므로 콘텍스트가 변경되어도 영햐이 없다.
        // -> 이것이 템플릿 메소드 패턴보다의 장점!
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 contextV1 = new ContextV1(strategyLogic1);
        contextV1.execute();

        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 contextV2 = new ContextV1(strategyLogic2);
        contextV2.execute();
    }
    
    @Test
    void strategyV2() {
        // 익명 내부 클래스를 자바8부터 제공하는 람다로 변경할 수 있다.
        // 람다로 변경하려면 인터페이스에 메서드가 1개만 있으면 되는데,
        // 여기에서 제공하는 Strategy 인터페이스는 메서드가 1개만 있으므로 람다로 사용할 수 있다.
        ContextV1 contextV1 = new ContextV1(() -> log.info("비즈니스 로직1 실행"));
        contextV1.execute();
        // 한번 조립 후에 실행하고자 하는 기능을 실행만 하면 된다!
        // 이 방식의 단점은 Context 와 Strategy 를 조립한 이후에는 전략을 변경하기가 번거롭다는 점

        ContextV1 contextV2 = new ContextV1(() -> log.info("비즈니스 로직2 실행"));
        contextV2.execute();
    }

}
