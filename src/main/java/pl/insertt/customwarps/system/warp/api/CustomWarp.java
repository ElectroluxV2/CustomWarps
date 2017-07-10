package pl.insertt.customwarps.system.warp.api;

import org.bukkit.Location;
import org.bukkit.Material;
import org.diorite.config.serialization.Serializable;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Warp interface with basic operations, all warp implementations must implement this.
 */
public interface CustomWarp extends Serializable
{
    /**
     * Returns UUID of warp's owner.
     *
     * @return UUID of warp's owner.
     */
    @Nonnull
    UUID getOwner();

    /**
     * Returns name of warp.
     *
     * @return name of warp.
     */
    @Nonnull
    String getName();

    /**
     * Returns location of warp.
     *
     * @return location of warp.
     */
    @Nonnull
    Location getLocation();

    /**
     * Sets a new location.
     * After this operation, warp will be marked as modified.
     *
     * @param location
     */
    void setLocation(Location location);

    /**
     * Sets a new name.
     * After this operation, warp will be marked as modified.
     *
     * @param name
     */
    void setName(String name);

    /**
     * Removes player from list of applicable players to teleport onto warp.
     *
     * @param player
     */
    void removeApplicablePlayer(UUID player);

    /**
     * Adds player to list of applicable players to teleport onto warp.
     *
     * @param player
     */
    void addApplicablePlayer(UUID player);

    /**
     * Returns boolean whether player is applicable to teleport onto warp.
     *
     * @param player
     *
     * @return true
     *         if player is applicable to teleport onto warp.
     */
    boolean isApplicable(UUID player);

    /**
     * Returns boolean whether warp is safe for teleport.
     *
     * @return true
     *         if warp is safe for teleport.
     */
    boolean isSafe();

    /**
     * Returns boolean whether warp has been modified before.
     *
     * @return true
     *         if warp has been modified before.
     */
    boolean isModified();

    /**
     * Returns set of applicable players to teleport onto warp.
     * Can return empty set if none is applicable to teleport onto warp.
     *
     * @return set of applicable players to teleport onto warp.
     */
    @Nonnull
    Set<UUID> getApplicablePlayers();

    /**
     * Returns date when warp has been created.
     *
     * @return date when warp has been created.
     */
    @Nonnull
    Date getCreationDate();

    /**
     * Returns date when warp has been modified.
     * Returns null if warp has not been modified before.
     *
     * @return date when warp has been modified.
     *         Can return null if warp has not been modified before.
     */
    @Nonnull
    Date getModificationDate();

    /**
     * Returns start date when applicable players can teleport onto warp.
     *
     * @return date when applicable players can teleport onto warp.
     */

    /**
     * Returns icon of warp in gui.
     *
     * @return icon of warp in gui.
     */
    @Nonnull
    Material getIcon();

    /**
     * Sets icon of warp in gui.
     */
    void setIcon(Material material);

}
