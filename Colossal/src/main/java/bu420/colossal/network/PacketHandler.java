package bu420.colossal.network;

import bu420.colossal.Main;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;
import java.util.function.Function;

public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    private static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Main.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int id = 0;

    public static void init() {
        register(SerpentPartHitPacket.class, SerpentPartHitPacket::new, NetworkDirection.PLAY_TO_SERVER);

        register(TestServerPacket.class, TestServerPacket::new, NetworkDirection.PLAY_TO_SERVER);
        register(TestClientPacket.class, TestClientPacket::new, NetworkDirection.PLAY_TO_CLIENT);
    }

    private static <T extends Packet> void register(Class<T> type, Function<FriendlyByteBuf, T> decoder, NetworkDirection direction) {
        INSTANCE.registerMessage(id++, type, Packet::encode, decoder, Packet::handle, Optional.of(direction));
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void sendToPlayer(ServerPlayer player, Object packet) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void broadcast(Object packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
}
