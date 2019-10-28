package tan.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public abstract class TANStat
{     
    public abstract void update(PlayerEntity player);
    
    public abstract void readNBT(CompoundNBT tanData);
    
    public abstract void writeNBT(CompoundNBT tanData);
}
