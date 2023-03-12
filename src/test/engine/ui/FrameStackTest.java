package test.engine.ui;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.*;
import org.mockito.Mockito;

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
	
	@Test
	public void clickingCallsTopFrameClickHandlingMethod() {
		Frame f = mock(Frame.class);
		Mockito.doCallRealMethod().when(f).clickAt(anyFloat(), anyFloat());

		FrameStack.getInstance().clickAt(0.0f, 0.0f);
		verify(f, times(0)).clickAt(anyFloat(), anyFloat());
		
		FrameStack.getInstance().push(f);
		FrameStack.getInstance().clickAt(0.0f, 0.0f);
		verify(f, times(1)).clickAt(anyFloat(), anyFloat());
	}
}
