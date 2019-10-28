package tan.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionWaterPoisoning extends Potion {
	public PotionWaterPoisoning() {
		new Potion("Water Posioning",new EffectInstance(EffectInstance.read(new CompoundNBT())));
		
	}

	public int getStatusIconIndex() {
		Minecraft.getInstance().textureManager
				.bindTexture(new ResourceLocation("toughasnails:textures/potions/TANPotionFX.png"));
		return 0;
	}

	public boolean isReady(int par1, int par2) {
		return par1 >= 1;
	}
}
