package chapter8;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

import org.junit.Test;

public class Chapter8Test {

	@Test
	public void testSelector() {
		try {
			Selector sel = Selector.open();
			Set<SelectionKey> ss = sel.selectedKeys();
			for (SelectionKey s : ss) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
