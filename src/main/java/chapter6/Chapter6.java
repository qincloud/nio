package chapter6;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.junit.Test;

public class Chapter6 {

	public void simpleTest(Path path) throws Exception {
		WatchService watchService = FileSystems.getDefault().newWatchService();
		path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
				StandardWatchEventKinds.ENTRY_MODIFY);
		while (true) {
			WatchKey watchKey = watchService.take();
			List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
			for (WatchEvent<?> event : watchEvents) {
				System.out
						.println("[" + event.context() + "]文件发生了[" + event.kind() + ":" + event.kind().type() + "]事件");
			}
			watchKey.reset();
		}
	}

	private static void setWatcherOnThemeFile() {
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();
//			WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				final WatchKey wk = watchService.take();
				for (WatchEvent<?> event : wk.pollEvents()) {
					// we only register "ENTRY_MODIFY" so the context is always a Path.
					final Path changed = (Path) event.context();
					System.out.println(changed);
					if (changed.endsWith("Theme.css")) {
						System.out.println("Theme.css has changed...reloading stylesheet.");
//						scene.getStylesheets().clear();
//						scene.getStylesheets().add("resources/Theme.css");
					}
				}
				boolean valid = wk.reset();
				if (!valid)
					System.out.println("Watch Key has been reset...");
			}
		} catch (Exception e) {
			/* Thrown to void */ }
	}

	@Test
	public void test1() {
		Path path = FileSystems.getDefault().getPath("/home/lzq996298643/桌面/ITBOOK");
		try {
			simpleTest(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
