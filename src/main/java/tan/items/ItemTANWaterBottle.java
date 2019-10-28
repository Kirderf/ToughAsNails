package tan.items;

import java.util.List;

import javax.swing.Icon;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tan.ToughAsNails;

public class ItemTANWaterBottle extends Food
{
	private static String[] items = {"freshwaterbottle", "dirtywaterbottle", "saltwaterbottle"};
	private Icon[] textures;
	private Food food;
	//int healing, float saturationIn, boolean isMeat, boolean alwaysEdible, boolean fastEdible, List<Pair<EffectInstance, Float>> effectsIn
    public ItemTANWaterBottle()
    {
       // super(0, 0.0F, false, true, false, null);
      //  super(new Item.Properties().group(ToughAsNails.tabToughAsNails).maxStackSize(1));
        super(new Food.Builder().hunger(0).build())
        /*FluidContainerRegistry.BUCKET_VOLUME / 5*/
       this.setFood(Builder.this.hunger(0).saturation(0).build());
        this.maxStackSize = 1;
    	setHasSubtypes(true);
        this.setCreativeTab(ToughAsNails.tabToughAsNails);
        
    }
    
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
    	return EnumAction.drink;
    }
    
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }
    
	public ItemStack onEaten(ItemStack itemstack, World world, PlayerEntity player)
    {
		itemstack.setCount(itemstack.getCount()-1);
		
        world.playSound(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onEaten(itemstack, world, player);

		if (!player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle)))
		{
            player.dropItem(new ItemStack(Item.glassBottle.itemID, 1, 0), false);
		}
        
        return itemstack;
    }


	public void registerIcons(IconRegister iconRegister)
	{
		textures = new Icon[items.length];

		for (int i = 0; i < items.length; ++i) {
			textures[i] = iconRegister.registerIcon("toughasnails:"+items[i]);
		}
	}

	public String getUnlocalizedName(ItemStack itemStack)
	{
		int meta = itemStack.getItemDamage();
		if (meta < 0 || meta >= items.length) {
			meta = 0;
		}

		return super.getUnlocalizedName() + "." + items[meta];
	}

	public Icon getIconFromDamage(int meta)
	{
		if (meta < 0 || meta >= textures.length) {
			meta = 0;
		}

		return textures[meta];
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(int itemId, CreativeTabs creativeTab, List subTypes)
	{
		for(int meta = 0; meta < items.length; ++meta) {
			subTypes.add(new ItemStack(itemId, 1, meta));
		}
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}
}
