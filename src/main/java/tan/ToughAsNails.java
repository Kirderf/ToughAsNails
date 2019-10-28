package tan;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
import tan.overlay.RenderAirOverlay;
import tan.overlay.RenderTemperatureOverlay;
import tan.overlay.RenderTemperatureVignettes;
import tan.overlay.RenderThirstOverlay;

@Mod(value = "ToughAsNails") // , name = "Tough As Nails",
								// dependencies="required-after:Forge@[1.42.666.42.1,)")
public class ToughAsNails {
	public static ToughAsNails instance;
	public static final ItemGroup tabToughAsNails = new CreativeTabTAN("ToughAsNails");
	public static String configPath;
	public static ForgeConfigSpec config;

	public ToughAsNails() {
		instance = this;
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegisteries);

		MinecraftForge.EVENT_BUS.register(this);
	}

	public void setup(final FMLCommonSetupEvent event) {
		// configPath = event.getModConfigurationDirectory() + "/toughasnails/";
		config.con
		TANConfiguration.init(configPath);

		TANPotions.init();
		TANItems.initializeItems(null);
		TANArmour.init();
		TANCrafting.init();
		TANPlayerStats.init();
		TANTemperature.init();
		TANThirst.init();

		MinecraftForge.EVENT_BUS.register(new StatUpdateEventHandler());

	}

	public void clientRegisteries(final FMLClientSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(new RenderTemperatureOverlay());
		MinecraftForge.EVENT_BUS.register(new RenderTemperatureVignettes());
		MinecraftForge.EVENT_BUS.register(new RenderThirstOverlay());
		MinecraftForge.EVENT_BUS.register(new RenderAirOverlay());
	}

}
