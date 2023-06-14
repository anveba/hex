package main.hex.ui;

import main.engine.graphics.Colour;
import main.engine.graphics.Texture;
import main.engine.ui.Slider;
import main.engine.ui.TextField;
import main.hex.player.PlayerType;
import main.hex.resources.SkinDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for the logic behind the StartGameFrame, but also acts as the model, containing all the data.
 * There has been taken inspiration from the MVC pattern, but it is not a full implementation, as in this case,
 * the model and the controller are the same class. This could however be easily achieved by extracting the private
 * fields into a separate class, and creating the belonging getters and setters.
 *
 * @Author Oliver Grønborg Christensen - s204479
 * @Author Oliver Siggaard - s204450 (Texture/Color caoursel)
 */
public class StartGameFrameLogic {

    private boolean swapRule = false;
    private TextField[] playerNames = new TextField[2];
    private Slider boardSizeSlider;
    private Slider turnTimeSlider;
    private int[] playerTextureIndex = new int[2];
    private int[] playerColourIndex = new int[2];
    private int[] playerTypeIndex = new int[2];
    private List<Integer> hexTextures = new ArrayList<>();
    private ArrayList<Colour> hexColours = new ArrayList<>();
    private Map<Integer, String> hexTextureMap = new HashMap<>();
    private Map<Colour, String> hexColourMap = new HashMap<>();
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
        i = (i == 0) ? hexTextures.size() - 1 : i - 1;
        setPlayerTextureIndex(playerIndex, i);
    }

    public void nextColour(int playerIndex) {
        int playerCol = playerColourIndex[playerIndex];
        for (int i = 0; i < playerColourIndex.length; i++) {
            if (i != playerIndex) {
                int otherPlayerCol = playerColourIndex[i];
                if (playerCol == hexColours.size() - 1) {
                    if (otherPlayerCol == 0) {
                        playerCol = 1;
                    } else {
                        playerCol = 0;
                    }
                } else if (playerCol + 1 == otherPlayerCol) {
                    if (otherPlayerCol == hexColours.size() - 1) {
                        playerCol = 0;
                    } else {
                        playerCol += 2;
                    }
                } else {
                    playerCol++;
                }
            }
        }
        setPlayerColourIndex(playerIndex, playerCol);
    }

    public void previousColour(int playerIndex) {
        int playerCol = playerColourIndex[playerIndex];
        for (int i = 0; i < playerColourIndex.length; i++) {
            if (i != playerIndex) {
                int otherPlayerCol = playerColourIndex[i];
                if (playerCol == 0) {
                    if (otherPlayerCol == hexColours.size() - 1) {
                        playerCol = hexColours.size() - 2;
                    } else {
                        playerCol = hexColours.size() - 1;
                    }
                } else if (playerCol - 1 == otherPlayerCol) {
                    if (otherPlayerCol == 0) {
                        playerCol = hexColours.size() - 1;
                    } else {
                        playerCol -= 2;
                    }
                } else {
                    playerCol--;
                }
            }
        }
        setPlayerColourIndex(playerIndex, playerCol);
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

    public void setPlayerColourIndex(int playerIndex, int colourIndex) {
        playerColourIndex[playerIndex] = colourIndex;
    }

    public void setPlayerTypeIndex(int playerIndex, int typeIndex) {
        playerTypeIndex[playerIndex] = typeIndex;
    }

    public void addHexTextureId(int textureId, String displayString) {
        hexTextures.add(textureId);
        hexTextureMap.put(textureId, displayString);
    }

    public void addHexColour(Colour colour, String displayString) {
        hexColours.add(colour);
        hexColourMap.put(colour, displayString);
    }

    public void addPlayerType(PlayerType type, String displayString) {
        opponentTypes.add(type);
        opponentTypeMap.put(type, displayString);
    }

    public int getHexTextureId(int index) {
        return hexTextures.get(index);
    }

    public int getPlayerTextureId(int playerIndex) {
        return getHexTextureId(playerTextureIndex[playerIndex]);
    }

    public String getPlayerTextureName(int playerIndex) {
        return hexTextureMap.get(getPlayerTextureId(playerIndex));
    }

    public Colour getHexColour(int index) {
        return hexColours.get(index);
    }

    public Colour getPlayerColour(int playerIndex) {
        return hexColours.get(playerColourIndex[playerIndex]);
    }

    public String getPlayerColourString(int playerIndex) {
        return hexColourMap.get(getPlayerColour(playerIndex));
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

    public int getHexColourCount() {
        return hexColours.size();
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

    public int getPlayerColourIndex(int playerIndex) {
        return playerColourIndex[playerIndex];
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

    public void setBoardSizeSlider(Slider boardSizeSlider) {
        this.boardSizeSlider = boardSizeSlider;
    }

    public void setTurnTimeSlider(Slider turnTimeSlider) {
        this.turnTimeSlider = turnTimeSlider;
    }
    public int getBoardSize() {
        return boardSizeSlider.getCurrent();
    }
    public int getTurnTime() {
        return turnTimeSlider.getCurrent();
    }
}