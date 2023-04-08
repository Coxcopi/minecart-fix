import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Vehicle;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class MinecartFix extends JavaPlugin {

    private ArrayList<Vehicle> carts;
    private HashMap<Vehicle, Chunk> cartChunks;
    private int taskID;

    public MinecartFix() {
        carts = new ArrayList<>();
        taskID = -1;
        cartChunks = new HashMap<>();
    }

    @Override
    public void onEnable() {
        FileConfiguration config = this.getConfig();
        config.addDefault("updateDelay", 5L);
        config.options().copyDefaults(true);
        saveConfig();
        long updateDelay = config.getLong("updateDelay");
        getServer().getPluginManager().registerEvents(new MinecartEventListener(this), this);
        preloadCarts();
        taskID = getServer().getScheduler().scheduleSyncRepeatingTask(this, this::update, 0L, updateDelay);
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTask(taskID);
    }

    public void addCart(Vehicle poweredMinecart) {
        if (!carts.contains(poweredMinecart)) {
            carts.add(poweredMinecart);
        }
    }

    public void removeCart(Vehicle poweredMinecart) {
        if (cartChunks.containsKey(poweredMinecart)) {
            Chunk chunk = cartChunks.get(poweredMinecart);
            if (chunk != null) {
                chunk.setForceLoaded(false);
            }
            cartChunks.remove(poweredMinecart);
        }
        poweredMinecart.getWorld().getChunkAt(poweredMinecart.getLocation()).setForceLoaded(false);
        carts.remove(poweredMinecart);
    }

    private void preloadCarts() {
        for (World world : getServer().getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getClass().getName().contains("Minecart")) {
                    addCart((Vehicle) entity);
                }
            }
        }
    }

    private void update() {
        for (Vehicle cart : carts) {
            Chunk chunk = cart.getWorld().getChunkAt(cart.getLocation());
            Chunk oldChunk = cartChunks.get(cart);
            if (!chunk.isForceLoaded()) {
                chunk.setForceLoaded(true);
            }
            cartChunks.put(cart,chunk);
            if (oldChunk == null) {
                continue;
            }
            if (oldChunk.equals(chunk)) {
                continue;
            }
            getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
                if (!cartChunks.containsValue(oldChunk)) {
                    oldChunk.setForceLoaded(false);
                }
            }, 20L * 30);
        }
    }
}
