package main.hex;

import main.engine.ResourceManager;
import main.engine.TimeRecord;
import main.engine.Utility;
import main.engine.graphics.Camera;
import main.engine.input.*;
import main.engine.math.Vector3;
import main.engine.sound.SoundInstance;
import main.engine.sound.SoundPlayer;

public class CameraController implements Updateable {

	private Camera camera;
	
	private float angle;
	private float pitch;
	private float radius;
	
	private float angleVelocity;
	private float pitchVelocity;
	private float zoomVelocity;
	
	private final float maxRotVelocity = 1.5f;
	private final float maxZoomVelocity = 20.0f;
	
	public CameraController(Camera camera) {
		this.camera = camera;
		angle = -(float)Math.PI * 0.5f;
		pitch = -(float)Math.PI * 0.25f;
    	radius = 10.0f;
    	angleVelocity = 0.0f;
    	pitchVelocity = 0.0f;
    	zoomVelocity = 0.0f;
	}
	
	@Override
	public void update(TimeRecord elapsed) {

		final float rotationAcceleration = 18.0f * elapsed.elapsedSeconds();
		final float zoomAcceleration = 150.0f * elapsed.elapsedSeconds();
		
		applyDeaccleration(elapsed, rotationAcceleration * 0.7f, zoomAcceleration * 0.7f);
		
		
		applyAcceleration(elapsed, rotationAcceleration, zoomAcceleration);
    	
		final float minZoom = 5.0f;
		final float maxZoom = 30.0f;
		final float minPitch = -(float)Math.PI * 0.05f; 
		final float maxPitch = -(float)Math.PI * 0.5f;
		
    	applyVelocity(elapsed, minZoom, maxZoom, minPitch, maxPitch);
	}

	private void applyDeaccleration(TimeRecord elapsed, 
			final float rotDeacceleration, final float zoomDeacceleration) {
		
		final float bias = 0.1f;
		
		{
			final float angleVelSign = Math.signum(angleVelocity);
			angleVelocity -= angleVelSign * rotDeacceleration * 
					(Math.abs(angleVelocity) / maxRotVelocity + bias * elapsed.elapsedSeconds());
			if (angleVelocity * angleVelSign < 0.0f)
				angleVelocity = 0.0f;
		}
		
		{
			final float pitchVelSign = Math.signum(pitchVelocity);
			pitchVelocity -= pitchVelSign * rotDeacceleration * 
					(Math.abs(pitchVelocity) / maxRotVelocity + bias * elapsed.elapsedSeconds());
			if (pitchVelocity * pitchVelSign < 0.0f)
				pitchVelocity = 0.0f;
		}
		
		{
			final float zoomVelSign = Math.signum(zoomVelocity);
			zoomVelocity -= zoomVelSign * zoomDeacceleration * 
					(Math.abs(zoomVelocity) / maxZoomVelocity + bias * elapsed.elapsedSeconds());
			if (zoomVelocity * zoomVelSign < 0.0f)
				zoomVelocity = 0.0f;
		}
		
	}

	private void applyAcceleration(TimeRecord elapsed, 
			final float rotationAcceleration, final float zoomAcceleration) {
		
		final var listener = Game.getInstance().getControlsListener();
		
    	if (listener.isDown(Controls.A) || listener.isDown(Controls.LEFT_ARROW))
    		angleVelocity -= rotationAcceleration;
    	if (listener.isDown(Controls.D) || listener.isDown(Controls.RIGHT_ARROW))
    		angleVelocity += rotationAcceleration;
    	if (listener.isDown(Controls.W) || listener.isDown(Controls.UP_ARROW))
    		pitchVelocity -= rotationAcceleration;
    	if (listener.isDown(Controls.S) || listener.isDown(Controls.DOWN_ARROW))
    		pitchVelocity += rotationAcceleration;
    	if (listener.isDown(Controls.SPACE))
    		zoomVelocity -= zoomAcceleration;
    	if (listener.isDown(Controls.LEFT_SHIFT))
    		zoomVelocity += zoomAcceleration;
    	
    	angleVelocity = Utility.clamp(angleVelocity, -maxRotVelocity, maxRotVelocity);
    	pitchVelocity = Utility.clamp(pitchVelocity, -maxRotVelocity, maxRotVelocity);
    	zoomVelocity = Utility.clamp(zoomVelocity, -maxZoomVelocity, maxZoomVelocity);
	}

	private void applyVelocity(TimeRecord elapsed, 
			final float minZoom, final float maxZoom,
			final float minPitch, final float maxPitch) {
		pitch += pitchVelocity * elapsed.elapsedSeconds();
    	angle += angleVelocity * elapsed.elapsedSeconds();
    	radius += zoomVelocity * elapsed.elapsedSeconds();
    	
    	pitch = Utility.clamp(pitch, minPitch, maxPitch);
    	radius = Utility.clamp(radius, minZoom, maxZoom);
    	
    	camera.setX((float)Math.cos(angle) * (float)Math.cos(pitch) * radius);
    	camera.setY(-(float)Math.sin(pitch) * radius);
    	camera.setZ(-(float)Math.sin(angle) * (float)Math.cos(pitch) * radius);
    	
    	camera.setYaw(angle + (float)Math.PI * 0.5f);
    	camera.setPitch(pitch);
    	camera.setRoll(0.0f);
	}
}
