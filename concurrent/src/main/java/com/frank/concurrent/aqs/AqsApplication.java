package com.frank.concurrent.aqs;

import sun.nio.ch.ThreadPool;

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

	private BlockingQueue<Runnable> workQueue = new SynchronousQueue<>();

	ThreadFactory factory = Executors.privilegedThreadFactory();

	private ThreadPoolExecutor executor = new ThreadPoolExecutor(1,1,1000, TimeUnit.SECONDS,
			workQueue, factory, new ThreadPoolExecutor.AbortPolicy());

	public void singleLock(){
		try
		{
			executor.submit(() -> myJob());
			executor.submit(() -> myJob());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	private void myJob(){
		System.out.println(Thread.currentThread().getName() + " try to get lock");
		lock.lock();
		System.out.println(Thread.currentThread().getName() + " get lock");
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		lock.unlock();
		System.out.println(Thread.currentThread().getName() + " release lock");
	}

	public static void main(String[] args){
		AqsApplication application = new AqsApplication();
		application.singleLock();
	}
}
