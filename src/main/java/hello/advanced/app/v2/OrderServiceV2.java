package hello.advanced.app.v2;

import hello.advanced.app.v1.OrderRepositoryV1;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import hello.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    // 트랜잭션 아이디와 레벨값을 유지하기 위해 파라미터로 계속 추가해야함
    public void orderItem(TraceId traceId, String itemId) {

        TraceStatus status = null;
        try {
            // 어떤 메서드에서 호출됐는지 로그로 보기 쉽지만 수작업임
            status = trace.beginSync(traceId, "OrderService.orderItem()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            // 예외를 처리해야 해서 try catch 문이 생기게 됨
            trace.exception(status, e);
            throw e;    // 예외를 꼭 다시 던져주어야 한다.
        }

    }

}
