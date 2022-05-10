package bu420.colossal.entity;

import net.minecraft.world.entity.Entity;

import java.util.List;

public interface Serpent<T extends Entity> {
    int getLength();
    float getPartDamageModifier();
    List<SerpentPart<T>> getPartEntities();
    SerpentPart<T> getHead();
    List<SerpentPart<T>> getBodies();
    SerpentPart<T> getTail();
}
