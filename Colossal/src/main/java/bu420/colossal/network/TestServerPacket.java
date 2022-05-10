package bu420.colossal.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TestServerPacket implements Packet {
    private final boolean test;

    public TestServerPacket(boolean test) {
        this.test = test;
    }

    public TestServerPacket(FriendlyByteBuf buffer) {
        this(buffer.readBoolean());
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeBoolean(test);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            System.out.println("Got packet from the client: " + test);

            PacketHandler.sendToPlayer(context.get().getSender(), new TestClientPacket(false));
        });

        context.get().setPacketHandled(true);
    }
}
