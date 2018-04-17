package producer.consumer.demo;

import java.util.Queue;
import java.util.Random;

class IntProducerRandom implements Runnable {

    private static final int MAX_QUEUE_SIZE = 64;

    private final Queue<Integer> queue;
    private final Random random;
    private final long period;

    public IntProducerRandom(Queue<Integer> queue, long period) {
        this.queue = queue;
        this.random = new Random();
        this.period = period;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        while (true) {
            try {
                synchronized (queue) {
                    while (queue.size() == MAX_QUEUE_SIZE) {
                        queue.wait();                   // Queue is full; await notification
                    }

                    Integer integer = random.nextInt();
                    if (queue.offer(integer)) {
                        System.out.println(Thread.currentThread().getName() + " enqueued: " + integer);
                    }
                    queue.notifyAll();                  // Wake up consumer threads
                }                                       // Release the queue's monitor

                Thread.sleep(period);                   // Sleep until next production
            } catch (InterruptedException e) {
                // Notification or other interrupt event
            }
        }
        // In cases where the thread doesn't go on forever, we'd stop producing new values and call join().
    }

}
