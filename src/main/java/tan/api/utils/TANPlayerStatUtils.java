package tan.api.utils;

import tan.api.TANStat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class TANPlayerStatUtils
{
    public static <Stat extends TANStat> Stat getPlayerStat(PlayerEntity player, Class<Stat> clazz)
    {
        Stat stat = null;
        
        try
        {
            stat = clazz.newInstance();
            
            if (!player.getEntity().getPersistentData().contains("ToughAsNails")) player.getEntity().getPersistentData().put("ToughAsNails", new CompoundNBT());
            
            stat.readNBT(player.getEntity().getPersistentData().getCompound("ToughAsNails"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }     

        return stat;
    }

    public static void setPlayerStat(PlayerEntity player, TANStat stat)
    {       
        if (!player.getEntity().getPersistentData().contains("ToughAsNails")) player.getEntity().getPersistentData().put("ToughAsNails", new CompoundNBT());
        
        stat.writeNBT(player.getEntity().getPersistentData().getCompound("ToughAsNails"));
    }
}
