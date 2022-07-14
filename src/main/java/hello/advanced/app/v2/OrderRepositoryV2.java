package hello.advanced.app.v2;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final HelloTraceV2 trace;

    // 트랜잭션 아이디와 레벨값을 유지하기 위해 파라미터로 계속 추가해야함
    public void save(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            // 어떤 메서드에서 호출됐는지 로그로 보기 쉽지만 수작업임
            status = trace.beginSync(traceId, "OrderRepository.save()");
            // 저장 로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);
            trace.end(status);
        } catch (Exception e) {
            // 예외를 처리해야 해서 try catch 문이 생기게 됨
            trace.exception(status, e);
            throw e;    // 예외를 꼭 다시 던져주어야 한다.
        }
    }
    // 남은 문제
    // HTTP 요청을 구분하고 깊이를 표현하기 위해서 TraceId 동기화가 필요하다. TraceId 의 동기화를 위해서 관련 메서드의 모든 파라미터를 수정해야 한다.
    // 만약 인터페이스가 있다면 인터페이스까지 모두 고쳐야 하는 상황이다.
    // 로그를 처음 시작할 때는 begin() 을 호출하고, 처음이 아닐때는 beginSync() 를 호출해야 한다.
    // 만약에 컨트롤러를 통해서 서비스를 호출하는 것이 아니라, 다른 곳에서 서비스를 처음으로 호출하는 상황이라면 파리미터로 넘길 TraceId 가 없다.


    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
