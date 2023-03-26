package test.hex.scene;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.*;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.hex.scene.*;
import main.hex.HexException;

public class SceneDirectorTest {
	
	@Before
	public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
	   Field instance = SceneDirector.class.getDeclaredField("instance");
	   instance.setAccessible(true);
	   instance.set(null, null);
	}
	
	@Test
	public void changingSceneCallsScenesBeginMethod() {
		Scene s = mock(Scene.class);
		
		verify(s, times(0)).begin();
		
		SceneDirector.changeScene(s);
		
		verify(s, times(1)).begin();
	}
	
	@Test
	public void changingSceneCallsOldScenesEndMethod() {
		Scene s1 = mock(Scene.class);
		Scene s2 = mock(Scene.class);
		
		SceneDirector.changeScene(s1);
		
		SceneDirector.changeScene(s2);
		
		verify(s1, times(1)).end();
	}
	
	@Test(expected = HexException.class)
	public void changingSceneToNullThrowsException() {
		SceneDirector.changeScene(null);
	}
	
	@Test
	public void updatingCurrentSceneCallsCurrentScenesUpdateMethod() {
		Scene s = mock(Scene.class);
		SceneDirector.changeScene(s);
		
		var time = mock(TimeRecord.class);
		SceneDirector.updateCurrentScene(mock(TimeRecord.class));
		verify(s, times(0)).update(time);
		
		SceneDirector.updateCurrentScene(time);
		verify(s, times(1)).update(time);
	}
	
	@Test(expected = HexException.class)
	public void updatingCurrentSceneWithNoSceneActiveThrowsException() {
		var time = mock(TimeRecord.class);
		SceneDirector.updateCurrentScene(time);
	}
	
	@Test
	public void drawingCurrentSceneCallsCurrentScenesDrawMethod2D() {
		Scene s = mock(Scene.class);
		SceneDirector.changeScene(s);
		
		var r = mock(Renderer2D.class);
		SceneDirector.updateCurrentScene(mock(TimeRecord.class));
		verify(s, times(0)).draw2D(r);
		
		SceneDirector.drawCurrentScene2D(r);
		verify(s, times(1)).draw2D(r);
	}
	
	@Test(expected = HexException.class)
	public void drawingCurrentScene2DWithNoSceneActiveThrowsException() {
		var r = mock(Renderer2D.class);
		SceneDirector.drawCurrentScene2D(r);
	}
	
	@Test
	public void defaultCurrentSceneIsNoScene() {
		assertEquals(null, SceneDirector.currentScene());
	}
	
	@Test
	public void changingSceneSetsGivenSceneAsCurrent() {
		Scene s = mock(Scene.class);
		SceneDirector.changeScene(s);
		assertEquals(s, SceneDirector.currentScene());
	}
}
