package com.frank.concurrent.http;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;
import com.netease.async.httpclient.AsyncHttpClientManager;
import com.netease.async.httpclient.HttpContext;
import com.netease.async.httpclient.callback.AsyncHttpStringCallback;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpTest
{
	static ExecutorService executor = Executors.newCachedThreadPool();

	public static void main(String[] args){
		int totalCount = 500;
		List<Future<Integer>> list = new LinkedList<>();
		long st1 = System.currentTimeMillis();
		while(totalCount-- > 0)
		{
			list.add(executor.submit(() -> {
				HttpGet httpget = new HttpGet("http://www.google.com/");
				try (CloseableHttpClient httpclient = HttpClients.createDefault())
				{
					httpclient.execute(httpget);
				}
				catch (Exception e)
				{
				}
				return 1;
			}));
		}
		System.out.println(totalCount);
		for(Future<Integer> future : list){
			try
			{
				totalCount += future.get();
			}
			catch (InterruptedException e)
			{
			}
			catch (ExecutionException e)
			{
			}
		}
		long st2 = System.currentTimeMillis();
		System.out.println(totalCount);
		System.out.println("total time," +(st2-st1));
	}



}
