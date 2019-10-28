package tan.configuration;

import java.io.File;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TANConfiguration {
	public static File idConfigFile;
	public static File temperatureConfigFile;

	public static void init(String configpath) {
		idConfigFile = new File(configpath + "ids.cfg");
		temperatureConfigFile = new File(configpath + "temperature.cfg");

		TANConfigurationIDs.init(idConfigFile);
		TANConfigurationTemperature.init(temperatureConfigFile);
	}
}
