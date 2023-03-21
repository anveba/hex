package test.engine.ui;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.mockito.Mockito;

import main.engine.EngineException;
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
		var e = mock(UIElement.class);
		assertFalse(g.containsChild(e));
		g.addChild(e);
		assertTrue(g.containsChild(e));
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
		var e = mock(UIElement.class);
		g.addChild(e);
		assertTrue(g.containsChild(e));
		assertTrue(g.removeChild(e));
		assertFalse(g.containsChild(e));
	}
	
	@Test
	public void groupContainsPositionsIffOneOrMoreChildrenContainPosition() {
		UIGroup g = new UIGroup(999.0f, 999.0f);
		var e1 = mock(UIElement.class);
		float x1 = 0.2f, y1 = 0.6f, x2 = 12.2f, y2 = -5.5f;
		when(e1.containsPosition(x1, y1)).thenReturn(true);
		when(e1.containsPosition(x2, y2)).thenReturn(false);
		assertFalse(g.containsPosition(x1, y1));
		assertFalse(g.containsPosition(x2, y2));
		g.addChild(e1);
		assertTrue(g.containsPosition(x1, y1));
		assertFalse(g.containsPosition(x2, y2));		
		g.removeChild(e1);

		var e2 = mock(UIElement.class);
		when(e2.containsPosition(x1, y1)).thenReturn(false);
		when(e2.containsPosition(x2, y2)).thenReturn(true);
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
	public void clickingCallsChildClickablesClickHandlingMethodIfTheyContainTheCursorPosition() {
		UIGroup g = new UIGroup(0.0f, 0.0f);
		RectButton e = mock(RectButton.class);
		when(e.containsPosition(0.0f, 0.0f)).thenReturn(true);
		Mockito.doCallRealMethod().when(e).onClick(any());

		ClickArgs args = new ClickArgs(0.0f, 0.0f);
		g.onClick(args);
		verify(e, times(0)).onClick(any());
		
		g.addChild(e);
		g.onClick(args);
		verify(e, times(1)).onClick(any());
	}
	
	@Test
	public void cursorUpdateCallsChildClickablesCursorUpdateHandlingMethod() {
		UIGroup g = new UIGroup(0.0f, 0.0f);
		RectButton e = mock(RectButton.class);
		Mockito.doCallRealMethod().when(e).updateCursorPosition(any());

		HoverArgs args = new HoverArgs(0.0f, 0.0f);
		g.updateCursorPosition(args);
		verify(e, times(0)).updateCursorPosition(any());
		
		g.addChild(e);
		g.updateCursorPosition(args);
		verify(e, times(1)).updateCursorPosition(any());
	}
}
