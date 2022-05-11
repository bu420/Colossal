package bu420.colossal;

import bu420.colossal.block.entity.TimedLightBlockEntity;
import bu420.colossal.entity.NightTerror;
import bu420.colossal.entity.SeaSerpent;
import bu420.colossal.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main {
    public static final String MODID = "colossal";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static final RegistryObject<EntityType<SeaSerpent>> SEA_SERPENT = registerEntity(SeaSerpent::new, "sea_serpent", MobCategory.WATER_CREATURE, 1, 0.625F);
    public static final RegistryObject<ForgeSpawnEggItem> SEA_SERPENT_SPAWN_EGG = ITEMS.register("sea_serpent_spawn_egg", () -> new ForgeSpawnEggItem(SEA_SERPENT, 0x4944B9, 0xE5E5E5, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<EntityType<NightTerror>> NIGHT_TERROR = registerEntity(NightTerror::new, "night_terror", MobCategory.WATER_CREATURE, 1.125F, 1.125F);
    public static final RegistryObject<Block> TIMED_LIGHT_BLOCK = BLOCKS.register("timed_light_block", () -> new Block(BlockBehaviour.Properties.of(Material.AIR).strength(-1.0F, 3600000.8F).noDrops().noOcclusion().lightLevel((whatever) -> 15).noCollission()));
    public static final RegistryObject<BlockEntityType<TimedLightBlockEntity>> TIMED_LIGHT_BLOCK_ENTITY = BLOCK_ENTITIES.register("timed_light_block_entity", () -> BlockEntityType.Builder.of(TimedLightBlockEntity::new, TIMED_LIGHT_BLOCK.get()).build(null));
    public static final RegistryObject<BlockItem> TIMED_LIGHT_BLOCK_ITEM = ITEMS.register("timed_light_block_item", () -> new BlockItem(TIMED_LIGHT_BLOCK.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));

    public Main() {
        PacketHandler.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(bus);
        BLOCKS.register(bus);
        BLOCK_ENTITIES.register(bus);
        ENTITIES.register(bus);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(EntityType.EntityFactory<T> factory, String id, MobCategory mobCategory, float base, float height) {
        return ENTITIES.register(id, () -> EntityType.Builder.of(factory, mobCategory).sized(base, height).build(new ResourceLocation(MODID, "sea_serpent").toString()));
    }

    public static boolean isDay() {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            return level.getDayTime() % 24000 < 12000;
        }
        else {
            LOGGER.warn("ClientLevel is null");
            return true;
        }
    }
}
