package bu420.colossal.event;

import bu420.colossal.EntityInit;
import bu420.colossal.Main;
import bu420.colossal.client.NightTerrorRenderer;
import bu420.colossal.client.SeaSerpentRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientForgeEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SeaSerpentRenderer.LAYER_LOCATION, SeaSerpentRenderer::createLayerDefinition);
        event.registerLayerDefinition(NightTerrorRenderer.LAYER_LOCATION, NightTerrorRenderer::createLayerDefinition);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityInit.SEA_SERPENT.get(), SeaSerpentRenderer::new);
        event.registerEntityRenderer(EntityInit.NIGHT_TERROR.get(), NightTerrorRenderer::new);
    }
}
