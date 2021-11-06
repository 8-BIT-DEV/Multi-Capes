package net.capes;

import java.util.Map;
import java.util.HashMap;
import java.util.UUID;
import java.net.URL;
import java.lang.Exception;

import net.capes.CapeStart;

import net.minecraft.client.network.PlayerListEntry;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;

public class CapeGet {
	
	public static Map<String, Identifier> allcapes = new HashMap<String, Identifier>();
	
	public static void getCape(GameProfile profile) {
		try {
		UUID uuid = profile.getId();
		String name = profile.getName();
		String dashuuid = uuid.toString();
		String uuidno = dashuuid.replace("-", "");
		
		CapeStart.LOGGER.info("Player: " + name + " has UUID: ");
		
		URL url = new URL("https://multicape.com/cape/?user=" + uuidno);
		NativeImage capetex = NativeImage.read(url.openStream());
		NativeImageBackedTexture cape = new NativeImageBackedTexture(capetex);
		Identifier capeid = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("cape_" + profile.getName().toLowerCase(), cape);
		allcapes.put(profile.getName(), capeid);
		} catch(Exception e) {
			CapeStart.LOGGER.info(e.getMessage());
			CapeStart.LOGGER.info("No cape for user: " + profile.getName());
			allcapes.put(profile.getName(), null);
		}
	}
}
