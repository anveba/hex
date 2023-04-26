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
	private float radius;
	
	public CameraController(Camera camera) {
		this.camera = camera;
		angle = -(float)Math.PI * 0.5f;
		pitch = -(float)Math.PI * 0.25f;
    	radius = 10.0f;
	}

	@Override
	public void update(TimeRecord elapsed) {
		var listener = Game.getInstance().getControlsListener();

		float rotationSpeed = 1.5f * elapsed.elapsedSeconds();
		float zoomSpeed = 8.0f * elapsed.elapsedSeconds();
		
    	if (listener.isDown(Controls.A) || listener.isDown(Controls.LEFT_ARROW))
    		angle -= rotationSpeed;
    	if (listener.isDown(Controls.D) || listener.isDown(Controls.RIGHT_ARROW))
    		angle += rotationSpeed;
    	if (listener.isDown(Controls.W) || listener.isDown(Controls.UP_ARROW))
    		pitch -= rotationSpeed;
    	if (listener.isDown(Controls.S) || listener.isDown(Controls.DOWN_ARROW))
    		pitch += rotationSpeed;
    	if (listener.isDown(Controls.SPACE))
    		radius -= zoomSpeed;
    	if (listener.isDown(Controls.LEFT_SHIFT))
    		radius += zoomSpeed;
    	
    	pitch = Utility.clamp(pitch, -(float)Math.PI * 0.5f, -(float)Math.PI * 0.1f);
    	radius = Utility.clamp(radius, 5.0f, 25.0f);
    	
    	camera.setX((float)Math.cos(angle) * (float)Math.cos(pitch) * radius);
    	camera.setY(-(float)Math.sin(pitch) * radius);
    	camera.setZ(-(float)Math.sin(angle) * (float)Math.cos(pitch) * radius);
    	
    	camera.setYaw(angle + (float)Math.PI * 0.5f);
    	camera.setPitch(pitch);
	}
}
