package test.engine.ui;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.*;
import org.mockito.Mockito;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.input.ControlsArgs;
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
	public void clearingStackSetsSizeToZero() {
		Frame f = mock(Frame.class);
		FrameStack.getInstance().push(f);
		assertEquals(1, FrameStack.getInstance().size());
		FrameStack.getInstance().clear();
		assertEquals(0, FrameStack.getInstance().size());
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
	public void pressingCallsTopFramePressHandlingMethod() {
		Frame f = mock(Frame.class);
		Mockito.doCallRealMethod().when(f).pressAt(anyFloat(), anyFloat());

		FrameStack.getInstance().pressAt(0.0f, 0.0f);
		verify(f, times(0)).pressAt(anyFloat(), anyFloat());

		FrameStack.getInstance().push(f);
		FrameStack.getInstance().pressAt(0.0f, 0.0f);
		verify(f, times(1)).pressAt(anyFloat(), anyFloat());
	}

	@Test
	public void releasingCallsTopFrameReleaseHandlingMethod() {
		Frame f = mock(Frame.class);
		Mockito.doCallRealMethod().when(f).releaseAt(anyFloat(), anyFloat());

		FrameStack.getInstance().releaseAt(0.0f, 0.0f);
		verify(f, times(0)).releaseAt(anyFloat(), anyFloat());

		FrameStack.getInstance().push(f);
		FrameStack.getInstance().releaseAt(0.0f, 0.0f);
		verify(f, times(1)).releaseAt(anyFloat(), anyFloat());
	}
	
	@Test
	public void hoveringCallsTopFrameHoverHandlingMethod() {
		Frame f = mock(Frame.class);
		Mockito.doCallRealMethod().when(f).hoverAt(anyFloat(), anyFloat());

		FrameStack.getInstance().hoverAt(0.0f, 0.0f);
		verify(f, times(0)).hoverAt(anyFloat(), anyFloat());
		
		FrameStack.getInstance().push(f);
		FrameStack.getInstance().hoverAt(0.0f, 0.0f);
		verify(f, times(1)).hoverAt(anyFloat(), anyFloat());
	}
	

	@Test
	public void textInputCallsTopFrameTextInputHandlingMethod() {
		Frame f = mock(Frame.class);
		Mockito.doCallRealMethod().when(f).processTextInput(anyChar());

		FrameStack.getInstance().processTextInput('c');
		verify(f, times(0)).processTextInput('c');
		
		FrameStack.getInstance().push(f);
		FrameStack.getInstance().processTextInput('c');
		verify(f, times(1)).processTextInput('c');
	}
	
	@Test
	public void controlsInputCallsTopFrameControlsInputHandlingMethod() {
		Frame f = mock(Frame.class);
		Mockito.doCallRealMethod().when(f).processControlsInput(any());

		FrameStack.getInstance().processControlsInput(mock(ControlsArgs.class));
		verify(f, times(0)).processControlsInput(any());
		
		FrameStack.getInstance().push(f);
		FrameStack.getInstance().processControlsInput(mock(ControlsArgs.class));
		verify(f, times(1)).processControlsInput(any());
	}
	

	@Test
	public void updateCallsTopFrameUpdateMethod() {
		Frame f = mock(Frame.class);
		Mockito.doCallRealMethod().when(f).update(any());

		FrameStack.getInstance().update(mock(TimeRecord.class));
		verify(f, times(0)).update(any());
		
		FrameStack.getInstance().push(f);
		FrameStack.getInstance().update(mock(TimeRecord.class));
		verify(f, times(1)).update(any());
	}
}
