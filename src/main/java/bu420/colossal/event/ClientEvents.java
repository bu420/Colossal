package bu420.colossal.event;

import bu420.colossal.Main;
import bu420.colossal.entity.Serpent;
import bu420.colossal.entity.SerpentPart;
import bu420.colossal.network.PacketHandler;
import bu420.colossal.network.SerpentPartHitPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Main.MODID)
public class ClientEvents {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onClick(InputEvent.ClickInputEvent event) {
        if (event.isAttack()) {
            LocalPlayer player = Minecraft.getInstance().player;

            if (player != null) {
                final double reach = ForgeMod.REACH_DISTANCE.get().getDefaultValue();
                AABB box = new AABB(-reach, -reach, -reach, reach, reach, reach).move(player.position());

                Vec3 start = player.getEyePosition();
                Vec3 end = start.add(player.getViewVector(1).multiply(reach, reach, reach));

                for (var entity : player.level.getEntities(player, box)) {
                    if (entity instanceof SerpentPart<?> part) {
                        Optional<Vec3> result = part.getBoundingBox().clip(start, end);

                        if (result.isPresent()) {
                            PacketHandler.sendToServer(new SerpentPartHitPacket());
                            player.level.playSound(player, part, SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.HOSTILE, 1, 1);
                            return;
                        }
                    }
                }
            }
        }
    }
}
