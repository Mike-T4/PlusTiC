package landmaster.plustic.util;

import java.util.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.*;
import org.apache.commons.lang3.StringUtils;
import landmaster.plustic.PlusTiC;
import landmaster.plustic.fluids.*;
import slimeknights.tconstruct.smeltery.block.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.materials.*;

public class Utils {
	public static void integrate(Map<String,Material> materials,Map<String,MaterialIntegration> materialIntegrations) {
		materials.entrySet().forEach(ent -> {
			MaterialIntegration mi;
			if (ent.getValue().getFluid() != null)
				mi = new MaterialIntegration(ent.getValue(),ent.getValue().getFluid(),StringUtils.capitalize(ent.getKey())).toolforge();
			else
				mi = new MaterialIntegration(ent.getValue());
			mi.integrate();
			mi.integrateRecipes();
			materialIntegrations.put(ent.getKey(), mi);
		});
	}
	
	public static FluidMolten fluidMetal(String name, int color) {
		return registerFluid(new FluidMolten(name,color));
	}
	
	public static void initFluidMetal(FluidMolten fluid) {
		Utils.registerMoltenBlock(fluid);
        PlusTiC.proxy.registerFluidModels(fluid);
	}
	
	public static <T extends Fluid> T registerFluid(T fluid) {
		fluid.setUnlocalizedName(PlusTiC.MODID+"."+fluid.getName().toLowerCase(Locale.US));
		FluidRegistry.registerFluid(fluid);
		return fluid;
	}
	
	public static <T extends Block> T registerBlock(T block, String name) {
		block.setUnlocalizedName(PlusTiC.MODID+"."+name);
		block.setRegistryName(PlusTiC.MODID+"."+name);
		Item ib = new ItemBlock(block).setRegistryName(block.getRegistryName());
		GameRegistry.register(block);
		GameRegistry.register(ib);
		return block;
	}
	
	public static BlockMolten registerMoltenBlock(Fluid fluid) {
		BlockMolten block = new BlockMolten(fluid);
		return registerBlock(block, "molten_" + fluid.getName());
	}
}
