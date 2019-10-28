package tan.textures;

import com.mojang.blaze3d.platform.TextureUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftGame;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import tan.api.utils.TANPlayerStatUtils;
import tan.api.utils.TemperatureUtils;
import tan.stats.TemperatureStat;

public class TextureThermometer extends TextureAtlasSprite
{
    public TextureThermometer()
    {
        super(new ResourceLocation("toughasnails:thermometer"), 0, 0);
    }

    @Override
    public void updateAnimation()
    {
        if (!this.frames.equals(null)) //.framesTextureData.isEmpty())
        {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerEntity player = (PlayerEntity) player.getEntity();

            if (player != null)
            {
                World world = player.world;

                int x = MathHelper.floor(player.posX);
                int y = MathHelper.floor(player.posY);
                int z = MathHelper.floor(player.posZ);

                TemperatureStat temperatureStat = TANPlayerStatUtils.getPlayerStat(player, TemperatureStat.class);

                this.frameCounter = MathHelper.clamp((int)(((TemperatureUtils.getAimedTemperature(TemperatureUtils.getEnvironmentTemperature(player.world, x, y, z), world, player) / 20F) - 1.35F) * this.getFrameCount()), 0, this.getFrameCount());
            }

            TextureUtil.uploadTextureSub(this.frames, this.width, this.height, this.x, this.y);
        }
    }
}
