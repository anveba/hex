package main.hex;

import main.engine.TimeRecord;
import main.engine.Utility;
import main.engine.graphics.Camera;
import main.engine.input.*;
import main.engine.math.Vector3;

public class CameraController implements Updateable {

	private Camera camera;
	
	private float angle;
	private float pitch;
	
	public CameraController(Camera camera) {
		this.camera = camera;
		angle = -(float)Math.PI * 0.5f;
		pitch = -(float)Math.PI * 0.25f;
	}

	public void onControlsInput(ControlsArgs args) {
		float move = 1.0f;
		float rot = 0.5f;
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

	@Override
	public void update(TimeRecord elapsed) {
		var listener = Game.getInstance().getControlsListener();
		
		/*
		float moveSpeed = 10.0f * elapsed.elapsedSeconds();
    	float rotationSpeed = 0.03f;
    	
    	Vector3 translation = new Vector3();
    	Vector3 rotation = new Vector3();
    	
    	if (listener.isDown(Controls.W))
    		translation.z -= moveSpeed;
    	if (listener.isDown(Controls.S))
    		translation.z += moveSpeed;
    	if (listener.isDown(Controls.A))
    		translation.x -= moveSpeed;
    	if (listener.isDown(Controls.D))
    		translation.x += moveSpeed;
    	if (listener.isDown(Controls.SPACE))
    		translation.y += moveSpeed;
    	if (listener.isDown(Controls.LEFT_CONTROL))
    		translation.y -= moveSpeed;
    	
    	if (listener.isDown(Controls.LEFT_ARROW))
    		rotation.x += rotationSpeed;
    	if (listener.isDown(Controls.RIGHT_ARROW))
    		rotation.x -= rotationSpeed;
    	if (listener.isDown(Controls.UP_ARROW))
    		rotation.y += rotationSpeed;
    	if (listener.isDown(Controls.DOWN_ARROW))
    		rotation.y -= rotationSpeed;
    	
    	camera.setX(camera.getX() + translation.x);
    	camera.setY(camera.getY() + translation.y);
    	camera.setZ(camera.getZ() + translation.z);
    	
    	camera.setYaw(camera.getYaw() + rotation.x);
    	camera.setPitch(camera.getPitch() + rotation.y);
    	 */

		float rotationSpeed = 1.5f * elapsed.elapsedSeconds();
		
    	if (listener.isDown(Controls.A) || listener.isDown(Controls.LEFT_ARROW))
    		angle -= rotationSpeed;
    	if (listener.isDown(Controls.D) || listener.isDown(Controls.RIGHT_ARROW))
    		angle += rotationSpeed;
    	if (listener.isDown(Controls.W) || listener.isDown(Controls.UP_ARROW))
    		pitch -= rotationSpeed;
    	if (listener.isDown(Controls.S) || listener.isDown(Controls.DOWN_ARROW))
    		pitch += rotationSpeed;
    	
    	pitch = Utility.clamp(pitch, -(float)Math.PI * 0.5f, -(float)Math.PI * 0.1f);
    	
    	float radius = 10.0f;
    	
    	camera.setX((float)Math.cos(angle) * (float)Math.cos(pitch) * radius);
    	camera.setY(-(float)Math.sin(pitch) * radius);
    	camera.setZ(-(float)Math.sin(angle) * (float)Math.cos(pitch) * radius);
    	
    	camera.setYaw(angle + (float)Math.PI * 0.5f);
    	camera.setPitch(pitch);
	}
}
