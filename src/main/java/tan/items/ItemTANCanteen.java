package tan.items;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.controller.MovementController.Action;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.ItemFluidContainer;
import tan.ToughAsNails;
import tan.api.thirst.TANDrinkInfo;
import tan.api.utils.TANPlayerStatUtils;
import tan.core.CreativeTabTAN;
import tan.core.TANPotions;
import tan.stats.ThirstStat;

public class ItemTANCanteen extends net.minecraftforge.fluids.capability.ItemFluidContainer
{
    public Icon canteenFilledIcon;
    public Icon canteenEmptyIcon;
    
    private ThirstStat thirstStat;
    
    private static ItemFluidContainer canteenFluid;
    
    public ItemTANCanteen()
    {
        super(new Item.Properties().group(ToughAsNails.tabToughAsNails).maxStackSize(1).maxDamage(5).containerItem(canteenFluid), 250);  /*FluidContainerRegistry.BUCKET_VOLUME / 5*/
        
    }
    
    public boolean onItemUse(ItemStack itemStack, PlayerEntity player, World world, int x, int y, int z, int side, float hitVecX, float hitVecY, float hitVecZ)
    {
        return false;
    }
    
    public ItemStack onEaten(ItemStack itemStack, World world, PlayerEntity player)
    {
        if (!player.isCreative())
        {
            drain(itemStack, 50, true);
            itemStack.damageItem(1, player);
        }

        if (itemStack.getItem().getDamage(itemStack) >= itemStack.getMaxDamage())
        {
            itemStack.setDamage(0);
        }

        if (!world.isRemote)
        {
            TANDrinkInfo drinkInfo = TANDrinkInfo.getDrinkInfo(canteenFluid.getName());
            
            thirstStat.addThirst(drinkInfo.thirstAmount / 4, drinkInfo.hydrationModifier / 4);
            
            if (world.rand.nextFloat() < drinkInfo.poisoningChance)
            {
                player.addPotionEffect(new PotionEffect(TANPotions.waterPoisoning.id, 1200, 0));
            }
            
            TANPlayerStatUtils.setPlayerStat(player, thirstStat);
        }

        return itemStack;
    }
    
    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity player)
    {
        MovingObjectPosition pos = this.getMovingObjectPositionFromPlayer(world, player, true);

        FluidStack fluid = getFluid(itemStack);
        
        if (fluid == null || fluid.amount != capacity)
        {
            if (pos != null)
            {
                int x = pos.blockX;
                int y = pos.blockY;
                int z = pos.blockZ;

                Fluid blockFluid = FluidRegistry.lookupFluidForBlock(Block.blocksList[world.getBlockId(x, y, z)]);
                
                if (blockFluid != null)
                {
                    if (blockFluid == FluidRegistry.WATER)
                    {
                        this.fill(itemStack, new FluidStack(blockFluid, capacity), true);
                        itemStack.setDamage(0);
                        world.setBlockToAir(x, y, z);
                        
                        return itemStack;
                    }
                }
            }
        }
        
        if (fluid != null && fluid.amount != 0)
        {
            thirstStat = TANPlayerStatUtils.getPlayerStat(player, ThirstStat.class);
            canteenFluid = fluid.getFluid();
            
            if (thirstStat.thirstLevel < 20)
            {
                player.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
            }
        }

        return itemStack;
    }

    public void addInformation(ItemStack itemStack, PlayerEntity player, List stringList, boolean showAdvancedInfo) 
    {
        FluidStack fluid = getFluid(itemStack);
        
        if (fluid != null && fluid.getAmount() > 0)
        {
            ResourceLocation localizedName = fluid.getFluid().getRegistryName();

            stringList.add(localizedName);
        }
    }
    
    public Action getItemUseAction(ItemStack par1ItemStack)
    {
    	return Action.drink;
    }
    
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {	
        return 48;
    }

    @Override
    public void registerIcons(IconRegister iconRegister)
    {
        canteenFilledIcon = iconRegister.registerIcon("toughasnails:canteenfull");
        canteenEmptyIcon = iconRegister.registerIcon("toughasnails:canteenempty");
    }
    
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override
    public Icon getIcon(ItemStack stack, int renderPass)
    {
        FluidStack fluid = getFluid(stack);
        
        if (fluid != null && fluid.amount != 0) return canteenFilledIcon;
        
        return canteenEmptyIcon;
    }
}
