package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            fieldService.logic("userA");
        };
        Runnable userB = () -> {
            fieldService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        // sleep(2000); // 동시성 문제 발생 X -> logic() 은 1초 대기이기 때문에 동시성이 생기지 않는다
        sleep(500); // 동시성 문제 발생 O -> 이전 스레드의 실행이 완료되기 전에 다음 스레드가 실행되기 떄문!
        threadB.start();

        sleep(3000);    // 메인 스레드 종료 대기
        log.info("main exit");
    }

    private void sleep(int milli) {
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
