package bu420.colossal;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<ForgeSpawnEggItem> SEA_SERPENT_SPAWN_EGG = ITEMS.register("sea_serpent_spawn_egg", () -> new ForgeSpawnEggItem(EntityInit.SEA_SERPENT, 0x4944B9, 0xE5E5E5, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
}
