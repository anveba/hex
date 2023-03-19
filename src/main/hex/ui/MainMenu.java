package main.hex.ui;

import main.engine.*;
import main.engine.font.BitmapFont;
import main.engine.graphics.*;
import main.engine.ui.*;

public class MainMenu extends Frame {

	//Currently an example UI class
	
	public MainMenu() {
		//Main menu extends Frame, so it has a UI element as a root
		UIGroup root = new UIGroup(0.0f, 0.0f);
		setRoot(root);
		
		//Load resources to be used in interface		
		Texture t = ResourceManager.getInstance().loadTexture("textures/board/blue_tile.png");
		BitmapFont f = ResourceManager.getInstance().loadFont("fonts/roboto.ttf");
		
		//Create an image, some text and a button and attach it to the root object
		root.addChild(new Image(-0.8f, 0.8f, 0.2f, 0.2f, t, 0, 0, t.width() / 2, t.height() / 2));
		root.addChild(new Text(0.0f, 0.8f, f, "Welcome to Hex!", 0.1f));
		ButtonCallback clickFnc = (args) -> { // Example callback function
			System.out.println("Clicked!");
		};
		root.addChild(new RectButton(0.0f, -0.8f, 0.2f, 0.2f, 
				t, 0.2f, 0.2f, 0, 0, t.width(), t.height(),
				f, "Click me!", 0.03f, clickFnc, null));
	}
    
}
