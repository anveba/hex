package main.engine.ui.callback;

/**
 * Immutable.
 *
 */
public class SliderCallbackArgs {
	
	private int sliderLevel;
	
	public SliderCallbackArgs(int sliderLevel) {
		this.sliderLevel = sliderLevel;
	}
	
	public int getSliderLevel() {
		return sliderLevel;
	}
}
