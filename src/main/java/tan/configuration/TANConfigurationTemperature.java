package tan.configuration;

import java.io.File;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TANConfigurationTemperature {
	private static final ForgeConfigSpec.Builder builder = null;
	public static ForgeConfigSpec config;

	public static String temperatureType;

	public static void init(File configFile) {
		config = new ForgeConfigSpec(configFile);

		try {
			config.load();

			temperatureType = config.get("Temperature", "Temperature Scale", "Celsius",
					"The temperature scale to use, supports Celsius, Fahrenheit & Kelvin").getString();

			FMLCommonHandler.instance().getFMLLogger().log(Level.INFO, "[ToughAsNails] Generated Temperature Config!");
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Tough As Nails has had a problem loading its configuration");
		} finally {
			if (config.hasChanged()) {
				config.save();
			}
		}
	}
}
