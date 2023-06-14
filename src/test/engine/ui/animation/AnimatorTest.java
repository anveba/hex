package test.engine.ui.animation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.*;

import main.engine.TimeRecord;
import main.engine.ui.UIElement;
import main.engine.ui.animation.*;
import main.engine.ui.animation.easing.CubicInOut;

public class AnimatorTest {
	@Before
	public void setup() {
		
	}
	
	@Test
	public void runningAnimationUntilDoneMarksAnimatorAsDone() {
		final float time = 2.0f;
		AnimationSequence seq = new AnimationSequence(new Wait(time));
		Animator anim = new Animator(seq);
		assertFalse(anim.done());
		final float delta = 0.001f;
		anim.animate(new TimeRecord(time + delta, time + delta));
		assertTrue(anim.done());
	}
	
	@Test
	public void finishingAnimationCallsAnimationEndCallback() {
		final float time = 2.0f;
		Runnable callback = mock(Runnable.class);
		UIElement e = mock(UIElement.class);
		AnimationSequence seq = new AnimationSequence(new Show(e),
				new SetPosition(e, 0.0f, 0.0f),
				new AnimationSequence(new Ease(e, new CubicInOut(),
				0.0f, 0.0f, 0.0f, 0.0f,
				time), new Hide(e)));
		seq.setOnEndAction(callback);
		Animator anim = new Animator(seq);
		verify(callback, times(0)).run();
		final float delta = 0.001f;
		anim.animate(new TimeRecord(0.0f, 0.0f));
		verify(callback, times(0)).run();
		anim.animate(new TimeRecord(time + delta, time + delta));
		verify(callback, times(1)).run();
	}
}
