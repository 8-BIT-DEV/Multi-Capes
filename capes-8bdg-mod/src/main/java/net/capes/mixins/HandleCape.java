package net.capes.mixins;

import net.capes.CapeGet;

import net.minecraft.client.network.PlayerListEntry;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.util.Map;
import java.util.HashMap;

import net.minecraft.util.Identifier;

// 738c48dbb0503c6f90535a4abfe0d0a6

@Mixin(PlayerListEntry.class)
public abstract class HandleCape {
	@Accessor
	public abstract GameProfile getProfile();
	
	@Accessor
	public abstract Map<Type, Identifier> getTextures();
	
	private boolean loadedCape = false;
	
	@Inject(method = "getCapeTexture", at = @At("HEAD"))
	public void getModdedCape(CallbackInfoReturnable info) {
	
		if(loadedCape == true && CapeGet.allcapes.get(getProfile().getName()) != null) {
			getTextures().put(Type.CAPE, CapeGet.allcapes.get(getProfile().getName()));
		}
		
		if(loadedCape == true) return;
		
		CapeGet.getCape(getProfile());
		
		if(loadedCape == true && CapeGet.allcapes.get(getProfile().getName()) != null) {
			getTextures().put(Type.CAPE, CapeGet.allcapes.get(getProfile().getName()));
		}
		
		loadedCape = true;
	}
}
