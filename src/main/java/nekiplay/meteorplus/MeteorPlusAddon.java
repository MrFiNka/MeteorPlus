package nekiplay.meteorplus;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.gui.tabs.Tabs;
import meteordevelopment.meteorclient.systems.modules.misc.BetterChat;
import nekiplay.MixinPlugin;
import nekiplay.meteorplus.features.commands.*;
import nekiplay.meteorplus.features.modules.combat.*;
import nekiplay.meteorplus.features.modules.combat.killaura.KillAuraPlus;
import nekiplay.meteorplus.features.modules.combat.velocity.VelocityPlus;
import nekiplay.meteorplus.features.modules.misc.*;
import nekiplay.meteorplus.features.modules.movement.*;
import nekiplay.meteorplus.features.modules.movement.elytrafly.ElytraFlyPlus;
import nekiplay.meteorplus.features.modules.movement.noslow.NoSlowPlus;
import nekiplay.meteorplus.features.modules.player.*;
import nekiplay.meteorplus.features.modules.render.*;
import nekiplay.meteorplus.features.modules.render.holograms.*;
import nekiplay.meteorplus.features.modules.world.*;
import nekiplay.meteorplus.features.modules.integrations.*;
import nekiplay.meteorplus.features.modules.world.autoobsidianmine.AutoObsidianFarm;
import nekiplay.meteorplus.gui.tabs.HiddenModulesTab;
import nekiplay.meteorplus.hud.TimerPlusCharge;
import nekiplay.meteorplus.features.modules.integrations.LitematicaPrinter;
import nekiplay.meteorplus.features.modules.integrations.MapIntegration;
import nekiplay.meteorplus.features.modules.world.timer.TimerPlus;
import nekiplay.meteorplus.items.ModItems;
import nekiplay.meteorplus.settings.ConfigModifier;
import net.fabricmc.loader.api.FabricLoader;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudGroup;
import net.minecraft.item.ItemStack;
import nekiplay.meteorplus.features.modules.movement.fastladder.FastLadderPlus;
import nekiplay.meteorplus.features.modules.movement.fly.FlyPlus;
import nekiplay.meteorplus.features.modules.movement.jesus.JesusPlus;
import nekiplay.meteorplus.features.modules.movement.nofall.NoFallPlus;
import nekiplay.meteorplus.features.modules.movement.speed.SpeedPlus;
import nekiplay.meteorplus.features.modules.movement.spider.SpiderPlus;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static nekiplay.MixinPlugin.*;

public class MeteorPlusAddon extends MeteorAddon {
	public static final Logger LOG = LoggerFactory.getLogger(MeteorPlusAddon.class);
	public static final ItemStack logo_mods_item = ModItems.LOGO_MODS_ITEM.getDefaultStack();

	public static final Category CATEGORYMODS = new Category("Integrations", logo_mods_item);
	public static final HudGroup HUD_GROUP = new HudGroup("Meteor+ Hud");
	public static final String LOGPREFIX = "[Meteor+]";

	private static MeteorPlusAddon instance;

	public static MeteorPlusAddon getInstance() {
		return instance;
	}

	@Override
	public void onInitialize() {
		instance = this;

		LOG.info(LOGPREFIX + " Initializing...");

		if (isXaeroWorldMapresent) {
			if (!isBaritonePresent) {
				LOG.warn(LOGPREFIX + " [Baritone] not found, disabling Xaero's World Map improvement");
			}
			else {
				LOG.info(LOGPREFIX + " [Baritone] found, enabling Xaero's World Map improvement");
			}
		}
		else {
			LOG.warn(LOGPREFIX + " [Xaero's World Map] not found, disabling Xaero's World Map improvement");
		}
		if (isJourneyMapPresent) {
			if (!isBaritonePresent) {
				LOG.warn(LOGPREFIX + " [Baritone] not found, disabling Journey Map improvement");
			}
			else {
				LOG.info(LOGPREFIX + " [Baritone] found, enabling Journey Map improvement");
			}
		}
		else {
			LOG.warn(LOGPREFIX + " [Journey Map] not found, disabling Journey Map improvement");
		}
		if (!isBaritonePresent) {
			LOG.warn(LOGPREFIX + " [Baritone] not found, disabling Freecam and Waypoints improvement");
		}
		else {
			LOG.info(LOGPREFIX + " [Baritone] found, enabling Freecam and Waypoints improvement");
		}

		if (!isWhereIsIt) {
			LOG.warn(LOGPREFIX + " [Where is it] not found, disabling ChestTracker improvement");
		}
		else {
			LOG.info(LOGPREFIX + " [Where is it] found, enabling ChestTracker improvement");
		}

		MeteorClient.EVENT_BUS.subscribe(new CordinateProtector());
		ConfigModifier.get();

		//region Commands
		LOG.info(LOGPREFIX + " Initializing commands...");

		Commands.add(new ItemRawIdCommand());
		Commands.add(new Eclip());
		Commands.add(new ClearInventory());
		if (isBaritonePresent) {
			Commands.add(new GotoPlus());
		}
		else {
			LOG.warn(LOGPREFIX + " [Baritone] not found, disabling Goto+");
		}
		Commands.add(new GPT());

		LOG.info(LOGPREFIX + " Loaded commands");
		//endregion

		LOG.info(LOGPREFIX + " Initializing better chat custom head...");
		BetterChat.registerCustomHead("[Meteor+]", new Identifier("meteorplus", "chat/icon.png"));
		LOG.info(LOGPREFIX + " Loaded better chat");


		//region Modules
		LOG.info(LOGPREFIX + " Initializing modules...");
		Modules modules = Modules.get();

		modules.add(new Teams());
		modules.add(new HologramModule());
		modules.add(new SprintPlus());
		modules.add(new ChatPrefix());
		modules.add(new ChatGPT());
		modules.add(new ItemHighlightPlus());
		modules.add(new FastLadderPlus());
		modules.add(new TriggerBot());
		modules.add(new EyeFinder());
		modules.add(new InventoryMovePlus());
		modules.add(new MiddleClickExtraPlus());
		modules.add(new AutoDropPlus());
		modules.add(new NoFallPlus());
		modules.add(new TimerPlus());
		modules.add(new SpeedPlus());
		modules.add(new FlyPlus());
		modules.add(new SpiderPlus());
		modules.add(new JesusPlus());
		modules.add(new BedrockStorageBruteforce());
		modules.add(new AutoCraftPlus());
		modules.add(new AutoObsidianFarm());
		modules.add(new XrayBruteforce());
		modules.add(new AutoLeave());
		modules.add(new AutoAccept());
		modules.add(new GhostBlockFixer());
		modules.add(new SafeMine());
		modules.add(new Freeze());
		modules.add(new AntiBotPlus());
		modules.add(new MultiTasks());
		modules.add(new ItemFrameEsp());
		modules.add(new KillAuraPlus());
		modules.add(new ElytraFlyPlus());
		modules.add(new VelocityPlus());
		if (!MixinPlugin.isMeteorRejects) {
			modules.add(new NoJumpDelay());
		}
		else {
			LOG.warn(LOGPREFIX + " Meteor Rejects detected, removing No Jump Delay");
		}
		modules.add(new NoSlowPlus());
		//modules.add(new VelocityPlus());
		if (isBaritonePresent) {
			if (isXaeroWorldMapresent || isJourneyMapPresent) {
				modules.add(new MapIntegration());
				LOG.info(LOGPREFIX + " Loaded mini-map integration");
			}
		}
		if (isLitematicaMapresent && isBaritonePresent) {
			modules.add(new LitematicaPrinter());
			LOG.info(LOGPREFIX + " Loaded litematica integration");
		}
		if (isWhereIsIt) {
			modules.add(new WhereIsIt());
			LOG.info(LOGPREFIX + " Loaded where is it integration");
		}
		LOG.info(LOGPREFIX + " Loaded modules");
		//endregion

		//region Hud
		LOG.info(LOGPREFIX + " Initializing hud...");

		Hud.get().register(TimerPlusCharge.INFO);

		LOG.info(LOGPREFIX + " Loaded hud");
		//endregion

		//region Tabs
		LOG.info(LOGPREFIX + " Initializing tabs...");

		Tabs.add(new HiddenModulesTab());

		LOG.info(LOGPREFIX + " Loaded tabs");
		//endregion
		LOG.info(LOGPREFIX + " Full loaded");
	}

	@Override
	public void onRegisterCategories() {
		LOG.info(LOGPREFIX + " registering categories...");
		if (isXaeroWorldMapresent ||
			isJourneyMapPresent ||
			MixinPlugin.isLitematicaMapresent ||
			MixinPlugin.isWhereIsIt
		) {
			Modules.registerCategory(CATEGORYMODS);
		}
		//Modules.registerCategory(CATEGORY);
		LOG.info(LOGPREFIX + " register categories");
	}

	@Override
	public String getWebsite() {
		return "https://meteor-plus.com/";
	}

	@Override
	public GithubRepo getRepo() {
		return new GithubRepo("Nekiplay", "MeteorPlus",  "main", null);
	}

	@Override
	public String getCommit() {
		String commit = FabricLoader
			.getInstance()
			.getModContainer("meteorplus")
			.get().getMetadata()
			.getCustomValue("github:sha")
			.getAsString();
		return commit.isEmpty() ? null : commit.trim();
	}

	@Override
	public String getPackage() {
		return "nekiplay.meteorplus";
	}
}
