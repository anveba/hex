package test.engine.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import main.engine.ui.Image;
import org.junit.*;

import main.engine.graphics.*;
import main.engine.ui.*;
import main.engine.*;

import java.awt.*;

import static main.engine.Utility.*;
import static org.mockito.Mockito.when;

public class ImageTest {
	
	@Test
	public void constructorSetsValuesCorrectly() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = 0.24f;
		int sx = 24, sy = 32, sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		Image image = new Image(x, y, width, height, t, sx, sy, sw, sh);
		assertTrue(floatEquals(image.getX(), x));
		assertTrue(floatEquals(image.getY(), y));
		assertTrue(floatEquals(image.getWidth(), width));
		assertTrue(floatEquals(image.getHeight(), height));
		
		assertEquals(sx, image.getSourceX());
		assertEquals(sy, image.getSourceY());
		assertEquals(sw, image.getSourceWidth());
		assertEquals(sh, image.getSourceHeight());
	}

	@Test
	public void simpleConstructorSetsValuesCorrectly() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = 0.24f;
		int sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		when(t.width()).thenReturn(sw);
		when(t.height()).thenReturn(sh);

		Image image = new Image(x, y, width, height, t);
		assertTrue(floatEquals(image.getX(), x));
		assertTrue(floatEquals(image.getY(), y));
		assertTrue(floatEquals(image.getWidth(), width));
		assertTrue(floatEquals(image.getHeight(), height));

		assertEquals(0, image.getSourceX());
		assertEquals(0, image.getSourceY());
		assertEquals(sw, image.getSourceWidth());
		assertEquals(sh, image.getSourceHeight());
	}

	@Test
	public void simpleConstructorWithColourSetsValuesCorrectly() {
		float x = 0.4f, y = -0.2f, width = 0.342f, height = 0.24f;
		int sw = 40, sh = 60;
		Texture t = mock(Texture.class);
		Colour col = Colour.Blue;
		when(t.width()).thenReturn(sw);
		when(t.height()).thenReturn(sh);

		Image image = new Image(x, y, width, height, t, col);
		assertTrue(floatEquals(x, image.getX()));
		assertTrue(floatEquals(y, image.getY()));
		assertTrue(floatEquals(width, image.getWidth()));
		assertTrue(floatEquals(height, image.getHeight()));
		assertEquals(col, image.getColour());

		assertEquals(0, image.getSourceX());
		assertEquals(0, image.getSourceY());
		assertEquals(sw, image.getSourceWidth());
		assertEquals(sh, image.getSourceHeight());
	}
	
	@Test
	public void getAndSetPositionGetsAndSetsPosition() {
		float x = 0.4f, y = -0.2f;
		Texture t = mock(Texture.class);
		Image image = new Image(0.0f, 0.0f, 1.0f, 1.0f, t, 0, 0, 1, 1);
		image.setPosition(x, y);
		assertTrue(floatEquals(image.getX(), x));
		assertTrue(floatEquals(image.getY(), y));
	}
	
	@Test
	public void getAndSetDimensionsGetsAndSetsDimensions() {
		float width = 0.8f, height = 0.6f;
		Texture t = mock(Texture.class);
		Image image = new Image(0.0f, 0.0f, 999.0f, 999.0f, t, 0, 0, 1, 1);
		image.setWidth(width);
		image.setHeight(height);
		assertTrue(floatEquals(image.getWidth(), width));
		assertTrue(floatEquals(image.getHeight(), height));
	}
	
	@Test
	public void settingNegativeWidthThrowsException() {
		float width = -10.0f;
		Texture t = mock(Texture.class);
		Image image = new Image(0.0f, 0.0f, 999.0f, 999.0f, t, 0, 0, 1, 1);
		try {
			image.setWidth(width);			
		} catch(EngineException ex) {
			return;
		}
		fail();
	}
	
	@Test
	public void settingNegativeHeightThrowsException() {
		float height = -10.0f;
		Texture t = mock(Texture.class);
		Image image = new Image(0.0f, 0.0f, 999.0f, 999.0f, t, 0, 0, 1, 1);
		try {
			image.setHeight(height);			
		} catch(EngineException ex) {
			return;
		}
		fail();
	}
	
	@Test
	public void settingNegativeWidthInConstructorThrowsException() {
		float width = -10.0f;
		Texture t = mock(Texture.class);
		try {
			new Image(0.0f, 0.0f, width, 999.0f, t, 0, 0, 1, 1);	
		} catch(EngineException ex) {
			return;
		}
		fail();
	}
	
	@Test
	public void settingNegativeHeightInConstructorThrowsException() {
		float height = -10.0f;
		Texture t = mock(Texture.class);
		try {
			new Image(0.0f, 0.0f, 999.0f, height, t, 0, 0, 1, 1);	
		} catch(EngineException ex) {
			return;
		}
		fail();
	}
	
	@Test
	public void getAndSetSourcePositionGetsAndSetsSourcePosition() {
		int sx = 80, sy = 90;
		Texture t = mock(Texture.class);
		Image image = new Image(0.0f, 0.0f, 1.0f, 1.0f, t, 0, 0, 1, 1);
		image.setSourceX(sx);
		image.setSourceY(sy);
		assertEquals(sx, image.getSourceX());
		assertEquals(sy, image.getSourceY());
	}
	
	@Test
	public void getAndSetSourceDimensionsGetsAndSetsSourceDimensions() {
		int sw = 32, sh = 72;
		Texture t = mock(Texture.class);
		Image image = new Image(0.0f, 0.0f, 1.0f, 1.0f, t, 0, 0, 1, 1);
		image.setSourceWidth(sw);
		image.setSourceHeight(sh);
		assertEquals(sw, image.getSourceWidth());
		assertEquals(sh, image.getSourceHeight());
	}
	
	@Test
	public void getAndSetTextureGetsAndSetsTexture() {
		Texture t0 = mock(Texture.class);
		Image image = new Image(0.0f, 0.0f, 1.0f, 1.0f, t0, 0, 0, 1, 1);
		Texture t1 = mock(Texture.class);
		image.setTexture(t1);
		assertEquals(t1, image.getTexture());
	}
	
	@Test
	public void settingTextureToNullThrowsException() {
		Texture t0 = mock(Texture.class);
		Image image = new Image(0.0f, 0.0f, 1.0f, 1.0f, t0, 0, 0, 1, 1);
		Texture t1 = null;
		try {
			image.setTexture(t1);	
			fail();
		} catch (EngineException ex) {
		}
	}
	
	@Test
	public void settingTextureToNullInConstructorThrowsException() {
		try {
			new Image(0.0f, 0.0f, 1.0f, 1.0f, null, 0, 0, 1, 1);	
			fail();
		} catch (EngineException ex) {
		}
	}
}
