package current.jdkutil.forkJoinPool;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Long> {
	long start;
	long end;
	long threadnum = 10000;

	public CountTask(long start, long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long sum = 0;
		boolean flag = (end - start) < threadnum;
		long pos = start;
		long d = (end - start) / 100;
		if (flag) {
			for(long i = start; i < end;i++) {
				sum += i;
			}
		} else {
			ArrayList<CountTask> tasks = new ArrayList<CountTask>();
			pos = d + 1;
			long dend = pos + d;
			CountTask task = new CountTask(pos,dend);
			tasks.add(task);
			task.fork();
			for(CountTask t : tasks) {
				sum += t.join();
			}
		}

		return sum;
	}

	public static void main(String[] args) {
		ForkJoinPool fork = new ForkJoinPool();
		ForkJoinTask<Long> task = fork.submit(new CountTask(0, 200000L));
		try {
			long sum = task.get();
			System.out.println(sum);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
