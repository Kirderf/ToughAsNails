package tan.eventhandler.modifiers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tan.api.thirst.ThirstEvent;
import tan.stats.ThirstStat;

public class ThirstMovementEventHandler
{
    @SubscribeEvent
    public void modifyThirst(ThirstEvent event)
    {
        PlayerEntity player = event.player;
        
        ThirstStat thirstStat = event.thirstStat;
        
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        
        if (player.getRidingEntity() == null)
        {
            if (player.onGround)
            {
                if (player.isSprinting())
                {
                    thirstStat.addExhaustion(0.0099999994F * 5);
                }
                else
                {
                    thirstStat.addExhaustion(0.001F * 5);
                }
            }
        }
    }
}