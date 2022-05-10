package bu420.colossal;

import bu420.colossal.entity.NightTerror;
import bu420.colossal.entity.SeaSerpent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);

    public static final RegistryObject<EntityType<SeaSerpent>> SEA_SERPENT = register(SeaSerpent::new, "sea_serpent", MobCategory.WATER_CREATURE, 1, 0.625F);
    public static final RegistryObject<EntityType<NightTerror>> NIGHT_TERROR = register(NightTerror::new, "night_terror", MobCategory.WATER_CREATURE, 1.125F, 1.125F);

    private static <T extends Entity> RegistryObject<EntityType<T>> register(EntityType.EntityFactory<T> factory, String id, MobCategory mobCategory, float base, float height) {
        return ENTITIES.register(id, () -> EntityType.Builder.of(factory, mobCategory).sized(base, height).build(new ResourceLocation(Main.MODID, "sea_serpent").toString()));
    }
}
