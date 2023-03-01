package cn.away;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

/**
 * @author guowei
 */
public class FiberExample {

    public static void main(String[] args) throws Exception{
        CountDownLatch count  = new CountDownLatch(10000);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        IntStream.range(0, 10000).forEach(i -> new Fiber() {
            @Override
            protected String run() throws SuspendExecution, InterruptedException {
                // Quasar中Thread和Fiber都被称为Strand,Fiber不能调用Thread.sleep休眠
                Strand.sleep(1000);
                count.countDown();
                return "aa";
            }
        }.start());
        count.await();
        stopWatch.stop();
        System.out.println("结束了: " + stopWatch.prettyPrint());
    }

}
