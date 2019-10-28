package tan.stats;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import tan.api.TANStat;
import tan.api.thirst.ThirstEvent;
import tan.core.TANDamageSource;

public class ThirstStat extends TANStat
{
    public int thirstLevel = 20;

    public float thirstHydrationLevel = 7F;
    public float thirstExhaustionLevel = 0F;

    public int thirstTimer = 0;

    @Override
    public void update(PlayerEntity player)
    {
        World world = player.world;
        
        if (player.isCreative()) return;
        
        ThirstEvent thirstEvent = new ThirstEvent(player, this);
        
        MinecraftForge.EVENT_BUS.post(thirstEvent);
        
        int i = player.world.getDifficulty().getId();

        if (this.thirstExhaustionLevel > 2.0F)
        {
            this.thirstExhaustionLevel -= 2.0F;

            if (this.thirstHydrationLevel > 0.0F)
            {
                this.thirstHydrationLevel = Math.max(this.thirstHydrationLevel - 1.0F, 0.0F);
            }
            else if (i > 0)
            {
                this.thirstLevel = Math.max(this.thirstLevel - 1, 0);
            }
        }

        if (this.thirstLevel <= 0)
        {
            ++this.thirstTimer;

            if (this.thirstTimer >= 80)
            {
                if (player.getFoodStats().getFoodLevel() == 0 || player.getHealth() > 10.0F || i >= 3 || player.getHealth() > 1.0F && i >= 2)
                {
                    player.attackEntityFrom(TANDamageSource.dehydration, 1.0F);
                }

                this.thirstTimer = 0;
            }
        }
        else
        {
            this.thirstTimer = 0;
        }
        
        if (player.isSprinting() && thirstLevel <= 6)
        {
            player.setSprinting(false);
        }
        
        //System.out.println("Exhaustion Level " + thirstExhaustionLevel);
        //System.out.println("Hydration Level " + thirstHydrationLevel);
        //System.out.println("Thirst Level " + thirstLevel);
    }
    
    @Override
    public void readNBT(CompoundNBT tanData)
    {
        if (tanData.getBoolean("thirst"))
        {
            CompoundNBT thirstCompound = tanData.getCompound("thirst");

            thirstLevel = thirstCompound.getInt("thirstLevel");
            thirstHydrationLevel = thirstCompound.getFloat("thirstHydrationLevel");
            thirstExhaustionLevel = thirstCompound.getFloat("thirstExhaustionLevel");
            thirstTimer = thirstCompound.getInt("thirstTimer");
        }
    }

    @Override
    public void writeNBT(CompoundNBT tanData)
    {
        CompoundNBT thirstCompound = new CompoundNBT();
        
        thirstCompound.putInt("thirstLevel", thirstLevel);
        thirstCompound.putFloat("thirstHydrationLevel", thirstHydrationLevel);
        thirstCompound.putFloat("thirstExhaustionLevel", thirstExhaustionLevel);
        thirstCompound.putInt("thirstTimer", thirstTimer);
        
        tanData.put("thirst", thirstCompound);
    }
    
    public void addThirst(int thirstAmount, float hydrationModifier)
    {
        this.thirstLevel = Math.min(thirstLevel + thirstAmount, 20);
        this.thirstHydrationLevel = Math.min(this.thirstHydrationLevel + (float)thirstAmount * hydrationModifier * 2.0F, (float)this.thirstLevel);
    }
    
    public void addExhaustion(float amount)
    {
        thirstExhaustionLevel = Math.min(thirstExhaustionLevel + amount, 40.0F);
    }
}
