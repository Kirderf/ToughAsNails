package tan.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.controller.MovementController.Action;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import tan.ToughAsNails;
import tan.api.thirst.TANDrinkInfo;
import tan.api.utils.TANPlayerStatUtils;
import tan.core.TANPotions;
import tan.stats.ThirstStat;

public class ItemTANCanteen extends net.minecraftforge.fluids.capability.ItemFluidContainer
{
    public Icon canteenFilledIcon;
    public Icon canteenEmptyIcon;
    
    private ThirstStat thirstStat;
    
    private Fluid canteenFluid;
    
    public ItemTANCanteen(int id)
    {
        super(id);
        this.maxStackSize = 1;
        this.capacity = 250;  /*FluidContainerRegistry.BUCKET_VOLUME / 5*/
        this.setMaxDamage(5);
        this.setCreativeTab(ToughAsNails.tabToughAsNails);
    }
    
    @Override
    public boolean onItemUse(ItemStack itemStack, PlayerEntity player, World world, int x, int y, int z, int side, float hitVecX, float hitVecY, float hitVecZ)
    {
        return false;
    }
    
    @Override
    public ItemStack onEaten(ItemStack itemStack, World world, PlayerEntity player)
    {
        if (!player.capabilities.isCreativeMode)
        {
            drain(itemStack, 50, true);
            itemStack.damageItem(1, player);
        }

        if (itemStack.getItemDamage() >= itemStack.getMaxDamage())
        {
            itemStack.setItemDamage(0);
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
    
    @Override
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
                        itemStack.setItemDamage(0);
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
