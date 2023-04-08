package main.hex;

import main.engine.graphics.Camera;
import main.engine.input.*;

public class CameraController implements ControlsCallback {

	private Camera camera;
	
	public CameraController(Camera camera) {
		this.camera = camera;
	}
	
	public void enable() {
        Game.getInstance().getControlsListener().addOnAnyPressCallback(this);
	}
	
	public void disable() {
		Game.getInstance().getControlsListener().removeOnAnyPressCallback(this);
	}

	@Override
	public void onControlsInput(ControlsArgs args) {
		float move = 0.8f;
    	float rot = 0.1f;
    	
    	switch (args.getControls()) {
    	case W:
    		camera.setZ(camera.getZ() - move);
    		break;
    	case A:
    		camera.setX(camera.getX() - move);
    		break;
    	case D:
    		camera.setX(camera.getX() + move);
    		break;
    	case S:
    		camera.setZ(camera.getZ() + move);
    		break;
    	case SPACE:
    		camera.setY(camera.getY() + move);
    		break;
    	case LEFT_CONTROL:
    		camera.setY(camera.getY() - move);
    		break;
    		
    	case LEFT_ARROW:
    		camera.setYaw(camera.getYaw() + rot);
    		break;
    	case RIGHT_ARROW:
    		camera.setYaw(camera.getYaw() - rot);
    		break;
    	case UP_ARROW:
    		camera.setPitch(camera.getPitch() + rot);
    		break;
    	case DOWN_ARROW:
    		camera.setPitch(camera.getPitch() - rot);
    		break;
		default:
			break;
    	}
	}
}
