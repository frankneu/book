package com.frank.netty;

import java.io.IOException;

/**
 * Created by wangbiao on 2018/9/7.
 */
public class Application
{

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
			aioApplication();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
