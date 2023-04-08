import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;

public class MinecartEventListener implements Listener {

    MinecartFix pluginInstance;

    public MinecartEventListener(MinecartFix pluginInstance) {
        this.pluginInstance = pluginInstance;
    }

    @EventHandler
    public void onEntitySpawn(VehicleCreateEvent event) {
        if (!event.getVehicle().getClass().getName().contains("Minecart")) {
            return;
        }
        pluginInstance.addCart(event.getVehicle());
    }

    @EventHandler
    public void onEntityDespawn(VehicleDestroyEvent event) {
        if (!event.getVehicle().getClass().getName().contains("Minecart")) {
            return;
        }
        pluginInstance.removeCart(event.getVehicle());
    }

}
