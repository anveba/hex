package main.engine.ui.callback;

/**
 * Immutable.
 * @author Andreas - s214971
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
