package flora.core;

public class ConstantsFLORA {

	public static final String modId="flora";

	public static final String modName="Fluids and Liquids on Ridiculous Armor";

	public static final String modVersion="1.0.8";

    public static final String COFH_CORE = "CoFHCore@[1.7.10R3.0.2,)";
    public static final String THERMAL_FOUNDATION = "ThermalFoundation@[1.7.10R1.0.0,)";
    public static final String REDSTONE_ARSENEL = "RedstoneArsenal@[1.7.10R1.1.0,)";
    public static final String THERMAL_EXPANSION = "ThermalExpansion";

	public static final String modDependencies = "required-after:" + COFH_CORE +";required-after:" + THERMAL_FOUNDATION +"; required-after:" + REDSTONE_ARSENEL +"; required-after:"+THERMAL_EXPANSION;
	public static final String commonProxy="flora.core.CommonProxy";

	public static final String clientProxy="flora.core.ClientProxy";

	public static final String nameArmor="fluidArmor";

	public static final String nameCreativeTab ="FLORA";

	public static final String PREFIX_MOD=modId+":";
	public static final String PREFIX_GUI = PREFIX_MOD+"textures/gui/";
	public static final String GUI_INFUSER_TEX=PREFIX_GUI+"infuser.png";


}
