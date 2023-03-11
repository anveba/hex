package test.engine.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.engine.ui.ClickArgs;

public class ClickArgsTest {

	@Test
	public void constructorSetsValuesCorrectly() {
		float x = 0.2f; float y = 5.0f;
		ClickArgs args = new ClickArgs(x, y);
		assertEquals(x, args.getX(), 0.001f);
		assertEquals(y, args.getY(), 0.001f);
	}
}
