package tan.eventhandler.modifiers;

import java.util.ArrayList;
import java.util.Collections;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tan.api.temperature.TemperatureEvent;
import tan.api.temperature.TemperatureRegistry;

public class TemperatureSourceEventHandler
{
    @SubscribeEvent
    public void modifyTemperature(TemperatureEvent event)
    {
        ArrayList<Float> temperatureModifiers = new ArrayList<Float>();
        
        PlayerEntity player = event.player;
        World world = player.world;
        
        int x = MathHelper.floor(player.posX);
        int y = MathHelper.floor(player.posY);
        int z = MathHelper.floor(player.posZ);
        
        for (int ix = -2; ix <= 2; ix++)
        {
            for (int iy = -1; iy <= 1; iy++)
            {
                for (int iz = -2; iz <= 2; iz++)
                {
                    int blockID = world.getBlockId(x + ix, y + iy, z + iz);
                    int metadata = world.getBlockMetadata(x + ix, y + iy, z + iz);

                    float temperatureModifier = TemperatureRegistry.getTemperatureSourceModifier("B", blockID, metadata);

                    temperatureModifiers.add(temperatureModifier);
                }
            }
        }
        
        float total = 0F;
        int divider = 0;
        
        for (float temperatureModifier : temperatureModifiers)
        {
            total += temperatureModifier;
            divider++;
        }
        
        if ((total / divider) > 0)
        {
            event.temperature += Collections.max(temperatureModifiers);
        }
        else
        {
            event.temperature += Collections.min(temperatureModifiers);
        }
    }
    
    @SubscribeEvent
    public void modifyRate(TemperatureEvent.Rate event)
    {
        ArrayList<Float> rateModifiers = new ArrayList<Float>();
        
        PlayerEntity player = event.player;
        World world = player.world;
        
        int x = MathHelper.floor(player.posX);
        int y = MathHelper.floor(player.posY);
        int z = MathHelper.floor(player.posZ);
        
        for (int ix = -2; ix <= 2; ix++)
        {
            for (int iy = -1; iy <= 1; iy++)
            {
                for (int iz = -2; iz <= 2; iz++)
                {
                    int blockID = world.getBlockId(x + ix, y + iy, z + iz);
                    int metadata = world.getBlockMetadata(x + ix, y + iy, z + iz);

                    float rateModifier = TemperatureRegistry.getTemperatureSourceRate("B", blockID, metadata);

                    rateModifiers.add(rateModifier);
                }
            }
        }
        
        float total = 0F;
        int divider = 0;
        
        for (float rateModifier : rateModifiers)
        {
            total += rateModifier;
            divider++;
        }
        
        if ((total / divider) > 0)
        {
            event.rate += Collections.max(rateModifiers);
        }
        else
        {
            event.rate += Collections.min(rateModifiers);
        }
    }
}
