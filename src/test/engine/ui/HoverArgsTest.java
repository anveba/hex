package test.engine.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.engine.ui.HoverArgs;

public class HoverArgsTest {

	@Test
	public void constructorSetsValuesCorrectly() {
		float x = 0.2f; float y = 5.0f;
		HoverArgs args = new HoverArgs(x, y);
		assertEquals(x, args.getX(), 0.001f);
		assertEquals(y, args.getY(), 0.001f);
	}
}
