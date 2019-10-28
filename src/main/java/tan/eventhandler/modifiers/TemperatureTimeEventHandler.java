package tan.eventhandler.modifiers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tan.api.temperature.TemperatureEvent;

public class TemperatureTimeEventHandler
{
    @SubscribeEvent
    public void modifyTemperature(TemperatureEvent event)
    {
        PlayerEntity player = event.player;
        World world = player.world;
        
        int x = MathHelper.floor(player.posX);
        int y = MathHelper.floor(player.posY);
        int z = MathHelper.floor(player.posZ);
        
        if (isNight(world)) 
        {
            if (world.getChunkFromChunkCoords(x >> 4, z >> 4).canBlockSeeTheSky(x & 15, y + 1, z & 15))
            {
                if (world.isRaining())
                {
                    event.temperature += -3.5F;
                }
                else
                {
                    event.temperature += -2F;
                }
            }
        }
        else
        {
            if (world.getChunkFromChunkCoords(x >> 4, z >> 4).canBlockSeeTheSky(x & 15, y + 1, z & 15))
            {
                if (world.isRaining())
                {
                    event.temperature += -1.75F;
                }
            }
        }
    }
    
    public boolean isDay(World world)
    {
        float celestialAngle = world.getCelestialAngle(0.0F);
        
        if (celestialAngle >= 0.75F && celestialAngle <= 1.0F || celestialAngle >= 0.0F && celestialAngle <= 0.25F) return true;
        
        return false;
    }
    
    public boolean isNight(World world)
    {
        float celestialAngle = world.getCelestialAngle(0.0F);

        if (celestialAngle >= 0.25F && celestialAngle <= 0.75F) return true;
        
        return false;
    }
}
