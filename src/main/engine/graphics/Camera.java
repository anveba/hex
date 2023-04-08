package main.engine.graphics;

import main.engine.EngineException;

public class Camera {
	
	private float x, y, z;
	private float yaw, pitch, roll;
	private float near, far;
	private float verticalFOV;
	
	public Camera(float near, float far, float verticalFOV) {
		this(0.0f, 0.0f, 0.0f, near, far, verticalFOV);
	}
	
	public Camera(float x, float y, float z,
			float near, float far, float verticalFOV) {
		setX(x);
		setY(y);
		setZ(z);
		setPlanes(near, far);
		setVerticalFOV(verticalFOV);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getRoll() {
		return roll;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public float getNear() {
		return near;
	}

	public float getFar() {
		return far;
	}

	public void setPlanes(float near, float far) {
		if (near >= far)
			throw new EngineException("Invalid plane distances.");
		this.near = near;
		this.far = far;
	}

	public float getVerticalFOV() {
		return verticalFOV;
	}

	public void setVerticalFOV(float verticalFOV) {
		if (verticalFOV <= 0.0f)
			throw new EngineException("Invalid FOV.");
		this.verticalFOV = verticalFOV;
	}
	
}
