package bu420.colossal.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface Packet {
    public void encode(FriendlyByteBuf buffer);
    public void handle(Supplier<NetworkEvent.Context> context);
}
