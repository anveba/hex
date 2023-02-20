package engine.font;

public class FontCharacterData {

    private int x0;
    private int x1;
    private int y0;
    private int y1;
    private float advance;
    private float xOffset;
    private float yOffset;

    public FontCharacterData(int x0, int x1, int y0, int y1, float advance, float xOffset, float yOffset) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        this.advance = advance;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int x0() {
        return x0;
    }

    public int x1() {
        return x1;
    }

    public int y0() {
        return y0;
    }

    public int y1() {
        return y1;
    }

    public float advance() {
        return advance;
    }

    public float xOffset() {
        return xOffset;
    }

    public float yOffset() {
        return yOffset;
    }
}
