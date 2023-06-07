package main.hex.serialisation;

import main.hex.player.Player;
import main.hex.player.PlayerSkin;
import main.hex.player.PlayerType;

/**
 * Represents a snapshot of a player's state.
 * This class is meant for serialisation.
 * @author andreas
 *
 */
public class PlayerState {


	public final PlayerType type;
	public final String name;
	public final PlayerSkin skin;
	public final float timeRemaining;
	
	public PlayerState(PlayerType type, String name, PlayerSkin skin, float timeRemaining) {
		this.type = type;
		this.name = name;
		this.skin = skin;
		this.timeRemaining = timeRemaining;
	}
}
