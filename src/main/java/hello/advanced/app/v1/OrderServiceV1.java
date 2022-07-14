package hello.advanced.app.v1;

import hello.advanced.app.v0.OrderRepositoryV0;
import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV1 trace;

    public void orderItem(String itemId) {

        TraceStatus status = null;
        try {
            // 어떤 메서드에서 호출됐는지 로그로 보기 쉽지만 수작업임
            status = trace.begin("OrderService.orderItem()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            // 예외를 처리해야 해서 try catch 문이 생기게 됨
            trace.exception(status, e);
            throw e;    // 예외를 꼭 다시 던져주어야 한다.
        }

    }

}
