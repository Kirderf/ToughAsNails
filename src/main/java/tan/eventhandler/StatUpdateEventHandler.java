package tan.eventhandler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDispatcher;
import tan.api.utils.TANPlayerStatUtils;
import tan.network.PacketTypeHandler;
import tan.network.packet.PacketSendStats;
import tan.stats.TemperatureStat;
import tan.stats.ThirstStat;

public class StatUpdateEventHandler
{
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event)
    {
        LivingEntity entityLiving = event.getEntityLiving();
        World world = entityLiving.world;
        
        if (entityLiving instanceof PlayerEntity)
        {
        	PlayerEntity player = (PlayerEntity)entityLiving;
            
            if (!world.isRemote)
            {
                TemperatureStat temperatureStat = TANPlayerStatUtils.getPlayerStat(player, TemperatureStat.class);
                ThirstStat thirstStat = TANPlayerStatUtils.getPlayerStat(player, ThirstStat.class);
                
                temperatureStat.update(player);
                thirstStat.update(player);
                
                TANPlayerStatUtils.setPlayerStat(player, temperatureStat);
                TANPlayerStatUtils.setPlayerStat(player, thirstStat);
                
                PacketDispatcher.sendPacketToPlayer(PacketTypeHandler.populatePacket(new PacketSendStats(player)), (PlayerEntity)player);
            }
        }
    }
}
