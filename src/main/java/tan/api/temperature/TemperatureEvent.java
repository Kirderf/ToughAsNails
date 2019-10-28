package tan.api.temperature;

import net.minecraft.entity.player.PlayerEntity;

public class TemperatureEvent extends net.minecraftforge.eventbus.api.Event
{
    public final PlayerEntity player;
    public float temperature;
    
    public TemperatureEvent(PlayerEntity player, float temperature)
    {
        this.player = player;
        this.temperature = temperature;
    }
    
    public static class Rate extends TemperatureEvent
    {
        public float rate;
        
        public Rate(PlayerEntity player, float temperature, float rate)
        {
            super(player, temperature);
            
            this.rate = rate;
        }
    }
}
