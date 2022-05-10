package bu420.colossal.network;

import bu420.colossal.entity.Serpent;
import bu420.colossal.entity.SerpentPart;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class SerpentPartHitPacket implements Packet {
    public SerpentPartHitPacket() {

    }

    public SerpentPartHitPacket(FriendlyByteBuf buffer) {

    }

    @Override
    public void encode(FriendlyByteBuf buffer) {

    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();

            if (player != null) {
                final double reach = ForgeMod.REACH_DISTANCE.get().getDefaultValue();
                AABB box = new AABB(-reach, -reach, -reach, reach, reach, reach).move(player.position());

                final Vec3 start = player.getEyePosition();
                final Vec3 end = start.add(player.getViewVector(1).multiply(reach, reach, reach));

                for (var entity : player.getLevel().getEntities(player, box)) {
                    if (entity instanceof SerpentPart<?> part) {
                        if (part.getParent() instanceof Serpent parent) {
                            Optional<Vec3> result = part.getBoundingBox().clip(start, end);

                            if (result.isPresent()) {
                                AttributeInstance damage = player.getAttribute(Attributes.ATTACK_DAMAGE);
                                part.hurt(DamageSource.playerAttack(player), damage != null ? (float)damage.getValue() * parent.getPartDamageModifier() : 0);
                                break;
                            }
                        }
                    }
                }
            }
        });

        context.get().setPacketHandled(true);
    }
}
