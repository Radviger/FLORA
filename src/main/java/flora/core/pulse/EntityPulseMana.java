package flora.core.pulse;

import flora.core.ClientProxy;
import flora.core.ConstantsFLORA;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPulseMana extends EntityPulse {

	public EntityPulseMana(World par1World, EntityLivingBase e, double par8, double par10, double par12) {
		super(par1World, e, par8, par10, par12);
	}

	//@Override
	//public IIcon getRenderIcon() {return ClientProxy.manaPulseIcon;}
	public EntityPulseMana(World par1World) {
		super(par1World);
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(ConstantsFLORA.PREFIX_MOD+"textures/fluid/mana_pulse.png");
	}


	@Override
	protected void onImpact(RayTraceResult var1) {
		if(var1.entityHit!=sender&&var1.entityHit!=null && var1.entityHit instanceof EntityLivingBase){
			((EntityLivingBase) var1.entityHit).curePotionEffects(new ItemStack(Items.MILK_BUCKET));
		}
		this.setDead();
	}

}
