/*
 * Copyright 2017 Rory Claasen
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.roryclaasen.asm.rorysmodcore.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.roryclaasen.rorysmod.core.Settings;

public class Helper {

	private Helper() {}

	public static boolean shouldWakeUpNow() {
		return !Settings.enableSleepInDay;
	}

	public static boolean shouldWakeUpNow(EntityPlayer player) {
		World world = player.worldObj;
		if (!world.isRemote) {
			if (Settings.enableSleepInDay) return false;
			if (world.isDaytime()) return true;
		}
		return false;
	}
}
