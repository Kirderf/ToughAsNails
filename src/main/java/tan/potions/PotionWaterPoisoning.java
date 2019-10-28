package tan.potions;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class PotionWaterPoisoning extends Potion {
	public PotionWaterPoisoning(int id, boolean isBad, int colour) {
		super(id, isBad, colour);
		this.setIconIndex(1, 0);
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
