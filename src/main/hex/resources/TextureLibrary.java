package main.hex.resources;

import main.engine.ResourceManager;
import main.engine.graphics.Texture;

public enum TextureLibrary {
    LOGO("textures/gui/misc/logo.png"),
    WHITE_TILE("textures/board/white_tile.png"),
    ZEBRA_TILE("textures/board/zebra_tile.png"),
    GREY_SQUARE_BOX("textures/gui/BoxesBanners/Box_Square.png"),
    BANNER_GREY("textures/gui/BoxesBanners/Banner_Grey.png"),
    ORANGE_BUTTON("textures/gui/ButtonsText/ButtonText_Large_Orange_Round.png"),
    GREEN_YES_BUTTON("textures/gui/ButtonsText/PremadeButtons_YesGreen.png"),
    ORANGE_NO_BUTTON("textures/gui/ButtonsText/PremadeButtons_No.png"),
    LEFT_CAROUSEL_ARROW("textures/gui/Sliders/ScrollSlider_Blank_Arrow_Left.png"),
    RIGHT_CAROUSEL_ARROW("textures/gui/Sliders/ScrollSlider_Blank_Arrow_Right.png"),
    LARGE_MENU_GREY("textures/gui/Icons/Icon_Large_Menu_Grey.png"),
    SMALL_UNDO_GREY("textures/gui/Icons/Icon_Small_Undo_Grey.png"),
    BACKGROUND_SQUARE("textures/gui/ButtonsIcons/IconButton_Large_Background_Square.png");

    private String texturePath;
    TextureLibrary(String texturePath) {
        this.texturePath = texturePath;
    }

    public Texture getTexture() {
        return ResourceManager.getInstance().loadTexture(texturePath);
    }

}