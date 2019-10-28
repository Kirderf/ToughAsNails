package tan.overlay;

import org.lwjgl.opengl.GL11;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderAirOverlay
{
    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() == ElementType.AIR)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, -10.0F, 0.0F);
        }
    }
    
    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() == ElementType.AIR)
        {
            GL11.glPopMatrix();
        }
    }
}
