package nekiplay.meteorplus.features.modules.timer.modes;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.world.Timer;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import nekiplay.meteorplus.features.modules.timer.TimerMode;
import nekiplay.meteorplus.features.modules.timer.TimerModes;
import nekiplay.meteorplus.features.modules.timer.TimerPlus;

import static nekiplay.meteorplus.features.modules.timer.TimerPlus.*;

public class NCP extends TimerMode {
	private Timer timer;
	private TimerPlus timerPlus;
	public NCP() {
		super(TimerModes.NCP);
		timer = Modules.get().get(Timer.class);
		timerPlus = Modules.get().get(TimerPlus.class);
	}

	@Override
	public void onDeactivate() {
		timer.setOverride(Timer.OFF);
	}

	@Override
	public void onTickEventPre(TickEvent.Pre event) {
		if (mc.player == null) return;
		if (rechargeTimer == 0) {
			if (workingTimer > workingDelay) {
				rechargeTimer = rechargeDelay;
				workingTimer = 0;
				timer.setOverride(Timer.OFF);
			}
			else {
				if (timerPlus.isActive()) {
					if (timerPlus.onlyInMove.get() && PlayerUtils.isMoving()) {
						workingTimer++;
						timer.setOverride(timerMultiplier);
					}
					else if (!timerPlus.onlyInMove.get()) {
						workingTimer++;
						timer.setOverride(timerMultiplier);
					}
					else {
						timer.setOverride(timerMultiplierOnRecharge);
					}
				}
			}
		}
		else {
			rechargeTimer--;
			if (timerPlus.isActive()) {
				timer.setOverride(timerMultiplierOnRecharge);
			}
			else {
				timer.setOverride(Timer.OFF);
			}
		}
	}
}