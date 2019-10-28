package tan.potions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tan.api.utils.TANPlayerStatUtils;
import tan.core.TANPotions;
import tan.stats.TemperatureStat;
import tan.stats.ThirstStat;

public class PotionEventHandler
{
    @SubscribeEvent
    public void onEntityLivingUpdate(LivingUpdateEvent event)
    {
        if (event.getEntityLiving() instanceof PlayerEntity)
        {
        	PlayerEntity player = (PlayerEntity)event.getEntityLiving();
            
            if (event.getEntityLiving().isPotionActive(TANPotions.waterPoisoning))
            {
                ThirstStat thirstStat = TANPlayerStatUtils.getPlayerStat(player, ThirstStat.class);
                
                int amplifier = player.getActivePotionEffect(TANPotions.waterPoisoning).getAmplifier();       
                float exhaustionAmount = 0.015F * (float)(amplifier + 2);     
                
                thirstStat.addExhaustion(exhaustionAmount);
                TANPlayerStatUtils.setPlayerStat(player, thirstStat);
            }
        }
    }
}
