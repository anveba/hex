package test.engine.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;

import org.junit.*;

import main.engine.EngineException;
import main.engine.ui.*;

public class FrameStackTest {
	
	@Before
	public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
	   Field instance = FrameStack.class.getDeclaredField("instance");
	   instance.setAccessible(true);
	   instance.set(null, null);
	}
	
	@Test
	public void poppingPushedFrameYieldsPushedFrame() {
		Frame f = mock(Frame.class);
		FrameStack.getInstance().push(f);
		assertEquals(f, FrameStack.getInstance().pop());
	}
	
	@Test
	public void pushingFrameIncrementsSize() {
		assertEquals(0, FrameStack.getInstance().size());
		Frame f = mock(Frame.class);
		FrameStack.getInstance().push(f);
		assertEquals(1, FrameStack.getInstance().size());
	}
	
	@Test
	public void poppingFrameDecrementsSize() {
		Frame f = mock(Frame.class);
		FrameStack.getInstance().push(f);
		assertEquals(1, FrameStack.getInstance().size());
		FrameStack.getInstance().pop();
		assertEquals(0, FrameStack.getInstance().size());
	}
	
	@Test(expected = EngineException.class)
	public void poppingEmptyStackThrowsException() {
		assertEquals(0, FrameStack.getInstance().size());
		FrameStack.getInstance().pop();
	}
}
