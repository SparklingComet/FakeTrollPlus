package org.shanerx.faketrollplus.core;

import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

public interface TrollPlayer {

	/**
	 * Gets the {@link java.util.UUID} of the player associated with the TrollPlayer instance.
	 * @return the UUID.
	 */
	UUID getUUID();

	/**
	 * Gets the name of the player.
	 * @return the name of the player.
	 */
	String getName();

	/**
	 * Gets the {@link org.json.simple.JSONObject} holding all the information about the player, which is collected by the plugin.
	 * @return the {@link org.json.simple.JSONObject} with the user's data.
	 */
	JSONObject getUserInfo();

	/**
	 * Gets the instance {@link org.bukkit.OfflinePlayer} associated with the TrollPlayer instance.
	 * @return the {@link org.bukkit.OfflinePlayer}.
	 */
	OfflinePlayer getOfflinePlayer();

	/**
	 * Whether the player can be trolled.
	 * <br>The trolling player needs to have proper permission.
	 * @param isConsole Whether the trolling entity is the console.
	 * @return {@code true} if the player can be trolled.
	 */
	boolean canTroll(boolean isConsole);

	/**
	 * Whether the player is frozen.
	 * @return {@code true} if frozen.
	 */
	boolean isFrozen();
	
	/**
	 * Sets the player's freeze-status.
	 * @param isFrozen whether or not the player should freeze.
	 */
	void setFrozen(boolean isFrozen);

	/**
	 * Whether the trolled player's chat messages will be changed to gibberish.
	 * @return {@code true} if effect is enabled.
	 */
	boolean chatIsGibberish();
	
	/**
	 * Adds or removes the Gibberish effect.
	 * @param chatIsGibberish whether the player should talk gibberish.
	 */
	void setGibberishChat(boolean chatIsGibberish);

	/**
	 * Whether the player's inventory is locked.
	 * @return {@code true} if yes.
	 */
	boolean invIsLocked();
	
	/**
	 * Sets the lock-state on the player's inventory.
	 * @param invIsLocked whether the player should be locked out or not.
	 */
	void setInvLocked(boolean invIsLocked);

	/**
	 * Whether the player can pickup items.
	 * @return {@code false} if the No-Pickup effect is <strong>enabled</strong>.
	 */
	boolean canPickup();
	
	/**
	 * Sets the state of the No-Pickup effect.
	 * @param canPickup whether the player should be able to pickup dropped items.
	 */
	void setPickup(boolean canPickup);

	/**
	 * Whether the player will be poisoned by any item they consume.
	 * @return {@code true} if yes.
	 */
	boolean hasBadfoodEffect();
	
	/**
	 * Adds or lifts the Badfood effect.
	 * @param hasBadfoodEffect whether the player should be poisoned by food.
	 */
	void setBadfoodEffect(boolean hasBadfoodEffect);
	
	/**
	 * Whether mining a block will generate an explosion.
	 * @return {@code true} if the player is affected.
	 */
	boolean hasExplodeMinedBlocksEffect();
	
	/**
	 * Add or lift the effect from the player.
	 * @param hasExplodeMinedBlocksEffect {@code true} if the blocks should explode.
	 */
	void setExplodeMinedBlocksEffect(boolean hasExplodeMinedBlocksEffect);

	/**
	 * Whether the player is blacklisted by the plugin.
	 * @return {@code true} if the player is blacklisted.
	 */
	boolean isBlacklisted();

	/**
	 * Adds or removes a player's UUID from the blacklist.
	 * @param isBlacklisted whether or not to blacklist the player.
	 */
	void setBlacklisted(boolean isBlacklisted);
}