package com.frank.netty;

import java.io.IOException;

/**
 * Created by wangbiao on 2018/9/7.
 */
public class Application
{
	/**
	 * bio功能，阻塞io，服务端和客户端代码串行执行
	 * @throws IOException
	 */
	public static void bioApplication() throws IOException
	{
		com.frank.netty.bio.TimeServer.start(8080);
		com.frank.netty.bio.TimeClient.start(8080);
	}

	/**
	 * 伪nio
	 * @throws IOException
	 */
	public static void pioApplication() throws IOException
	{
		com.frank.netty.pio.TimeServer.start(8080);
		com.frank.netty.bio.TimeClient.start(8080);
	}

	/**
	 * nio功能，基于多路复用选择器Selector，底层基于epoll，当channel中数据准备好的时候，就会被selector选择出来
	 * @throws IOException
	 */
	public static void nioApplication() throws IOException
	{
		com.frank.netty.nio.TimeServer.start(8080);
		com.frank.netty.nio.TimeClient.start(8080);
	}

	/**
	 * aio功能
	 * client向服务端请求当前时间，然后服务端通过命令行返回当前时间
	 * @throws IOException
	 */
	public static void aioApplication() throws IOException
	{
		com.frank.netty.aio.TimeClient.start(8080);
		com.frank.netty.aio.TimeServer.start(8080);
	}



	public static void main(String[] args){
		try
		{
			pioApplication();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
