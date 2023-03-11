package test.engine.ui;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;

import main.engine.EngineException;
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
	public void clickingCallsRootClickableClickHandlingMethod() {
		Frame f = new Frame();
		RectButton e = mock(RectButton.class);
		when(e.containsPosition(0.0f, 0.0f)).thenReturn(true);
		Mockito.doCallRealMethod().when(e).onClick(any());

		f.clickAt(0.0f, 0.0f);
		verify(e, times(0)).onClick(any());
		
		f.setRoot(e);
		f.clickAt(0.0f, 0.0f);
		verify(e, times(1)).onClick(any());
	}
}
