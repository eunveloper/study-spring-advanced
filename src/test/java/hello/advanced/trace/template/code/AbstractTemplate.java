package hello.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 템플릿 메서드 패턴은 이름 그대로 템플릿을 사용하는 방식이다.
// 템플릿은 기준이 되는 거대한 틀이다.
// 템플릿이라는 틀에 변하지 않는 부분을 몰아둔다. 그리고 일부 변하는 부분을 별도로 호출해서 해결한다.
// 템플릿 메서드 패턴은 부모 클래스에 변하지 않는 템플릿 코드를 둔다.
// 그리고 변하는 부분은 자식 클래스에 두고 상속과 오버라이딩을 사용해서 처리한다.
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        call(); // 상속
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    protected abstract void call();

}
