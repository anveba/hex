package main.hex.ui;

import main.engine.graphics.Texture;
import main.engine.ui.TextField;
import main.hex.player.PlayerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StartGameFrameLogic {

    private boolean swapRule = false;
    private TextField[] playerNames = new TextField[2];
    private int[] playerTextureIndex = new int[2];
    private int[] playerTypeIndex = new int[2];
    private ArrayList<Texture> hexSkins = new ArrayList<>();
    private Map<PlayerType, String> opponentTypeMap = new HashMap<>();
    private ArrayList<PlayerType> opponentTypes = new ArrayList<>();

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

    public void nextPlayerType(int playerIndex) {
        int i = playerTypeIndex[playerIndex];
        i = (i >= opponentTypes.size() - 1) ?  0 : i + 1;
        setPlayerTypeIndex(playerIndex, i);
    }

    public void previousPlayerType(int playerIndex) {
        int i = playerTypeIndex[playerIndex];
        i = (i == 0) ?  opponentTypes.size() - 1 : i - 1;
        setPlayerTypeIndex(playerIndex, i);
    }

    public void setPlayerName(int playerIndex, TextField textField) {
        playerNames[playerIndex] = textField;
    }

    public void setPlayerSkinIndex(int playerIndex, int textureIndex) {
        playerTextureIndex[playerIndex] = textureIndex;
    }

    public void setPlayerTypeIndex(int playerIndex, int typeIndex) {
        playerTypeIndex[playerIndex] = typeIndex;
    }

    public void addHexSkin(Texture texture) {
        hexSkins.add(texture);
    }
    public void addPlayerType(PlayerType type, String displayString) {
        opponentTypes.add(type);
        opponentTypeMap.put(type, displayString);
    }

    public Texture getHexSkin(int index) {
        return hexSkins.get(index);
    }

    public Texture getPlayerSkin(int playerIndex) {
        return hexSkins.get(playerTextureIndex[playerIndex]);
    }

    public PlayerType getPlayerType(int playerIndex) {
        return opponentTypes.get(playerTypeIndex[playerIndex]);
    }

    public String getPlayerTypeString(int playerIndex) {
        return opponentTypeMap.get(getPlayerType(playerIndex));
    }

    public int getHexSkinCount() {
        return hexSkins.size();
    }
    public int getOpponentTypeCount() {
        return opponentTypes.size();
    }

    public String getOpponentTypeString(PlayerType type) {
        return opponentTypeMap.get(type);
    }

    public PlayerType getOpponentType(int index) {
        return opponentTypes.get(index);
    }

    public int getPlayerSkinIndex(int playerIndex) {
        return playerTextureIndex[playerIndex];
    }

    public int getPlayerTypeIndex(int playerIndex) {
        return playerTypeIndex[playerIndex];
    }

    public String getPlayerName(int playerIndex) {
        return playerNames[playerIndex].getText();
    }
    public boolean getSwapRule() {
        return swapRule;
    }
}
