package main.hex.ui;

import main.engine.graphics.Colour;
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
    private ArrayList<Texture> hexTextures = new ArrayList<>();
    private Colour player1Col = Colour.White;
    private Colour player2Col = Colour.White;
    private Map<PlayerType, String> opponentTypeMap = new HashMap<>();
    private ArrayList<PlayerType> opponentTypes = new ArrayList<>();

    public void toggleSwapRule() {
        swapRule = !swapRule;
    }

    public void nextTexture(int playerIndex) {
        int i = playerTextureIndex[playerIndex];
        i = (i >= hexTextures.size() - 1) ?  0 : i + 1;
        setPlayerTextureIndex(playerIndex, i);
    }

    public void previousTexture(int playerIndex) {
        int i = playerTextureIndex[playerIndex];
        i = (i == 0) ?  hexTextures.size() - 1 : i - 1;
        setPlayerTextureIndex(playerIndex, i);
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

    public void setPlayerTextureIndex(int playerIndex, int textureIndex) {
        playerTextureIndex[playerIndex] = textureIndex;
    }

    public void setPlayerTypeIndex(int playerIndex, int typeIndex) {
        playerTypeIndex[playerIndex] = typeIndex;
    }

    public void addHexTexture(Texture texture) {
        hexTextures.add(texture);
    }

    public void addPlayerType(PlayerType type, String displayString) {
        opponentTypes.add(type);
        opponentTypeMap.put(type, displayString);
    }

    public Texture getHexTexture(int index) {
        return hexTextures.get(index);
    }

    public Texture getPlayerTexture(int playerIndex) {
        return hexTextures.get(playerTextureIndex[playerIndex]);
    }

    public PlayerType getPlayerType(int playerIndex) {
        return opponentTypes.get(playerTypeIndex[playerIndex]);
    }

    public String getPlayerTypeString(int playerIndex) {
        return opponentTypeMap.get(getPlayerType(playerIndex));
    }

    public int getHexTextureCount() {
        return hexTextures.size();
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

    public int getPlayerTextureIndex(int playerIndex) {
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

    public Colour getPlayer1Col() {
        return player1Col;
    }
    public void setPlayer1Col(Colour p1Col) {
        player1Col = p1Col;
    }

    public Colour getPlayer2Col() {
        return player2Col;
    }
    public void setPlayer2Col(Colour p2Col) {
        player2Col = p2Col;
    }
}
