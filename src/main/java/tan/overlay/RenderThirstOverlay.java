package tan.overlay;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import tan.api.utils.TANPlayerStatUtils;
import tan.core.TANPotions;
import tan.stats.ThirstStat;

public class RenderThirstOverlay extends RenderTANOverlay
{
    public ResourceLocation overlayLocation = new ResourceLocation("toughasnails:textures/overlay/overlay.png");
    
    @Override
    public void preRender(RenderGameOverlayEvent.Pre event)
    {
        //Check for crosshairs since they are always drawn and is before the air bar
        if (event.type != ElementType.CROSSHAIRS) 
        {
            return;
        }
        
        bindTexture(overlayLocation);
        {
            if (!minecraft.player.capabilities.isCreativeMode)
            {
                renderThirst();
            }
        }
        bindTexture(new ResourceLocation("minecraft:textures/gui/icons.png"));
    }


    private void renderThirst()
    {
        minecraft.mcProfiler.startSection("thirst");
        {
            int left = scaledRes.getScaledWidth() / 2 + 91;
            int top = scaledRes.getScaledHeight() - 49;

            ThirstStat thirstStat = TANPlayerStatUtils.getPlayerStat(minecraft.player, ThirstStat.class);
            
            int level = thirstStat.thirstLevel;

            for (int i = 0; i < 10; ++i)
            {
                int idx = i * 2 + 1;
                int x = left - i * 8 - 9;
                int y = top;
                int icon = 16;
                byte backgound = 0;

                if (minecraft.player.isPotionActive(TANPotions.waterPoisoning.id))
                {
                    icon += 36;
                    backgound = 13;
                }

                if (thirstStat.thirstHydrationLevel <= 0.0F && updateCounter % (level * 3 + 1) == 0)
                {
                    y = top + (rand.nextInt(3) - 1);
                }

                drawTexturedModalRect(x, y, backgound * 9, 16, 9, 9);

                if (idx < level)
                    drawTexturedModalRect(x, y, icon + 20, 16, 9, 9);
                else if (idx == level)
                    drawTexturedModalRect(x, y, icon + 29, 16, 9, 9);
            }
        }
        minecraft.mcProfiler.endSection();
    }
}
