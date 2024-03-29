package main.engine.ui;

import main.engine.TimeRecord;
import main.engine.graphics.Colour;
import main.engine.graphics.Renderer2D;
import main.engine.graphics.Texture;

import java.util.ArrayList;

/**
 * The animated background in the menus.
 * The background is made up of tiles with new ones being added when needed (about to be seen by the user)
 * and old ones being deleted when no longer needed (can no longer be seen by the user).
 *
 * @author Oliver Siggaard - s204450
 */

public class Background extends UIElement {
    
    private float x;
    private float y;
    private final float xSpeed;
    private final float ySpeed;
    private final float backgroundTileWidth;
    private final float backgroundTileHeight;
    private final Texture texture;
    private final Colour colour;
    private final ArrayList<Image> backgroundTiles = new ArrayList<>();
    private float aspectRatio;

    public Background(float x, float y, float xSpeed, float ySpeed, Texture texture, Colour colour) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.backgroundTileWidth = (float) texture.width() / 800.0f;
        this.backgroundTileHeight = (float) texture.height() / 800.0f;
        this.texture = texture;
        this.colour = colour;
        Image startTile = new Image(x, y, backgroundTileWidth, backgroundTileHeight, texture, colour);
        backgroundTiles.add(startTile);
    }

    private boolean tileExistsAtPosition(float x, float y) {
        for (Image backgroundTile : backgroundTiles) {
            if (Math.abs(x - backgroundTile.getX()) <= 0.01f && Math.abs(y - backgroundTile.getY()) <= 0.01f) {
                return true;
            }
        }
        return false;
    }

    private Image getTileAtPosition(float x, float y) {
        for (Image backgroundTile : backgroundTiles) {
            if (Math.abs(x - backgroundTile.getX()) <= 0.01f && Math.abs(y - backgroundTile.getY()) <= 0.01f) {
                return backgroundTile;
            }
        }
        return null;
    }

    private void updateBackground() {
        if (backgroundTiles.size() <= 500) {
            createNewTiles();
        }
        deleteUnusedTiles();
    }

    private void createNewTiles() {
        ArrayList<Image> newTiles = new ArrayList<>();
        for (Image backgroundTile : backgroundTiles) {
            if (backgroundTile.getX() - backgroundTileWidth / 2 >= -aspectRatio) {
                if (!tileExistsAtPosition(backgroundTile.getX() - backgroundTileWidth, backgroundTile.getY())) {
                    Image bt = new Image(backgroundTile.getX() - backgroundTileWidth, backgroundTile.getY(),
                            backgroundTileWidth, backgroundTileHeight, texture, colour);
                    newTiles.add(bt);
                }
            }

            if (backgroundTile.getX() + backgroundTileWidth / 2 <= aspectRatio) {
                if (!tileExistsAtPosition(backgroundTile.getX() + backgroundTileWidth, backgroundTile.getY())) {
                    Image bt = new Image(backgroundTile.getX() + backgroundTileWidth, backgroundTile.getY(),
                            backgroundTileWidth, backgroundTileHeight, texture, colour);
                    newTiles.add(bt);
                }
            }

            if (backgroundTile.getY() - backgroundTileHeight / 2 >= -1.0f) {
                if (!tileExistsAtPosition(backgroundTile.getX(), backgroundTile.getY() - backgroundTileHeight)) {
                    Image bt = new Image(backgroundTile.getX(), backgroundTile.getY() - backgroundTileHeight,
                            backgroundTileWidth, backgroundTileHeight, texture, colour);
                    newTiles.add(bt);
                }
            }

            if (backgroundTile.getY() + backgroundTileHeight / 2 <= 1.0f) {
                if (!tileExistsAtPosition(backgroundTile.getX(), backgroundTile.getY() + backgroundTileHeight)) {
                    Image bt = new Image(backgroundTile.getX(), backgroundTile.getY() + backgroundTileHeight,
                            backgroundTileWidth, backgroundTileHeight, texture, colour);
                    newTiles.add(bt);
                }
            }
        }
        backgroundTiles.addAll(newTiles);
    }

    private void deleteUnusedTiles() { // Removing tiles that can no longer be seen
        ArrayList<Image> unusedTiles = new ArrayList<>();
        for (Image backgroundTile : backgroundTiles) {
            if (backgroundTile.getX() + backgroundTileWidth / 2 < -aspectRatio) {
                unusedTiles.add(getTileAtPosition(backgroundTile.getX(), backgroundTile.getY()));
            }

            if (backgroundTile.getX() - backgroundTileWidth / 2 > aspectRatio) {
                unusedTiles.add(getTileAtPosition(backgroundTile.getX(), backgroundTile.getY()));
            }

            if (backgroundTile.getY() + backgroundTileHeight / 2 < -1.0f) {
                unusedTiles.add(getTileAtPosition(backgroundTile.getX(), backgroundTile.getY()));
            }

            if (backgroundTile.getY() - backgroundTileHeight / 2 > 1.0f) {
                unusedTiles.add(getTileAtPosition(backgroundTile.getX(), backgroundTile.getY()));
            }
        }
        backgroundTiles.removeAll(unusedTiles);
    }

    public float getXSpeed() {
        return xSpeed;
    }

    public float getYSpeed() {
        return ySpeed;
    }

    public float getBackgroundTileWidth() {
        return backgroundTileWidth;
    }

    public float getBackgroundTileHeight() {
        return backgroundTileHeight;
    }

    public Texture getTexture() {
        return texture;
    }

    public Colour getColour() {
        return colour;
    }

    public int getNoOfTiles() {
        return backgroundTiles.size();
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void drawElement(Renderer2D renderer, float offsetX, float offsetY, Colour colour) {
        setAspectRatio((float) renderer.context.getViewportWidth() / renderer.context.getViewportHeight());
        for (Image backgroundTile : backgroundTiles) {
            backgroundTile.draw(renderer, 0.0f, 0.0f, colour);
        }
    }

    @Override
    public void updateElement(TimeRecord elapsed) {
        for (Image backgroundTile : backgroundTiles) {
            backgroundTile.setPosition(backgroundTile.getX() + xSpeed * elapsed.elapsedSeconds(),
                    backgroundTile.getY() + ySpeed * elapsed.elapsedSeconds());
        }
        updateBackground();
    }
}