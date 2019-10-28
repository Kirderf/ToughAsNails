package tan.core;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tan.configuration.TANConfigurationIDs;
import tan.items.ItemTANCanteen;
import tan.items.ItemTANMiscItems;
import tan.items.ItemTANThermometer;
import tan.items.ItemTANWaterBottle;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TANItems
{
	public static Item canteen;
    public static Item thermometer;
    public static Item waterBottle;
    public static Item miscItems;

    @SubscribeEvent
    public static void initializeItems(final RegistryEvent.Register<Item> event)
    {
    	event.getRegistry().registerAll(
    	canteen = new ItemTANCanteen().setRegistryName(location("tan.canteen")),
        thermometer = new ItemTANThermometer(TANConfigurationIDs.thermometerID).setRegistryName(location("tan.thermometer")),
        waterBottle = new ItemTANWaterBottle().setRegistryName(location("tan.waterBottle")),
        miscItems = new ItemTANMiscItems(TANConfigurationIDs.miscItemsID).setRegistryName(location("tan.miscItems"))
        );
    }
    
    
 /**   public static void registerItem(Item item)
    {
        String name = item.getUnlocalizedName().replace("item.", "");
        
        GameRegistry.registerItem(item, name);
  ***///      ContentRegistry.addItem(name, item);
    //}
    public static ResourceLocation location(String name) {
		return new ResourceLocation("compactxpbottles", name);

	}
}
