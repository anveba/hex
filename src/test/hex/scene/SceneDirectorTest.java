package test.hex.scene;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;

import org.junit.*;

import main.engine.TimeRecord;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Renderer3D;
import main.hex.scene.*;
import main.hex.HexException;

public class SceneDirectorTest  {
	
	@Before
	public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
	   Field instance = SceneDirector.class.getDeclaredField("instance");
	   instance.setAccessible(true);
	   instance.set(null, null);
	}
	
	@Test
	public void changingSceneCallsScenesBeginMethod() {
		TestScene s = mock(TestScene.class);
		
		verify(s, times(0)).begin();
		
		SceneDirector.changeScene(s);
		
		verify(s, times(1)).begin();
	}
	
	@Test
	public void changingSceneCallsOldScenesEndMethod() {
		TestScene s1 = mock(TestScene.class);
		TestScene s2 = mock(TestScene.class);
		
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
		TestScene s = mock(TestScene.class);
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
		TestScene s = mock(TestScene.class);
		SceneDirector.changeScene(s);
		
		var r = mock(Renderer2D.class);
		SceneDirector.updateCurrentScene(mock(TimeRecord.class));
		verify(s, times(0)).draw2D(r);
		
		SceneDirector.drawCurrentScene2D(r);
		verify(s, times(1)).draw2D(r);
	}
	
	@Test
	public void drawingCurrentSceneCallsCurrentScenesDrawMethod3D() {
		TestScene s = mock(TestScene.class);
		SceneDirector.changeScene(s);
		
		var r = mock(Renderer3D.class);
		SceneDirector.updateCurrentScene(mock(TimeRecord.class));
		verify(s, times(0)).draw3D(r);
		
		SceneDirector.drawCurrentScene3D(r);
		verify(s, times(1)).draw3D(r);
	}
	
	@Test(expected = HexException.class)
	public void drawingCurrentScene2DWithNoSceneActiveThrowsException() {
		var r = mock(Renderer2D.class);
		SceneDirector.drawCurrentScene2D(r);
	}
	
	@Test(expected = HexException.class)
	public void drawingCurrentScene3DWithNoSceneActiveThrowsException() {
		var r = mock(Renderer3D.class);
		SceneDirector.drawCurrentScene3D(r);
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
	
	@Test
	public void pausingBlocksUpdates() {
		TestScene s = mock(TestScene.class);
		SceneDirector.changeScene(s);
		
		var time = mock(TimeRecord.class);
		SceneDirector.updateCurrentScene(time);
		verify(s, times(1)).update(time);
		
		SceneDirector.pause();
		
		time = mock(TimeRecord.class);
		SceneDirector.updateCurrentScene(time);
		verify(s, times(0)).update(time);
	}
	
	@Test
	public void resumingUnblocksUpdates() {
		TestScene s = mock(TestScene.class);
		SceneDirector.changeScene(s);
		
		SceneDirector.pause();
		SceneDirector.resume();
		
		var time = mock(TimeRecord.class);
		SceneDirector.updateCurrentScene(time);
		verify(s, times(1)).update(time);
	}
}
