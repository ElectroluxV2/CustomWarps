package pl.insertt.customwarps.system.warp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;
import org.bukkit.material.SimpleAttachableMaterialData;
import org.bukkit.material.TrapDoor;
import org.diorite.config.annotations.SerializableAs;
import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.SerializationData;
import pl.insertt.customwarps.util.FormatUtils;
import pl.insertt.customwarps.system.warp.api.CustomWarp;
import pl.insertt.customwarps.util.RandomUtils;

import javax.annotation.Nonnull;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.*;

@SerializableAs(CustomWarp.class)
/*
    Simple custom warp service, responsible for holding warp data.
 */
public class CustomWarpService implements CustomWarp
{
    private final UUID warpOwner;

    private String warpName;

    private final Date creationDate;

    private String world;
    private double coordX;
    private double coordY;
    private double coordZ;
    private float pitch;
    private float yaw;

    private Set<UUID> applicablePlayers;

    private boolean modified = false;

    private Date modificationDate;

    private Material icon = RandomUtils.getRandomMaterial(CustomWarpConstants.RANDOM_WARP_ICONS);

    /**
     * @param owner
     *        of the warp.
     * @param name
     *        of the warp.
     * @param location
     *        of the warp.
     */
    CustomWarpService(final UUID owner, final String name, final Location location, final Material icon)
    {
        this.warpOwner = owner;
        this.warpName = name;

        this.creationDate = new Date(System.currentTimeMillis());

        this.world = location.getWorld().getName();
        this.coordX = location.getX();
        this.coordY = location.getY();
        this.coordZ = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();

        applicablePlayers = new HashSet<>();

        this.icon = icon;
    }

    protected CustomWarpService(DeserializationData data)
    {
        this.warpOwner = data.getOrThrow("owner", UUID.class);
        this.warpName = data.getOrThrow("name", String.class);

        this.creationDate = Date.from(FormatUtils.fromLong(data.getOrThrow("creationDate", Long.class)));

        this.world = data.getOrThrow("world", String.class);
        this.coordX = data.getOrThrow("coordX", double.class);
        this.coordY = data.getOrThrow("coordY", double.class);
        this.coordZ = data.getOrThrow("coordZ", double.class);
        this.pitch = data.getOrThrow("pitch", float.class);
        this.yaw = data.getOrThrow("yaw", float.class);

        data.getAsCollection("applicablePlayers", UUID.class, this.applicablePlayers);

        this.modified = data.getOrThrow("modified", boolean.class);
        this.modificationDate = Date.from(FormatUtils.fromLong(data.getOrThrow("modificationDate", Long.class)));

        this.icon = data.getOrThrow("icon", Material.class);

        if(this.applicablePlayers == null)
        {
            this.applicablePlayers = new HashSet<>();
        }
    }

    public static CustomWarpService deserialize(DeserializationData data)
    {
        return new CustomWarpService(data);
    }

    @OverridingMethodsMustInvokeSuper
    public void serialize(SerializationData data)
    {
        data.add("owner", this.warpOwner);
        data.add("name", this.warpName);
        data.add("creationDate", this.creationDate.getTime());

        data.add("world", this.world);
        data.add("coordX", this.coordX);
        data.add("coordY", this.coordY);
        data.add("coordZ", this.coordZ);
        data.add("yaw", this.yaw);
        data.add("pitch", this.pitch);

        data.addCollection("applicablePlayers", this.applicablePlayers, UUID.class);

        data.add("modified", this.modified);

        data.add("modificationDate", this.modificationDate == null ? 0 : this.modificationDate.getTime());

        data.add("icon", this.icon, Material.class);
    }

    @Override
    public UUID getOwner()
    {
        return warpOwner;
    }

    @Override
    @Nonnull
    public String getName()
    {
        return warpName;
    }

    @Override
    public Location getLocation()
    {
        return new Location(Bukkit.getWorld(world), coordX, coordY, coordZ, yaw, pitch);
    }

    @Override
    public void setLocation(Location location)
    {
        this.world = location.getWorld().getName();
        this.coordX = location.getX();
        this.coordY = location.getY();
        this.coordZ = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        modified = true;
        modificationDate = new Date(System.currentTimeMillis());
    }

    @Override
    public void setName(String name)
    {
        this.warpName = name;
        modified = true;
        modificationDate = new Date(System.currentTimeMillis());
    }

    @Override
    public void removeApplicablePlayer(UUID player)
    {
        applicablePlayers.remove(player);
    }

    @Override
    public void addApplicablePlayer(UUID player)
    {
        applicablePlayers.add(player);
    }

    @Override
    public boolean isApplicable(UUID player)
    {
        return applicablePlayers.contains(player);
    }

    @Override
    public boolean isSafe()
    {
        final Block ground = getLocation().getBlock().getRelative(BlockFace.DOWN);
        final Block space1 = getLocation().getBlock();
        final Block space2 = getLocation().getBlock().getRelative(BlockFace.UP);

        if (space1.getType() == Material.PORTAL || ground.getType() == Material.PORTAL || space2.getType() == Material.PORTAL
                || space1.getType() == Material.ENDER_PORTAL || ground.getType() == Material.ENDER_PORTAL || space2.getType() == Material.ENDER_PORTAL)
        {
            return false;
        }

        if (ground.getType() == Material.AIR)
        {
            return false;
        }

        if (ground.isLiquid() || space1.isLiquid() || space2.isLiquid())
        {
            if (ground.getType().equals(Material.STATIONARY_LAVA) || ground.getType().equals(Material.LAVA)
                    || space1.getType().equals(Material.STATIONARY_LAVA) || space1.getType().equals(Material.LAVA)
                    || space2.getType().equals(Material.STATIONARY_LAVA) || space2.getType().equals(Material.LAVA)) {
                return false;
            }
        }

        MaterialData md = ground.getState().getData();

        if (md instanceof SimpleAttachableMaterialData)
        {
            if (md instanceof TrapDoor)
            {
                TrapDoor trapDoor = (TrapDoor)md;
                if (trapDoor.isOpen())
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        if (ground.getType().equals(Material.CACTUS) || ground.getType().equals(Material.BOAT) || ground.getType().equals(Material.FENCE)
                || ground.getType().equals(Material.NETHER_FENCE) || ground.getType().equals(Material.SIGN_POST) || ground.getType().equals(Material.WALL_SIGN))
        {
            return false;
        }

        if (space1.getType().isSolid() && !space1.getType().equals(Material.SIGN_POST) && !space1.getType().equals(Material.WALL_SIGN))
        {
            return false;
        }

        if (space2.getType().isSolid()&& !space2.getType().equals(Material.SIGN_POST) && !space2.getType().equals(Material.WALL_SIGN))
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean isModified()
    {
        return modified;
    }

    @Override
    public Set<UUID> getApplicablePlayers()
    {
        return new HashSet<>(applicablePlayers);
    }

    @Override
    public Date getCreationDate()
    {
        return creationDate;
    }

    @Override
    public Date getModificationDate()
    {
        return modificationDate;
    }

    @Override
    public Material getIcon()
    {
        return icon;
    }

    @Override
    public void setIcon(Material material)
    {
        this.icon = icon;
        modified = true;
        modificationDate = new Date(System.currentTimeMillis());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        CustomWarpService that = (CustomWarpService) o;
        return Double.compare(that.coordX, coordX) == 0 && Double.compare(that.coordY, coordY) == 0 && Double.compare(that.coordZ, coordZ) == 0 && Float.compare(that.pitch, pitch) == 0 && Float.compare(that.yaw, yaw) == 0 && modified == that.modified && Objects.equals(warpOwner, that.warpOwner) && Objects.equals(warpName, that.warpName) && Objects.equals(creationDate, that.creationDate) && Objects.equals(world, that.world);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(warpOwner, warpName, creationDate, world, coordX, coordY, coordZ, pitch, yaw, modified);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this)
                .append("warpOwner", warpOwner)
                .append("warpName", warpName)
                .append("creationDate", creationDate)
                .append("world", world)
                .append("coordX", coordX)
                .append("coordY", coordY)
                .append("coordZ", coordZ)
                .append("pitch", pitch)
                .append("yaw", yaw)
                .append("applicablePlayers", applicablePlayers)
                .append("modified", modified)
                .append("modificationDate", modificationDate)
                .toString();
    }
}
