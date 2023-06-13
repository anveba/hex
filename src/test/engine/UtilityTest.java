package test.engine;

import static main.engine.Utility.*;
import static org.junit.Assert.*;

import org.junit.Test;

import main.engine.EngineException;
import main.engine.TimeRecord;
import main.engine.Utility;

public class UtilityTest {
	@Test
    public void clampingValueLessThanLowerBoundReturnsLowerBound() {
        float lower = -4.0f, upper = 6.2f;
        float val = -9.0f;
        float delta = 0.001f;
        assertEquals(lower, Utility.clamp(val, lower, upper), delta);
    }
	
	@Test
    public void clampingValueLargerThanUpperBoundReturnsUpperBound() {
        float lower = -4.0f, upper = 6.2f;
        float val = 9.0f;
        float delta = 0.001f;
        assertEquals(upper, Utility.clamp(val, lower, upper), delta);
    }
	
	@Test
    public void clampingValueBetweenBoundsReturnsValue() {
        float lower = -4.0f, upper = 6.2f;
        float val = 2.6f;
        float delta = 0.001f;
        assertEquals(val, Utility.clamp(val, lower, upper), delta);
    }
	
	@Test
    public void lowerBoundLargerThanUpperBoundYieldsTheSameAsIfTheyWereSwapped() {
        final float lower = 4.0f, upper = -6.2f;
        float val = 2.0f;
        final float delta = 0.001f;
        assertEquals(val, Utility.clamp(val, lower, upper), delta);
        val = 9.2f;
        assertEquals(lower, Utility.clamp(val, lower, upper), delta);
        val = -60.8f;
        assertEquals(upper, Utility.clamp(val, lower, upper), delta);
    }
	
	@Test
    public void lerpOfZeroYieldsLowerBound() {
        final float lower = -3.2f, upper = 0.6f;
        final float t = 0.0f;
        final float delta = 0.001f;
        assertEquals(lower, Utility.lerp(t, lower, upper), delta);
    }
	
	@Test
    public void lerpOfOneYieldsUpperBound() {
        final float lower = -3.2f, upper = 0.6f;
        final float t = 1.0f;
        final float delta = 0.001f;
        assertEquals(upper, Utility.lerp(t, lower, upper), delta);
    }
	
	@Test
    public void lerpBetweenBoundsLinearlyInterpolates() {
        final float lower = 1.0f, upper = 3.0f;
        final float t = 0.5f;
        final float expected = 2.0f;
        final float delta = 0.001f;
        assertEquals(expected, Utility.lerp(t, lower, upper), delta);
    }
	
	@Test(expected = EngineException.class)
    public void lerpWithValueLessThanZeroThrowsException() {
        Utility.lerp(-1.0f, 0.0f, 0.0f);
    }
	
	@Test(expected = EngineException.class)
    public void lerpWithValueLargerThanOneThrowsException() {
        Utility.lerp(2.0f, 0.0f, 0.0f);
    }

    @Test
    public void testGetFormattedTime() {
        int seconds = 100;
        assertEquals("01:40", Utility.getFormattedTime(seconds));
    }
}
