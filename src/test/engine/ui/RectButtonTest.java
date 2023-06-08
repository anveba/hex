package test.engine.ui;

import static main.engine.Utility.floatEquals;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;

import main.engine.EngineException;
import main.engine.font.BitmapFont;
import main.engine.graphics.Texture;
import main.engine.math.Vector2;
import main.engine.ui.ButtonCallback;
import main.engine.ui.ClickArgs;
import main.engine.ui.HoverArgs;
import main.engine.ui.Image;
import main.engine.ui.RectButton;
import main.engine.ui.Text;
import main.engine.ui.UIGroup;

public class RectButtonTest {

	@Test
	public void constructorSetsValuesCorrectly() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = 0.24f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		BitmapFont f = mock(BitmapFont.class);
		String displayedString = "hello world";
		RectButton button = new RectButton(
				x, y, width, height, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
		
		assertTrue(floatEquals(button.getX(), x));
		assertTrue(floatEquals(button.getY(), y));
		assertTrue(floatEquals(button.getWidth(), width));
		assertTrue(floatEquals(button.getHeight(), height));
		
		assertEquals(iw, button.getImageWidth(), 0.001f);
		assertEquals(ih, button.getImageHeight(), 0.001f);
		assertEquals(sx, button.getImageSourceX(), 0.001f);
		assertEquals(sy, button.getImageSourceY(), 0.001f);
		assertEquals(sw, button.getImageSourceWidth(), 0.001f);
		assertEquals(sh, button.getImageSourceHeight(), 0.001f);
		
		assertEquals(t, button.getTexture());
		assertEquals(f, button.getFont());
		assertEquals(displayedString, button.getDisplayedString());
		assertEquals(th, button.getTextHeight(), 0.001f);
	}

	@Test
	public void simpleConstructorSetsValuesCorrectly() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = 0.24f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		when(t.width()).thenReturn(sw);
		when(t.height()).thenReturn(sh);


		BitmapFont f = mock(BitmapFont.class);
		String displayedString = "hello world";
		RectButton button = new RectButton(
				x, y, width, height,
				t, f, displayedString, th,
				null, null, null);

		assertTrue(floatEquals(button.getX(), x));
		assertTrue(floatEquals(button.getY(), y));
		assertTrue(floatEquals(button.getWidth(), width));
		assertTrue(floatEquals(button.getHeight(), height));

		assertEquals(width, button.getImageWidth(), 0.001f);
		assertEquals(height, button.getImageHeight(), 0.001f);
		assertEquals(0, button.getImageSourceX(), 0.001f);
		assertEquals(0, button.getImageSourceY(), 0.001f);
		assertEquals(t.width(), button.getImageSourceWidth(), 0.001f);
		assertEquals(t.height(), button.getImageSourceHeight(), 0.001f);

		assertEquals(t, button.getTexture());
		assertEquals(f, button.getFont());
		assertEquals(displayedString, button.getDisplayedString());
		assertEquals(th, button.getTextHeight(), 0.001f);
	}
	
	@Test
	public void getAndSetPositionGetsAndSetsPosition() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = 0.24f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		BitmapFont f = mock(BitmapFont.class);
		String displayedString = "hello world";
		RectButton button = new RectButton(
				999.0f, 999.0f, width, height, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
		button.setPosition(x, y);
		assertTrue(floatEquals(button.getX(), x));
		assertTrue(floatEquals(button.getY(), y));
	}
	
	@Test
	public void getAndSetDimensionsGetsAndSetsDimensions() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = 0.24f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		BitmapFont f = mock(BitmapFont.class);
		String displayedString = "hello world";
		RectButton button = new RectButton(
				x, y, 999.0f, 999.0f, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
		button.setWidth(width);
		button.setHeight(height);
		assertTrue(floatEquals(button.getWidth(), width));
		assertTrue(floatEquals(button.getHeight(), height));
	}
	
	@Test
	public void settingNegativeWidthThrowsException() {
		float x = 0.4f, y = -0.2f, width = -0.342f, height = 0.24f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		BitmapFont f = mock(BitmapFont.class);
		String displayedString = "hello world";
		RectButton button = new RectButton(
				x, y, 999.0f, 999.0f, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
		try {
			button.setWidth(width);			
		} catch(EngineException ex) {
			return;
		}
		fail();
	}
	
	@Test
	public void settingNegativeHeightThrowsException() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = -0.24f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		BitmapFont f = mock(BitmapFont.class);
		String displayedString = "hello world";
		RectButton button = new RectButton(
				x, y, 999.0f, 999.0f, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
		try {
			button.setHeight(height);			
		} catch(EngineException ex) {
			return;
		}
		fail();
	}
	
	@Test(expected = EngineException.class)
	public void settingNegativeWidthInConstructorThrowsException() {
		float x = 0.4f, y = -0.2f, width = -0.342f, height = 0.24f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		BitmapFont f = mock(BitmapFont.class);
		String displayedString = "hello world";
		new RectButton(
				x, y, width, height, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
	}
	
	@Test(expected = EngineException.class)
	public void settingNegativeHeightInConstructorThrowsException() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = -0.24f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		BitmapFont f = mock(BitmapFont.class);
		String displayedString = "hello world";
		new RectButton(
				x, y, width, height, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
	}
	
	@Test
	public void pointsInsideIsReportedAsInside() {
		Texture t = mock(Texture.class);
		String displayedString = "hello world";
		BitmapFont f = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		RectButton button = new RectButton(
				x, y, wHalf * 2.0f, hHalf * 2.0f, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
		assertTrue(button.containsPosition(-wHalf + 0.1f + x, -hHalf + 0.1f + y));
		assertTrue(button.containsPosition(wHalf - 0.1f + x, -hHalf + 0.1f + y));
		assertTrue(button.containsPosition(-wHalf + 0.1f + x, hHalf - 0.1f + y));
		assertTrue(button.containsPosition(wHalf - 0.1f + x, hHalf - 0.1f + y));
		assertTrue(button.containsPosition(x, y));
	}
	
	@Test
	public void pointsOutsideIsReportedAsOutside() {
		Texture t = mock(Texture.class);
		String displayedString = "hello world";
		BitmapFont f = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		RectButton button = new RectButton(
				x, y, wHalf * 2.0f, hHalf * 2.0f, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);
		assertFalse(button.containsPosition(-wHalf - 0.1f + x, -hHalf - 0.1f + y));
		assertFalse(button.containsPosition(wHalf + 0.1f + x, -hHalf - 0.1f + y));
		assertFalse(button.containsPosition(-wHalf - 0.1f + x, hHalf + 0.1f + y));
		assertFalse(button.containsPosition(wHalf + 0.1f + x, hHalf + 0.1f + y));
		assertFalse(button.containsPosition(-wHalf - 0.1f + x, y));
		assertFalse(button.containsPosition(wHalf + 0.1f + x, y));
		assertFalse(button.containsPosition(x, -hHalf - 0.1f));
		assertFalse(button.containsPosition(x, hHalf + 0.1f));
	}
	
	@Test
	public void clickingCallsClickCallback() {
		Texture t = mock(Texture.class);
		String displayedString = "hello world";
		BitmapFont f = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		ButtonCallback callback = mock(ButtonCallback.class);
		RectButton button = spy(new RectButton(
				x, y, wHalf * 2.0f, hHalf * 2.0f, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				callback, null, null));

		verify(callback, times(0)).call(any());
		ClickArgs args = new ClickArgs(0.0f, 0.0f);
		when(button.containsPosition(anyFloat(), anyFloat())).thenReturn(false);
		button.processClickRelease(args);
		verify(callback, times(0)).call(any());
		when(button.containsPosition(anyFloat(), anyFloat())).thenReturn(true);
		button.processClickRelease(args);
		verify(callback, times(1)).call(any());
	}
	
	@Test
	public void clickCallbackIsIgnoredWhenNoCallbackIsSet() {
		Texture t = mock(Texture.class);
		String displayedString = "hello world";
		BitmapFont f = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		RectButton button = new RectButton(
				x, y, wHalf * 2.0f, hHalf * 2.0f, 
				t, iw, ih, sx, sy, sw, sh,
				f, displayedString, th,
				null, null, null);

		ClickArgs args = new ClickArgs(0.0f, 0.0f);
		button.processClickRelease(args);
	}
	
	@Test
	public void hoverEnterCallsHoverEnterCallback() {
		Texture t = mock(Texture.class);
		String displayedString = "hello world";
		BitmapFont f = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		ButtonCallback callback = mock(ButtonCallback.class);
		RectButton bSpy;
		{
			RectButton button = new RectButton(
					x, y, wHalf * 2.0f, hHalf * 2.0f, 
					t, iw, ih, sx, sy, sw, sh,
					f, displayedString, th,
					null, callback, null);
			bSpy = spy(button);
		}
		HoverArgs args = new HoverArgs(0.0f, 0.0f);
		when(bSpy.containsPosition(anyFloat(), anyFloat())).thenReturn(false);
		bSpy.updateCursorPosition(args);
		verify(callback, times(0)).call(any());
		
		when(bSpy.containsPosition(anyFloat(), anyFloat())).thenReturn(true);
		bSpy.updateCursorPosition(args);
		verify(callback, times(1)).call(any());
	}
	
	@Test
	public void hoverEnterCallbackIsIgnoredWhenNoCallbackIsSet() {
		Texture t = mock(Texture.class);
		String displayedString = "hello world";
		BitmapFont f = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		RectButton bSpy;
		{
			RectButton button = new RectButton(
					x, y, wHalf * 2.0f, hHalf * 2.0f, 
					t, iw, ih, sx, sy, sw, sh,
					f, displayedString, th,
					null, null, null);
			bSpy = spy(button);
		}
		HoverArgs args = new HoverArgs(0.0f, 0.0f);
		when(bSpy.containsPosition(anyFloat(), anyFloat())).thenReturn(false);
		bSpy.updateCursorPosition(args);
		
		when(bSpy.containsPosition(anyFloat(), anyFloat())).thenReturn(true);
		bSpy.updateCursorPosition(args);
	}
	
	@Test
	public void hoverExitCallsHoverExitCallback() {
		Texture t = mock(Texture.class);
		String displayedString = "hello world";
		BitmapFont f = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		ButtonCallback callback = mock(ButtonCallback.class);
		RectButton bSpy;
		{
			RectButton button = new RectButton(
					x, y, wHalf * 2.0f, hHalf * 2.0f, 
					t, iw, ih, sx, sy, sw, sh,
					f, displayedString, th,
					null, null, callback);
			bSpy = spy(button);
		}
		HoverArgs args = new HoverArgs(0.0f, 0.0f);
		when(bSpy.containsPosition(anyFloat(), anyFloat())).thenReturn(true);
		bSpy.updateCursorPosition(args);
		verify(callback, times(0)).call(any());
		
		when(bSpy.containsPosition(anyFloat(), anyFloat())).thenReturn(false);
		bSpy.updateCursorPosition(args);
		verify(callback, times(1)).call(any());
	}
	
	@Test
	public void hoverExitCallbackIsIgnoredWhenNoCallbackIsSet() {
		Texture t = mock(Texture.class);
		String displayedString = "hello world";
		BitmapFont f = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		float iw = 4.0f, ih = 2.0f, th = 0.15f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		RectButton bSpy;
		{
			RectButton button = new RectButton(
					x, y, wHalf * 2.0f, hHalf * 2.0f, 
					t, iw, ih, sx, sy, sw, sh,
					f, displayedString, th,
					null, null, null);
			bSpy = spy(button);
		}
		HoverArgs args = new HoverArgs(0.0f, 0.0f);
		when(bSpy.containsPosition(anyFloat(), anyFloat())).thenReturn(true);
		bSpy.updateCursorPosition(args);
		
		when(bSpy.containsPosition(anyFloat(), anyFloat())).thenReturn(false);
		bSpy.updateCursorPosition(args);
	}
}
