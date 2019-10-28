package tan;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import tan.configuration.TANConfiguration;
import tan.core.CreativeTabTAN;
import tan.core.TANArmour;
import tan.core.TANCrafting;
import tan.core.TANItems;
import tan.core.TANPlayerStats;
import tan.core.TANPotions;
import tan.core.TANTemperature;
import tan.core.TANThirst;
import tan.eventhandler.StatUpdateEventHandler;
import tan.handler.ConnectionHandler;
import tan.handler.LocalizationHandler;
import tan.network.PacketHandler;
import tan.overlay.RenderAirOverlay;
import tan.overlay.RenderTemperatureOverlay;
import tan.overlay.RenderTemperatureVignettes;
import tan.overlay.RenderThirstOverlay;

@Mod(value = "ToughAsNails")//, name = "Tough As Nails", dependencies="required-after:Forge@[1.42.666.42.1,)")
public class ToughAsNails
{
    public static ToughAsNails instance;   
    public static CreativeTabs tabToughAsNails;
    public static String configPath;
    
    public ToughAsNails() {
		instance = this;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegisteries);

		MinecraftForge.EVENT_BUS.register(this);
	}
    
    public void setup(final FMLCommonSetupEvent event)
    {
        configPath = event.getModConfigurationDirectory() + "/toughasnails/";
        TANConfiguration.init(configPath);
        
        tabToughAsNails = new CreativeTabTAN(CreativeTabs.getNextID(), "tabToughAsNails");

        TANPotions.init();
        TANItems.init();
        TANArmour.init();
        TANCrafting.init();
        TANPlayerStats.init();
        TANTemperature.init();
        TANThirst.init();
        
        MinecraftForge.EVENT_BUS.register(new StatUpdateEventHandler());
        
 
    }
    
    public void clientRegisteries(final FMLClientSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new RenderTemperatureOverlay());
        MinecraftForge.EVENT_BUS.register(new RenderTemperatureVignettes());
        MinecraftForge.EVENT_BUS.register(new RenderThirstOverlay());
        MinecraftForge.EVENT_BUS.register(new RenderAirOverlay());
    }
    
}
