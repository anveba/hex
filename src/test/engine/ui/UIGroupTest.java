package test.engine.ui;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.input.ControlsArgs;
import main.engine.ui.*;

public class UIGroupTest {
	@Test
	public void constructorSetsValuesCorrectly() {
		float x = 0.23f, y = 87.3f;
		UIGroup g = new UIGroup(x, y);
		assertEquals(x, g.getX(), 0.001f);
		assertEquals(y, g.getY(), 0.001f);
	}
	
	@Test
	public void setPositionSetsXAndY() {
		float x = 0.23f, y = 87.3f;
		UIGroup g = new UIGroup(999.0f, 999.0f);
		g.setPosition(x, y);
		assertEquals(x, g.getX(), 0.001f);
		assertEquals(y, g.getY(), 0.001f);
	}
	
	@Test
	public void addingChildAddsChildAndRegistersAsBeingContainedInside() {
		UIGroup g = new UIGroup(999.0f, 999.0f);
		var e = new TestUIElementClass();
		assertFalse(g.containsChild(e));
		g.addChild(e);
		assertTrue(g.containsChild(e));
	}
	
	@Test(expected = EngineException.class)
	public void addingSameChildToSameGroupMoreThanOnceThrowsException() {
		UIGroup g = new UIGroup(999.0f, 999.0f);
		var e = new TestUIElementClass();
		g.addChild(e);
		g.addChild(e);
	}
	
	@Test(expected = EngineException.class)
	public void addingSameChildToTwoDifferentGroupsThrowsException() {
		UIGroup g1 = new UIGroup(999.0f, 999.0f);
		UIGroup g2 = new UIGroup(999.0f, 999.0f);
		var e = new TestUIElementClass();
		g1.addChild(e);
		g2.addChild(e);
	}
	
	@Test
	public void addingSameChildToOneGroupAndRemovingItAndAddingToAnotherGroupAddsChildToTheSecondGroup() {
		UIGroup g1 = new UIGroup(999.0f, 999.0f);
		UIGroup g2 = new UIGroup(999.0f, 999.0f);
		var e = new TestUIElementClass();
		g1.addChild(e);
		g1.removeChild(e);
		g2.addChild(e);
		assertTrue(g2.containsChild(e));
	}
	
	@Test(expected = EngineException.class)
	public void addingNullChildThrowsException() {
		UIGroup g = new UIGroup(999.0f, 999.0f);
		g.addChild(null);
	}
	
	@Test(expected = EngineException.class)
	public void addingItselfAsChildThrowsException() {
		UIGroup g = new UIGroup(999.0f, 999.0f);
		g.addChild(g);
	}
	
	@Test
	public void removingChildRemovesChildAndRegistersAsNotBeingContainedInside() {
		UIGroup g = new UIGroup(999.0f, 999.0f);
		var e = new TestUIElementClass();
		g.addChild(e);
		assertTrue(g.containsChild(e));
		assertTrue(g.removeChild(e));
		assertFalse(g.containsChild(e));
	}
	
	@Test
	public void groupContainsPositionsIffOneOrMoreClickableChildrenContainPosition() {
		float gx = 5.35f, gy = -1.34f;
		UIGroup g = new UIGroup(gx, gy);
		var e1 = spy(new TestClickableElementClass());
		float x1 = 0.2f, y1 = 0.6f, x2 = 12.2f, y2 = -5.5f;
		when(e1.containsPosition(x1 - gx, y1 - gy)).thenReturn(true);
		when(e1.containsPosition(x2 - gx, y2 - gy)).thenReturn(false);
		assertFalse(g.containsPosition(x1, y1));
		assertFalse(g.containsPosition(x2, y2));
		g.addChild(e1);
		assertTrue(g.containsPosition(x1, y1));
		assertFalse(g.containsPosition(x2, y2));		
		g.removeChild(e1);

		var e2 = spy(new TestClickableElementClass());
		when(e2.containsPosition(x1 - gx, y1 - gy)).thenReturn(false);
		when(e2.containsPosition(x2 - gx, y2 - gy)).thenReturn(true);
		assertFalse(g.containsPosition(x1, y1));
		assertFalse(g.containsPosition(x2, y2));
		g.addChild(e2);
		assertFalse(g.containsPosition(x1, y1));
		assertTrue(g.containsPosition(x2, y2));
		
		g.addChild(e1);
		assertTrue(g.containsPosition(x1, y1));
		assertTrue(g.containsPosition(x2, y2));
	}
	
	@Test
	public void clickingCallsChildClickHandlingMethodIfTheyContainTheCursorPositionWithLocalCoordinates() {
		float gx = 1.2f, gy = 0.242f;
		float cx = 0.45f, cy = 2.41f;
		UIGroup g = new UIGroup(gx, gy);
		var e = spy(new TestClickableElementClass());
		when(e.containsPosition(anyFloat(), anyFloat())).thenReturn(true);
		Mockito.doCallRealMethod().when(e).processClick(any());

		ClickArgs args = new ClickArgs(cx, cy);
		g.processClick(args);
		verify(e, times(0)).processClick(any());

		g.addChild(e);
		g.processClick(args);
		final ArgumentCaptor<ClickArgs> captor = ArgumentCaptor.forClass(ClickArgs.class);		
		verify(e, times(1)).processClick(captor.capture());
		final ClickArgs actualArgs = captor.getValue();
		assertEquals(cx - gx, actualArgs.getX(), 0.001f);
		assertEquals(cy - gy, actualArgs.getY(), 0.001f);
	}
	
	@Test
	public void cursorUpdateCallsChildCursorUpdateHandlingMethodWithLocalMousePosition() {
		float gx = 1.2f, gy = 0.242f;
		float hx = 0.45f, hy = 2.41f;
		UIGroup g = new UIGroup(gx, gy);
		var e = spy(new TestClickableElementClass());
		Mockito.doCallRealMethod().when(e).updateCursorPosition(any());

		HoverArgs args = new HoverArgs(hx, hy);
		g.updateCursorPosition(args);
		verify(e, times(0)).updateCursorPosition(any());
		
		g.addChild(e);
		g.updateCursorPosition(args);
		final ArgumentCaptor<HoverArgs> captor = ArgumentCaptor.forClass(HoverArgs.class);		
		verify(e, times(1)).updateCursorPosition(captor.capture());
		final HoverArgs actualArgs = captor.getValue();
		assertEquals(hx - gx, actualArgs.getX(), 0.001f);
		assertEquals(hy - gy, actualArgs.getY(), 0.001f);
	}
	
	@Test
	public void textInputCallsChildTextInputHandlingMethod() {
		float gx = 1.2f, gy = 0.242f;
		UIGroup g = new UIGroup(gx, gy);
		var e = spy(new TestClickableElementClass());
		Mockito.doCallRealMethod().when(e).processTextInput(any());

		var args = mock(TextInputArgs.class);
		g.processTextInput(args);
		verify(e, times(0)).updateCursorPosition(any());
		
		g.addChild(e);
		g.processTextInput(args);
		final ArgumentCaptor<TextInputArgs> captor = ArgumentCaptor.forClass(TextInputArgs.class);		
		verify(e, times(1)).processTextInput(captor.capture());
		final TextInputArgs actualArgs = captor.getValue();
		assertEquals(args.getCharacter(), actualArgs.getCharacter());
	}
	
	@Test
	public void controlsInputCallsChildControlsInputHandlingMethod() {
		float gx = 1.2f, gy = 0.242f;
		UIGroup g = new UIGroup(gx, gy);
		var e = spy(new TestClickableElementClass());
		Mockito.doCallRealMethod().when(e).processControlsInput(any());

		var args = mock(ControlsArgs.class);
		g.processControlsInput(args);
		verify(e, times(0)).updateCursorPosition(any());
		
		g.addChild(e);
		g.processControlsInput(args);
		final ArgumentCaptor<ControlsArgs> captor = ArgumentCaptor.forClass(ControlsArgs.class);		
		verify(e, times(1)).processControlsInput(captor.capture());
		final ControlsArgs actualArgs = captor.getValue();
		assertEquals(args.getControls(), actualArgs.getControls());
	}
	
	@Test
	public void updateCallsChildUpdateMethod() {
		float gx = 1.2f, gy = 0.242f;
		UIGroup g = new UIGroup(gx, gy);
		var e = spy(new TestClickableElementClass());
		Mockito.doCallRealMethod().when(e).update(any());

		var args = mock(TimeRecord.class);
		g.update(args);
		verify(e, times(0)).updateCursorPosition(any());
		
		g.addChild(e);
		g.update(args);
		final ArgumentCaptor<TimeRecord> captor = ArgumentCaptor.forClass(TimeRecord.class);		
		verify(e, times(1)).update(captor.capture());
		final TimeRecord actualArgs = captor.getValue();
		assertEquals(args.elapsedSeconds(), actualArgs.elapsedSeconds(), 0.0001f);
	}

	@Test
	public void disableCallsChildDisableMethod() {
		float gx = 1.2f, gy = 0.242f;
		UIGroup g = new UIGroup(gx, gy);

		Assert.assertFalse(g.isDisabled());

		var e1 = spy(new TestClickableElementClass());
		var e2 = spy(new TestClickableElementClass());

		g.addChild(e1);
		g.addChild(e2);

		g.disable();

		Assert.assertTrue(g.isDisabled());

		verify(e1, times(1)).disable();
		verify(e2, times(1)).disable();
	}

	@Test
	public void enableCallsChildEnableMethod() {
		float gx = 1.2f, gy = 0.242f;
		UIGroup g = new UIGroup(gx, gy);

		Assert.assertFalse(g.isDisabled());

		var e1 = spy(new TestClickableElementClass());
		var e2 = spy(new TestClickableElementClass());

		g.addChild(e1);
		g.addChild(e2);

		g.disable();

		Assert.assertTrue(g.isDisabled());

		verify(e1, times(1)).disable();
		verify(e2, times(1)).disable();

		g.enable();

		Assert.assertFalse(g.isDisabled());

		verify(e1, times(1)).enable();
		verify(e2, times(1)).enable();
	}
}
