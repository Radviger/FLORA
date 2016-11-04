package flora.core;


import flora.core.logic.KeyHandlerEnder;
import flora.core.pulse.EntityPulse;
import flora.core.pulse.RenderPulse;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy{
	public static IIcon coalPulseIcon;
	public static IIcon enderPulseIcon;
	public static IIcon cryotheumPulseIcon;
	public static IIcon pyrotheumPulseIcon;
	public static IIcon manaPulseIcon;

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
        RenderingRegistry.registerEntityRenderingHandler(EntityPulse.class, new RenderPulse(.5F));
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(new KeyHandlerEnder());
    }

	@SubscribeEvent
	public void registerIcons(TextureStitchEvent e){
		coalPulseIcon=e.map.registerIcon(ConstantsFLORA.PREFIX_MOD+"coal_pulse");

		enderPulseIcon=e.map.registerIcon(ConstantsFLORA.PREFIX_MOD+"ender_pulse");

		cryotheumPulseIcon=e.map.registerIcon(ConstantsFLORA.PREFIX_MOD+"cryotheum_pulse");

		pyrotheumPulseIcon=e.map.registerIcon(ConstantsFLORA.PREFIX_MOD+"pyrotheum_pulse");

		manaPulseIcon=e.map.registerIcon(ConstantsFLORA.PREFIX_MOD+"mana_pulse");
	}


}
