package main.hex.resources;

import main.engine.graphics.Texture;
import main.engine.io.ResourceManager;

/**
 * This enum is used to store all the textures used in the game.
 * This is done to avoid having to load the same texture multiple times, but also to abstract the texture paths
 * away from the rest of the code.
 *
 * This class is not tested, as it is only a collection of constants.
 *
 * @Author Oliver Grønborg Christensen - s204479
 */


public enum TextureLibrary {
    LIGHT_GREY_TILE("textures/board/light_grey_tile.png"),
    BORDER("textures/board/border.png"),
    LEFT_BORDER_CORNER("textures/board/left_border_corner.png"),
    RIGHT_BORDER_CORNER("textures/board/right_border_corner.png"),
    GREY_SQUARE_BOX("textures/gui/BoxesBanners/Box_Square.png"),
    BANNER_GREY("textures/gui/BoxesBanners/Banner_Grey.png"),
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
    BUTTON_TEXT_LARGE_GREEN_ROUND("textures/gui/ButtonsText/ButtonText_Large_Green_Round.png"),
    PREMADE_BUTTONS_X_WHITE_OUTLINE("textures/gui/ButtonsText/PremadeButtons_XWhiteOutline.png"),
    BUTTON_TEXT_LARGE_RED_ROUND("textures/gui/ButtonsText/ButtonText_Large_Red_Round.png"),
    BUTTON_TEXT_LARGE_SQUARE("textures/gui/ButtonsText/ButtonText_Large_Square.png"),
    BUTTON_TEXT_LARGE_OUTLINE_ROUND("textures/gui/ButtonsText/ButtonText_Large_GreyOutline_Round.png"),
    BACKGROUND_SQUARE("textures/gui/ButtonsIcons/IconButton_Large_Background_Square.png"),
    HEX_LOGO("textures/gui/misc/HEX_Logo_V2.png"),
    TOGGLE_SWITCH_FOREGROUND("textures/gui/ButtonsText/ButtonText_OnOffButton.png"),
    TOGGLE_SWITCH_BACKGROUND("textures/gui/ButtonsText/ButtonText_OnOffBackground.png"),
    BOX_ORANGE_ROUNDED("textures/gui/BoxesBanners/Box_Orange_Rounded.png"),
    BUTTON_LARGE_ORANGE_SQUARE("textures/gui/ButtonsText/ButtonText_Large_Orange_Square.png"),
	WHITE_PX("textures/white_px.png"),
    BACKGROUND_TILE_GREYSCALE("textures/background_tile_greyscale.png"),
    BUTTON_LARGE_GREEN_SQUARE("textures/gui/ButtonsText/ButtonText_Large_Green_Square.png"),
    BLANK_ARROW_LEFT("textures/gui/Icons/Icon_Small_WhiteOutline_Arrow.png"),
    MENU_BUTTON("textures/gui/ButtonsText/PremadeButtons_Menu.png"),
    BOX_ORANGE_SQUARE("textures/gui/BoxesBanners/Box_Orange_Square.png");

    private String texturePath;
    TextureLibrary(String texturePath) {
        this.texturePath = texturePath;
    }

    public Texture getTexture() {
        return ResourceManager.getInstance().loadTexture(texturePath);
    }

}