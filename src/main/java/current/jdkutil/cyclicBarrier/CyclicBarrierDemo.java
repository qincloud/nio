package current.jdkutil.cyclicBarrier;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier用法简单示范
 * */

public class CyclicBarrierDemo {
	
	public static class Solider extends Thread{
		CyclicBarrier cyclic; 
		String solider = "";
		public Solider(CyclicBarrier cyclic,String solider) {
			this.cyclic = cyclic;
			this.solider = solider;
		}
		
		@Override
		public void run() {
			try {
				cyclic.await();  //第一次调用等待士兵集合完成调用BarrierRun
				doWork();
				cyclic.await();  //第一次调用等待士兵任务完成调用BarrierRun
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void doWork() {
			System.out.println(solider+"任务完成");
		}
	}
	
	public static class BarrierRun implements Runnable{
		int n = 0;
		boolean flag;
		public BarrierRun(int n,boolean flag) {
			this.n = n;
		   this.flag = flag;
		}
		
		@Override
		public void run() {
			if(flag) {
				System.out.println("士兵"+n+"人任务全部完成");
			}else {
				flag = true;
				System.out.println("士兵"+n+"人集合完毕");
			}
		}
	}
	
	public static void main(String[] args) {
		int N = 10;
		boolean flag = false;
		CyclicBarrier cyclic = new CyclicBarrier(N,new BarrierRun(10,flag));
		Thread[] allthread = new Thread[N];
		for(int i = 0 ; i < N ; i++) {
			System.out.println("士兵"+i+"集合");
			allthread[i] = new Solider(cyclic,"士兵"+i);
			allthread[i].start();
		}
	}
}
