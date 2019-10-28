package tan.eventhandler.modifiers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tan.api.temperature.TemperatureEvent;

public class TemperaturePlayerStateEventHandler
{
    @SubscribeEvent
    public void modifyTemperature(TemperatureEvent event)
    {
        PlayerEntity player = event.player;
        
        float modifier = 0F;
        
        if (player.isSprinting()) modifier += 2.25F;
        if (player.isWet()) modifier -= 1.75F;
        if (player.isBurning()) modifier += 4F;
        
        int x = MathHelper.floor(player.posX);
        int y = MathHelper.floor(player.posY);
        int z = MathHelper.floor(player.posZ);
        
        event.temperature += modifier;    
    }
}
