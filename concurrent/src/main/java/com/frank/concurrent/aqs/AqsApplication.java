package com.frank.concurrent.aqs;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 验证常用AQS实现工具类
 * Created by wangbiao on 2018/9/3.
 */
public class AqsApplication
{
	//公平锁，先等待的先获得锁
	private static ReentrantLock lock = new ReentrantLock(true);

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

	private void condition(){
		Condition condition = lock.newCondition();
		condition.notifyAll();
		try
		{
			lock.lockInterruptibly();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}


	public static boolean RegexTest(String demo){
		String regex = "^(boolean|int|float|long|void|double|char|byte|short|String"
				+ "|Boolean|Integer|Float|Long|Double|Character|Byte|Short)\\s"
				+ "[A-Za-z]{1,}\\.[a-zA-Z0-9]{1,}\\("
				+ "((boolean|int|float|long|double|char|byte|short|String"
				+ "|Boolean|Integer|Float|Long|Double|Character|Byte|Short)\\s.{1,}"
				+ "((\\s)*\\,(\\s)*(boolean|int|float|long|double|char|byte|short|String"
				+ "|Boolean|Integer|Float|Long|Double|Character|Byte|Short)\\s.{1,})*)*\\)\\;";
		boolean i = demo.matches(regex);
		return i;
	}

	public static void main(String[] args){
		System.out.println(RegexTest("void partnerTransferCron.requestPartnerTransferTask()"));
	}
}
