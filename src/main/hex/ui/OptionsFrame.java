package main.hex.ui;

import main.engine.font.BitmapFont;
import main.engine.graphics.Colour;
import main.engine.io.ResourceManager;
import main.engine.ui.*;
import main.hex.resources.TextureLibrary;


/**
 *
 * The options frame is used to change the settings of the game.
 * This frame can be reached from the main menu, and through the pause menu while in a game.
 * Attached to this frame is a logic class "OptionsFrameLogic", which handles the logic of the frame.
 *
 * This class is not tested, as it is mainly consisting of UI, which is tested by inspection.
 *
 * @Author Oliver Grønborg Christensen - s204479
 */

public class OptionsFrame extends Frame {

    private OptionsFrameLogic logic;

    private BitmapFont FONT_FREDOKA_ONE = ResourceManager.getInstance().loadFont("fonts/fredoka-one.one-regular.ttf");
    private final float BANNER_FONT_SIZE = 0.18f;
    private final float SETTING_HEADER_FONT_SIZE = 0.10f;
    private final float SETTING_FONT_SIZE = 0.075f;
    private final float SUB_SETTING_FONT_SIZE = 0.06f;
    private final Colour SETTING_HEADER_COLOUR = new Colour(0.6f, 0.6f, 0.6f, 1.0f);
    private final Colour SETTING_COLOUR = new Colour(0.6f, 0.6f, 0.6f, 1.0f);
    private final String FRAME_TITLE = "OPTIONS";
    private final String BACK_BUTTON = "Done";
    private final String SOUND_TITLE = "Sound";
    private final String MASTER_SETTING = "Master Volume:";
    private final String MUSIC_SETTING = "Music Volume:";
    private final String SOUND_SETTING = "Sound Effects:";
    private final String GRAPHICS_STYLE_SETTING = "Graphics Style:";
    private final String GRAPHICS_TITLE = "Graphics";

    public OptionsFrame() {
        logic = new OptionsFrameLogic();


        UIGroup root = new UIGroup(0.0f, 0.0f);
        initializeFrameView(root);
        setRoot(root);
    }

    private void initializeFrameView(UIGroup root) {

        UIGroup settingsMenu = new UIGroup(0.0f, 0.0f);
        root.addChild(settingsMenu);

        settingsMenu.addChild(createBackground());
        settingsMenu.addChild(createSoundOptions());
        settingsMenu.addChild(createGraphicsOptions());
        settingsMenu.addChild(createBackButton());

    }

    private UIGroup createBackground() {
        UIGroup backgroundUIGroup = new UIGroup(0.0f, 0.0f);
        Image background = new Image(0.0f, -0.075f, 1.7f, 1.7f, TextureLibrary.GREY_SQUARE_BOX.getTexture());
        backgroundUIGroup.addChild(background);

        //Creating banner
        UIGroup banner = new UIGroup(0.0f, 0.75f);
        Image bannerBackground = new Image(0.0f, 0.0f, 0.8f, 0.26f, TextureLibrary.BUTTON_TEXT_LARGE_SQUARE.getTexture());
        Text bannerText = new Text(0.0f, 0.03f, FONT_FREDOKA_ONE, FRAME_TITLE, BANNER_FONT_SIZE);
        banner.addChild(bannerBackground);
        banner.addChild(bannerText);

        backgroundUIGroup.addChild(banner);

        return backgroundUIGroup;
    }

    private UIGroup createSoundOptions() {
        UIGroup soundOptions = new UIGroup(-0.7f, 0.45f);

        Text title = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, SOUND_TITLE, SETTING_HEADER_FONT_SIZE, SETTING_HEADER_COLOUR);
        title.setAnchorPoint(AnchorPoint.Left);
        soundOptions.addChild(title);

        //Music Slider
        UIGroup masterVolumeSection = new UIGroup(0.0f, -0.14f);
        soundOptions.addChild(masterVolumeSection);

        Text masterVolumeText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, MASTER_SETTING, SETTING_FONT_SIZE, SETTING_COLOUR);
        masterVolumeText.setAnchorPoint(AnchorPoint.Left);
        masterVolumeSection.addChild(masterVolumeText);

        Slider masterVolumeSlider = new Slider(
                0.85f,
                -0.015f,
                0.7f,
                0.06f,
                TextureLibrary.SCROLLBAR_GREY.getTexture(),
                TextureLibrary.SCROLLBAR_BUTTON_GREY.getTexture(),
                0, 100, logic.getMasterVolume(),
                args -> logic.setMasterVolume(args.getSliderLevel())
        );
        masterVolumeSlider.setText(new Text(0.45f, 0.0f, FONT_FREDOKA_ONE, "{}%", SUB_SETTING_FONT_SIZE, SETTING_COLOUR));
        masterVolumeSection.addChild(masterVolumeSlider);



        //Music Slider
        UIGroup musicSection = new UIGroup(0.0f, -0.26f);
        soundOptions.addChild(musicSection);

        Text musicText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, MUSIC_SETTING, SETTING_FONT_SIZE, SETTING_COLOUR);
        musicText.setAnchorPoint(AnchorPoint.Left);
        musicSection.addChild(musicText);

        Slider musicSlider = new Slider(
                0.85f,
                -0.015f,
                0.7f,
                0.06f,
                TextureLibrary.SCROLLBAR_GREY.getTexture(),
                TextureLibrary.SCROLLBAR_BUTTON_GREY.getTexture(),
                0, 100, logic.getMusicVolume(),
                args -> logic.setMusicVolume(args.getSliderLevel())
        );
        musicSlider.setText(new Text(0.45f, 0.0f, FONT_FREDOKA_ONE, "{}%", SUB_SETTING_FONT_SIZE, SETTING_COLOUR));
        musicSection.addChild(musicSlider);


        //Sounds Slider
        UIGroup soundsSection = new UIGroup(0.0f, -0.38f);
        soundOptions.addChild(soundsSection);
        Text soundsText = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, SOUND_SETTING, SETTING_FONT_SIZE, SETTING_COLOUR);
        soundsText.setAnchorPoint(AnchorPoint.Left);
        soundsSection.addChild(soundsText);

        Slider soundsSlider = new Slider(
                0.85f,
                -0.015f,
                0.7f,
                0.06f,
                TextureLibrary.SCROLLBAR_GREY.getTexture(),
                TextureLibrary.SCROLLBAR_BUTTON_GREY.getTexture(),
                0, 100, logic.getSfxVolume(),
                args -> logic.setSoundVolume(args.getSliderLevel())
        );
        soundsSlider.setText(new Text(0.45f, 0.0f, FONT_FREDOKA_ONE, "{}%", SUB_SETTING_FONT_SIZE, SETTING_COLOUR));
        soundsSection.addChild(soundsSlider);



        return soundOptions;
    }

    private UIGroup createGraphicsOptions() {
        UIGroup graphicsOptions = new UIGroup(-0.7f, -0.10f);

        Text title = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, GRAPHICS_TITLE, SETTING_HEADER_FONT_SIZE, SETTING_HEADER_COLOUR);
        title.setAnchorPoint(AnchorPoint.Left);
        graphicsOptions.addChild(title);

        //Graphics style toggle
        UIGroup graphicsStyleSection = new UIGroup(0.0f, -0.14f);
        Text graphicsStyle = new Text(0.0f, 0.0f, FONT_FREDOKA_ONE, GRAPHICS_STYLE_SETTING, SETTING_FONT_SIZE, SETTING_COLOUR);
        graphicsStyle.setAnchorPoint(AnchorPoint.Left);
        graphicsStyleSection.addChild(graphicsStyle);

        UIGroup graphicsStyleToggle = new UIGroup(0.7f, -0.005f);
        ToggleSwitch toggleSwitch = new ToggleSwitch(
                0.0f,
                -0.015f,
                0.15f,
                0.15f * 0.45f,
                logic.is3DEnabled(),
                (args) -> logic.enable3DGraphics(),
                (args) -> logic.enable2DGraphics(),
                null,
                null
        );
        graphicsStyleToggle.addChild(toggleSwitch);
        graphicsStyleToggle.addChild(new Text(-0.13f, 0.0f, FONT_FREDOKA_ONE, "2D", SUB_SETTING_FONT_SIZE, SETTING_COLOUR));
        graphicsStyleToggle.addChild(new Text(0.13f, 0.0f, FONT_FREDOKA_ONE, "3D", SUB_SETTING_FONT_SIZE, SETTING_COLOUR));
        graphicsStyleSection.addChild(graphicsStyleToggle);

        graphicsOptions.addChild(graphicsStyleSection);


        return graphicsOptions;
    }

    private UIGroup createBackButton() {
        UIGroup backButton = new UIGroup(0.0f, -0.7f);

        backButton.addChild( new RectButton(
                0.0f,
                0.0f,
                0.7f,
                0.18f,
                TextureLibrary.BUTTON_TEXT_LARGE_SQUARE.getTexture(),
                FONT_FREDOKA_ONE,
                BACK_BUTTON,
                0.12f,
                (args) -> logic.exitSettingsButtonPressed(),
                null,
                null)
        );

        return backButton;
    }
}