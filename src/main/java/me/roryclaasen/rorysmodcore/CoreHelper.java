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
package me.roryclaasen.rorysmodcore;

import me.roryclaasen.rorysmod.core.Settings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class CoreHelper {

	private CoreHelper() {}

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

	public static void notifyPlayers(WorldServer world) {
		world.provider.resetRainAndThunder();
		for (Object object : world.playerEntities) {
			if (object instanceof EntityPlayer) {
				@SuppressWarnings("unused")
				EntityPlayer player = (EntityPlayer) object;
				// player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.rorysmod.sleeping.wakeup")));
				// This will spam the user of messages till someone leaves a bed
			}
		}
	}
}
