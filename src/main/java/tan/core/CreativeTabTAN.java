package tan.core;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CreativeTabTAN extends ItemGroup {
	public CreativeTabTAN(String tabID) {
		super(tabID);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(TANItems.thermometer);
	}
}
