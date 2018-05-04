package current.threadpool.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExtThreadPool {

	public static class MyTask implements Runnable{
		
		public String name = "";
		
		public MyTask(String name) {
			this.name = name;
		}
		
		@Override
		public void run() {
			System.out.println("正在执行:"+Thread.currentThread().getId());
		}
	}
	
	public static void main(String[] args) throws Exception {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(5,5,1L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>()) {
			
			@Override
			public void beforeExecute(Thread t, Runnable r) {
				System.out.println("准备执行"+((MyTask)r).name);
			}
			
			@Override
			public void afterExecute(Runnable r,Throwable e) {
				System.out.println("执行完毕"+((MyTask)r).name);
			}
			
			@Override
			public void terminated() {
				System.out.println("GG思密达");
			}
		};

		for(int i = 0 ; i < 5 ; i++) {
			pool.execute(new MyTask("TASK"+i));
		}
		
		pool.shutdown();
	
	}
}
