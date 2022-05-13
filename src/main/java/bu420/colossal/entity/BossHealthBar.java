package bu420.colossal.entity;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class BossHealthBar {
    private ServerBossEvent props;
    private LivingEntity entity;

    public BossHealthBar(LivingEntity entity, BossEvent.BossBarColor color) {
        props = new ServerBossEvent(entity.getType().getDescription(), color, BossEvent.BossBarOverlay.PROGRESS);
        this.entity = entity;

        if (!entity.level.isClientSide) {
            for (var player : getPlayersWithinRange()) {
                props.addPlayer(player);
            }
        }
    }

    public void tick() {
        if (props != null) {
            props.setProgress(entity.getHealth() / entity.getMaxHealth());

            if (!entity.level.isClientSide) {
                props.setVisible(entity.isAlive());


            }
        }
    }

    public void remove() {
        if (!entity.level.isClientSide) {
            props.setProgress(0);
            props.setVisible(false);
            props.removeAllPlayers();
            updateVisibility();
        }
    }

    private void updateVisibility() {
        if (!entity.level.isClientSide) {
            props.removeAllPlayers();

            for (var player : getPlayersWithinRange()) {
                props.addPlayer(player);
            }
        }
    }

    private List<ServerPlayer> getPlayersWithinRange() {
        return ((ServerLevel)entity.level).getPlayers(EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.withinDistance(0.0D, 128.0D, 0.0D, 192.0D)));
    }
}
