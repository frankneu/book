package com.frank.concurrent.aqs;


import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Created by wangbiao on 2018/9/3.
 */
public class AqsApplication
{
	private static ReentrantLock lock = new ReentrantLock();

	private static Semaphore semaphore = new Semaphore(2, true);

	private CountDownLatch countDownLatch = new CountDownLatch(2);

	private BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();

	ThreadFactory factory = Executors.privilegedThreadFactory();

	private ThreadPoolExecutor executor = new ThreadPoolExecutor(1,5,1000, TimeUnit.SECONDS,
			workQueue, factory, new ThreadPoolExecutor.AbortPolicy());

	public void singleLock(){
		try
		{
			executor.submit(() -> countDownLatch(100));
			executor.submit(() -> countDownLatch(100));
			executor.submit(() -> countDownLatch(2000));
			executor.submit(() -> countDownLatchWait());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void reentrantLock(){
		System.out.println(Thread.currentThread().getName() + " try to get lock");
		lock.lock();
		try
		{
			System.out.println(Thread.currentThread().getName() + " get lock ############");
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		lock.unlock();
		System.out.println(Thread.currentThread().getName() + " release lock");
	}

	private void semaphore(){
		System.out.println(Thread.currentThread().getName() + " try to get semaphore");
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " get semaphore ############");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		semaphore.release();
		System.out.println(Thread.currentThread().getName() + " release semaphore");
	}

	private void countDownLatch(final int time){
		System.out.println(Thread.currentThread().getName() + " countDownLatch work thread start");
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " countDownLatch countDown ############");
		countDownLatch.countDown();
	}

	private void countDownLatchWait(){
		System.out.println(Thread.currentThread().getName() + " countDownLatch wait");
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " countDownLatch wait finished ############");
	}


	public static void main(String[] args){
		AqsApplication application = new AqsApplication();
		application.singleLock();
	}
}
