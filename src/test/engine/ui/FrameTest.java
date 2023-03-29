package test.engine.ui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.input.ControlsArgs;
import main.engine.ui.*;

public class FrameTest {
	
	@Test
	public void settingRootSetsRoot() {
		Frame f = new Frame();
		UIElement e = mock(UIElement.class);
		f.setRoot(e);
		assertEquals(e, f.getRoot());
	}
	
	@Test
	public void clickingCallsRootClickHandlingMethod() {
		Frame f = new Frame();
		RectButton e = mock(RectButton.class);
		Mockito.doCallRealMethod().when(e).processClick(any());

		f.clickAt(0.0f, 0.0f);
		verify(e, times(0)).processClick(any());
		
		f.setRoot(e);
		f.clickAt(0.0f, 0.0f);
		verify(e, times(1)).processClick(any());
	}
	
	@Test
	public void hoveringCallsRootHoverHandlingMethod() {
		Frame f = new Frame();
		RectButton e = mock(RectButton.class);
		Mockito.doCallRealMethod().when(e).updateCursorPosition(any());

		f.hoverAt(0.0f, 0.0f);
		verify(e, times(0)).updateCursorPosition(any());
		
		f.setRoot(e);
		f.hoverAt(0.0f, 0.0f);
		verify(e, times(1)).updateCursorPosition(any());
	}
	
	@Test
	public void textInputCallsRootTextInputHandlingMethod() {
		Frame f = new Frame();
		RectButton e = mock(RectButton.class);
		Mockito.doCallRealMethod().when(e).processTextInput(any());

		f.processTextInput('c');
		verify(e, times(0)).processTextInput(any());
		
		f.setRoot(e);
		f.processTextInput('c');
		verify(e, times(1)).processTextInput(any());
	}
	
	@Test
	public void controlsInputCallsRootControlsInputHandlingMethod() {
		Frame f = new Frame();
		RectButton e = mock(RectButton.class);
		Mockito.doCallRealMethod().when(e).processControlsInput(any());

		f.processControlsInput(mock(ControlsArgs.class));
		verify(e, times(0)).processControlsInput(any());
		
		f.setRoot(e);
		f.processControlsInput(mock(ControlsArgs.class));
		verify(e, times(1)).processControlsInput(any());
	}
	
	@Test
	public void updateCallsRootUpdateMethod() {
		Frame f = new Frame();
		RectButton e = mock(RectButton.class);
		Mockito.doCallRealMethod().when(e).update(any());

		f.update(mock(TimeRecord.class));
		verify(e, times(0)).update(any());
		
		f.setRoot(e);
		f.update(mock(TimeRecord.class));
		verify(e, times(1)).update(any());
	}
}
