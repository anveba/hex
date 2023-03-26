package main.hex.ui;

import main.engine.graphics.Texture;
import main.engine.ui.TextField;

import java.util.ArrayList;

public class StartGameFrameLogic {

    private boolean swapRule = false;
    private TextField[] playerNames = new TextField[2];
    private int[] playerTextureIndex = new int[2];
    private ArrayList<Texture> hexSkins = new ArrayList<>();

    public void toggleSwapRule() {
        swapRule = !swapRule;
    }

    public void nextSkin(int playerIndex) {
        int i = playerTextureIndex[playerIndex];
        i = (i >= hexSkins.size() - 1) ?  0 : i + 1;
        setPlayerSkinIndex(playerIndex, i);
        System.out.println(playerTextureIndex[playerIndex]);
    }

    public void previousSkin(int playerIndex) {
        int i = playerTextureIndex[playerIndex];
        i = (i == 0) ?  hexSkins.size() - 1 : i - 1;
        setPlayerSkinIndex(playerIndex, i);
    }

    public void setPlayerName(int playerIndex, TextField textField) {
        playerNames[playerIndex] = textField;
    }

    public void setPlayerSkinIndex(int playerIndex, int textureIndex) {
        playerTextureIndex[playerIndex] = textureIndex;
    }

    public void addHexSkin(Texture texture) {
        hexSkins.add(texture);
    }

    public Texture getHexSkin(int index) {
        return hexSkins.get(index);
    }

    public Texture getPlayerSkin(int playerIndex) {
        return hexSkins.get(playerTextureIndex[playerIndex]);
    }
    public int getHexSkinCount() {
        return hexSkins.size();
    }

    public int getPlayerSkinIndex(int playerIndex) {
        return playerTextureIndex[playerIndex];
    }

    public String getPlayerName(int playerIndex) {
        return playerNames[playerIndex].getText();
    }
    public boolean getSwapRule() {
        return swapRule;
    }
}
