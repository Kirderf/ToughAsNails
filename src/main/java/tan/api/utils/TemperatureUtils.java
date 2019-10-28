package tan.api.utils;

import java.text.DecimalFormat;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import tan.api.temperature.TemperatureEvent;

public class TemperatureUtils
{
    public static float getAimedTemperature(float environmentTemperature, World world, PlayerEntity player)
    {
        float aimedTemperature = environmentTemperature;
        
        TemperatureEvent temperatureEvent = new TemperatureEvent(player, aimedTemperature);
        
        MinecraftForge.EVENT_BUS.post(temperatureEvent);
        aimedTemperature = temperatureEvent.temperature;
        
        DecimalFormat twoDForm = new DecimalFormat("#.##");   

        try
        {
            aimedTemperature = Float.parseFloat(twoDForm.format(aimedTemperature));
        }
        catch (Exception e)
        {

        }

        return aimedTemperature;
    }
    
    public static float getEnvironmentTemperature(World world, int x, int y, int z)
    {
        float averageAimedEnvironmentTemperature = 0F;

        int environmentDivider = 0;

        for (int ix = -2; ix <= 2; ix++)
        {
            for (int iy = -1; iy <= 1; iy++)
            {
                for (int iz = -2; iz <= 2; iz++)
                {
                    
                    Biome biome = world.getBiome(new BlockPos(x + ix, z + iz, iy));

                    averageAimedEnvironmentTemperature += ((biome.getDefaultTemperature() / 2) * 20) + 27;

                    environmentDivider++;
                }
            }
        }
        
        return averageAimedEnvironmentTemperature / environmentDivider;
    }
    
}
