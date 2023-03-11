package test.engine.ui;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.*;

import main.engine.ui.*;
import main.engine.*;
import main.engine.font.BitmapFont;

import static main.engine.Utility.*;

public class TextTest {

	@Test
	public void constructorSetsValuesCorrectly() {
		float x = 0.4f, y = -0.2f, height = 0.24f;
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		Text text = new Text(x, y, font, str, height);
		assertTrue(floatEquals(text.getX(), x));
		assertTrue(floatEquals(text.getY(), y));
		assertEquals(font, text.getFont());
		assertEquals(str, text.getText());
		assertTrue(floatEquals(text.getHeight(), height));
	}
	
	@Test
	public void getAndSetPositionGetsAndSetsPosition() {
		float x = 0.23f, y = 0.3f;
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		Text text = new Text(999.0f, 999.0f, font, str, 1.0f);
		text.setPosition(x, y);
		assertTrue(floatEquals(text.getX(), x));
		assertTrue(floatEquals(text.getY(), y));
	}
	
	@Test
	public void getAndSetHeightGetsAndSetsHeight() {
		float height = 0.1f;
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		Text text = new Text(999.0f, 999.0f, font, str, 999.0f);
		text.setHeight(height);
		assertTrue(floatEquals(text.getHeight(), height));
	}
	
	@Test(expected = EngineException.class)
	public void settingNegativeHeightThrowsException() {
		float height = -0.2f;
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		Text text = new Text(999.0f, 999.0f, font, str, 999.0f);
		text.setHeight(height);			
	}
	
	@Test(expected = EngineException.class)
	public void settingNegativeHeightInConstructorThrowsException() {
		float height = -2.5f;
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		new Text(999.0f, 999.0f, font, str, height);
	}
	
	@Test
	public void pointsInsideTextIsReportedAsInside() {
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 4.5f, y = 6.82f;
		when(font.measureString(str)).thenReturn(new Vector2(wHalf * 2, hHalf * 2));
		Text text = new Text(x, y, font, str, 999.0f);
		assertTrue(text.containsPosition(-wHalf + 0.1f + x, -hHalf + 0.1f + y));
		assertTrue(text.containsPosition(wHalf - 0.1f + x, -hHalf + 0.1f + y));
		assertTrue(text.containsPosition(-wHalf + 0.1f + x, hHalf - 0.1f + y));
		assertTrue(text.containsPosition(wHalf - 0.1f + x, hHalf - 0.1f + y));
		assertTrue(text.containsPosition(x, y));
	}
	
	@Test
	public void pointsOutsideTextIsReportedAsOutside() {
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		float wHalf = 1.0f, hHalf = 1.0f;
		float x = 0.28f, y = 2.4f;
		when(font.measureString(str)).thenReturn(new Vector2(wHalf * 2, hHalf * 2));
		Text text = new Text(x, y, font, str, 999.0f);
		assertFalse(text.containsPosition(-wHalf - 0.1f + x, -hHalf - 0.1f + y));
		assertFalse(text.containsPosition(wHalf + 0.1f + x, -hHalf - 0.1f + y));
		assertFalse(text.containsPosition(-wHalf - 0.1f + x, hHalf + 0.1f + y));
		assertFalse(text.containsPosition(wHalf + 0.1f + x, hHalf + 0.1f + y));
		assertFalse(text.containsPosition(-wHalf - 0.1f + x, y));
		assertFalse(text.containsPosition(wHalf + 0.1f + x, y));
		assertFalse(text.containsPosition(x, -hHalf - 0.1f));
		assertFalse(text.containsPosition(x, hHalf + 0.1f));
	}
	
	@Test
	public void getAndSetFontGetsAndSetsFont() {
		String str = "hello world";
		BitmapFont font1 = mock(BitmapFont.class);
		Text text = new Text(999.0f, 999.0f, font1, str, 999.0f);
		BitmapFont font2 = mock(BitmapFont.class);
		text.setFont(font2);
		assertEquals(font2, text.getFont());
	}
	
	@Test(expected = EngineException.class)
	public void settingFontToNullThrowsException() {
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		Text text = new Text(999.0f, 999.0f, font, str, 999.0f);
		text.setFont(null);
	}
	
	@Test(expected = EngineException.class)
	public void settingFontToNullInConstructorThrowsException() {
		new Text(0.0f, 0.0f, null, "string test", 10.0f);	
	}
	
	@Test
	public void getAndSetTextGetsAndSetsFont() {
		String str1 = "hello world";
		BitmapFont font1 = mock(BitmapFont.class);
		Text text = new Text(999.0f, 999.0f, font1, str1, 999.0f);
		String str2 = "some other string";
		text.setText(str2);
		assertEquals(str2, text.getText());
	}
	
	@Test(expected = EngineException.class)
	public void settingTextToNullThrowsException() {
		String str = "hello world";
		BitmapFont font = mock(BitmapFont.class);
		Text text = new Text(999.0f, 999.0f, font, str, 999.0f);
		text.setText(null);
	}
	
	@Test(expected = EngineException.class)
	public void settingTextToNullInConstructorThrowsException() {
		new Text(0.0f, 0.0f, mock(BitmapFont.class), null, 10.0f);	
	}
}
