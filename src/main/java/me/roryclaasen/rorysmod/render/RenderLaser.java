/*
 * Copyright 2016-2017 Rory Claasen
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
package me.roryclaasen.rorysmod.render;

import java.awt.Color;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import me.roryclaasen.rorysmod.core.RorysMod;
import me.roryclaasen.rorysmod.core.Settings;
import me.roryclaasen.rorysmod.entity.EntityLaser;
import me.roryclaasen.rorysmod.model.ModelLaser;
import me.roryclaasen.rorysmod.util.ColorUtils;
import me.roryclaasen.rorysmod.util.NBTLaser;

import org.lwjgl.opengl.GL11;

public class RenderLaser extends Render {

	private static ResourceLocation backup = new ResourceLocation(RorysMod.MODID, "textures/entity/bolt.png");
	private ModelBase model;

	private int textureId = -1;

	public RenderLaser() {
		model = new ModelLaser();
	}

	@Override
	public ResourceLocation getEntityTexture(Entity entity) {
		return backup;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTick - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTick, 0.0F, 0.0F, 1.0F);
		bindTexture(backup);
		if (Settings.coloredLaser) {
			if (entity instanceof EntityLaser) {
				EntityLaser laser = (EntityLaser) entity;
				if (laser.getNBT() != null) {
					if (laser.getLaserData().getItemCount(NBTLaser.Items.Lens) > 0) {
						int color = ColorUtils.getIntFromColor(Color.RED);
						if (laser.getNBT().hasKey("color")) {
							color = ColorUtils.getIntColorFromIntArray(laser.getNBT().getIntArray("color"));
						}
						GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTexture(color));
					}
				}
			}
		}
		model.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	public int getTexture(int color) {
		return getTexture(new Color(color));
	}

	public int getTexture(Color color) {
		if (textureId == -1) textureId = ColorUtils.loadTextureFromColour(color, 64, 32);
		return textureId;
	}
}
