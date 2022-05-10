package bu420.colossal;

import bu420.colossal.network.PacketHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "colossal";
    public static final Logger LOGGER = LogManager.getLogger();

    public Main() {
        PacketHandler.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(bus);
        EntityInit.ENTITIES.register(bus);
    }
}
