package main.hex.resources;

import main.engine.ResourceManager;
import main.engine.graphics.Texture;

public enum TextureLibrary {
    LOGO("textures/gui/misc/logo.png"),
    WHITE_TILE("textures/board/white_tile.png"),
    WHITE_TILE_FULL("textures/board/white_tile_full.png"),
    ZEBRA_TILE("textures/board/zebra_tile.png"),
    BUBBLE_TILE("textures/board/large_bubble_tile.png"),
    ABSTRACT_TILE("textures/board/abstract_tile.png"),
    DUCK_TILE("textures/board/duck_tile.png"),
    GREY_SQUARE_BOX("textures/gui/BoxesBanners/Box_Square.png"),
    BANNER_GREY("textures/gui/BoxesBanners/Banner_Grey.png"),
    GREEN_YES_BUTTON("textures/gui/ButtonsText/PremadeButtons_YesGreen.png"),
    ORANGE_NO_BUTTON("textures/gui/ButtonsText/PremadeButtons_No.png"),
    LEFT_CAROUSEL_ARROW("textures/gui/Sliders/ScrollSlider_Blank_Arrow_Left.png"),
    RIGHT_CAROUSEL_ARROW("textures/gui/Sliders/ScrollSlider_Blank_Arrow_Right.png"),
    SCROLLBAR_GREY("textures/gui/Sliders/ScrollBar_WhiteOutline_Base.png"),
    SCROLLBAR_BUTTON_GREY("textures/gui/Sliders/ScrollBar_Button.png"),
    LARGE_MENU_GREY("textures/gui/Icons/Icon_Large_Menu_Grey.png"),
    SMALL_UNDO_GREY("textures/gui/Icons/Icon_Small_Undo_Grey.png"),
    WIDE_BLANK_BACKGROUND("textures/gui/Sliders/WideSlider_Blank_Background.png"),
    WHITE_SQUARE("textures/white_px.png"),
    BOX_WHITE_OUTLINE_ROUNDED("textures/gui/BoxesBanners/Box_WhiteOutline_Rounded.png"),
    BUTTON_TEXT_LARGE_ORANGE_ROUND("textures/gui/ButtonsText/ButtonText_Large_Orange_Round.png"),
    PREMADE_BUTTONS_X_WHITE_OUTLINE("textures/gui/ButtonsText/PremadeButtons_XWhiteOutline.png"),
    BUTTON_TEXT_LARGE_RED_ROUND("textures/gui/ButtonsText/ButtonText_Large_Red_Round.png"),

    BUTTON_TEXT_LARGE_SQUARE("textures/gui/ButtonsText/ButtonText_Large_Square.png"),
    BACKGROUND_SQUARE("textures/gui/ButtonsIcons/IconButton_Large_Background_Square.png");

    private String texturePath;
    TextureLibrary(String texturePath) {
        this.texturePath = texturePath;
    }

    public Texture getTexture() {
        return ResourceManager.getInstance().loadTexture(texturePath);
    }

}