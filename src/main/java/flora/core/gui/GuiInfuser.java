package flora.core.gui;

import flora.core.ConstantsFLORA;
import flora.core.block.TileInfuser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class GuiInfuser extends GuiContainer {
	TileInfuser tileInfuser;
	public GuiInfuser(TileInfuser tile, InventoryPlayer inventoryPlayer) {
		super(new ContainerInfuser(inventoryPlayer, tile));
		tileInfuser=tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.FI"), 8, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(ConstantsFLORA.GUI_INFUSER_TEX));
		this.drawTexturedModalRect(42, 25, 0, ySize, 116, 14);
		ArrayList<FluidTank> tanks= tileInfuser.getTotalFluidTank();
		int total=tileInfuser.getTotalFluidAmount();
		int currentX=44;
        GL11.glDisable(GL11.GL_BLEND);
		List<String> text=new ArrayList<String>();
		int mouseXTranslated=mouseX-guiLeft;
		int mouseYTranslated=mouseY-guiTop;
		for(FluidTank tank:tanks){
			if(tank.getFluid()!=null){
				//this.mc.renderEngine.bindTexture(new ResourceLocation(ConstantsFLORA.PREFIX_MOD+"textures/fluid/"+tank.getFluid().getFluid().getName()+".png"));
				float size=1F*tank.getFluidAmount();
				size/=total;
				size*=100;
				drawRectangleXRepeated(currentX, 27, tanks.lastIndexOf(tank)==tanks.size()-1? 144-currentX : (int)size, 16, tank.getFluid().getFluid());
				if(mouseXTranslated>currentX && mouseXTranslated<(currentX+size) && mouseYTranslated > 27 && mouseYTranslated<38){
					text.add(EnumColor.DARK_GREEN+tank.getFluid().getFluid().getLocalizedName());
					text.add(EnumColor.DARK_GREEN + "" + tank.getFluidAmount() + "mB" + EnumColor.WHITE);
				}
				currentX+=(int)size;
			}
		}

		drawHoveringText(text, mouseXTranslated, mouseYTranslated+30, fontRendererObj);
        drawTopGlassLayer();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(new ResourceLocation(ConstantsFLORA.GUI_INFUSER_TEX));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

    public void drawTopGlassLayer(){
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        this.mc.renderEngine.bindTexture(new ResourceLocation(ConstantsFLORA.GUI_INFUSER_TEX));
        this.drawTexturedModalRect(44, 27, 0, 180, 112, 10);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
    }
	//This method thanks to Paleocrafter
	public void drawRectangleXRepeated(int x, int y, int width, int tileWidth, Fluid fluid)
	{
		int numX = (int) Math.ceil((float) width / tileWidth);

		for (int x2 = 0; x2 < numX; ++x2)
		{
			int w = tileWidth;


			int tileX = w * x2;

			if (tileWidth > width)
			{
				w = width;
				tileX = w * x2;
			}
			else if (x2 == numX - 1)
			{
				if (tileWidth > width - x2 * tileWidth)
				{
					w = width - x2 * tileWidth;
					tileX = tileWidth * x2;
				}
			}

			drawRectangleStretched(x + tileX, y, fluid);
		}
	}

	public void drawRectangleStretched(int x, int y, Fluid fluid)
	{
        this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        this.drawTexturedModelRectFromIcon(x, y, getFluidTexture(fluid, false), 16, 10);
	}
    //Taken from BuildCraft... If you own it, ask and I'll remove it
    public static IIcon getFluidTexture(Fluid fluid, boolean flowing) {
        if (fluid == null) {
            return null;
        }
        IIcon icon = flowing ? fluid.getFlowingIcon() : fluid.getStillIcon();
        if (icon == null) {
            icon = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        return icon;
    }
}

