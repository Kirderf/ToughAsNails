package tan.api.thirst;

import net.minecraft.entity.player.PlayerEntity;
import tan.stats.ThirstStat;

public class ThirstEvent extends net.minecraftforge.eventbus.api.Event
{
    public final PlayerEntity player;
    
    public ThirstStat thirstStat;
    
    public ThirstEvent(PlayerEntity player, ThirstStat thirstStat)
    {
        this.player = player;
        
        this.thirstStat = thirstStat;
    }
}
