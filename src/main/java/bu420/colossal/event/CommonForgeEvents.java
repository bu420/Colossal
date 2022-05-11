package bu420.colossal.event;

import bu420.colossal.Main;
import bu420.colossal.entity.NightTerror;
import bu420.colossal.entity.SeaSerpent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonForgeEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(Main.SEA_SERPENT.get(), SeaSerpent.createAttributes().build());
        event.put(Main.NIGHT_TERROR.get(), NightTerror.createAttributes().build());
    }
}
