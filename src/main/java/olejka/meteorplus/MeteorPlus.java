package olejka.meteorplus;
//import olejka.meteorplus.commands.*;
import olejka.meteorplus.modules.*;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
//import meteordevelopment.meteorclient.systems.commands.Commands;

import net.minecraft.item.Items;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class MeteorPlus extends MeteorAddon {
	public static final Logger LOG = LoggerFactory.getLogger(MeteorPlus.class);
	public static final Category CATEGORY = new Category("Meteor Plus", Items.EMERALD_BLOCK.getDefaultStack());

	@Override
	public void onInitialize() {
		LOG.info("Initializing MeteorPlus");

		// Required when using @EventHandler
		MeteorClient.EVENT_BUS.registerLambdaFactory("olejka.meteorplus", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

		//Modules
		Modules.get().add(new XrayBruteforce());
		Modules.get().add(new AutoLeave());
		Modules.get().add(new AutoAccept());

		//Commands
		//Commands.get().add();
	}

	@Override
	public void onRegisterCategories() {
		Modules.registerCategory(CATEGORY);
	}
}