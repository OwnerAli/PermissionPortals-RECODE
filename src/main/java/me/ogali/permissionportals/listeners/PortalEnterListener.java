package me.ogali.permissionportals.listeners;

import lombok.AllArgsConstructor;
import me.ogali.permissionportals.PermissionPortals;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

@AllArgsConstructor
public class PortalEnterListener implements Listener {

    private final PermissionPortals main;

    @EventHandler
    public void onPortalEnter(PlayerTeleportEvent event) {
        if (main.getConfig().getBoolean("global.tp-without-perms") &&
                event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL &&
                event.getCause() != PlayerTeleportEvent.TeleportCause.END_PORTAL) return;

        main.getPortalPlayerRegistry().getPortalPlayer(event.getPlayer()).ifPresent(portalPlayer -> {
            World.Environment environment = event.getTo().getWorld().getEnvironment();
            if (environment.equals(World.Environment.NETHER)) {
                portalPlayer.enterPortal(event, true);
            } else if (environment.equals(World.Environment.THE_END)) {
                portalPlayer.enterPortal(event, false);
            }
        });
    }

}
