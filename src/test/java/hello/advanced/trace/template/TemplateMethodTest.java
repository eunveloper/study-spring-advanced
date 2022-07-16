package hello.advanced.trace.template;

import hello.advanced.trace.template.code.AbstractTemplate;
import hello.advanced.trace.template.code.SubClassLogic1;
import hello.advanced.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
        logic1();
        logic2();
        // 코드 중복이 있음
        // 코드 중복이 있다는 것은 변경이 필요할때 중복의 개수만큼 변경을 해야하는 구조! -> 좋지 않은 구조
    }


    // 비지니스 로직을 제외하면 다른 부분은 모두 동일하다!
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
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();
        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
        // 코드 중복이 없음
    }

    @Test
    void templateMethodV2() {
        // 템플릿 메서드 패턴은 SubClassLogic1 , SubClassLogic2 처럼 클래스를 계속 만들어야 하는 단점이 있다.
        // 익명 내부 클래스를 사용하면 이런 단점을 보완
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직 1 실행");
            }
        };
        log.info("class name = {}", template1.getClass()); // TemplateMethodTest$1 Java 가 임의로 만들어준 익명 내부 클래스
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직 2 실행");
            }
        };
        template2.execute();
    }

}
