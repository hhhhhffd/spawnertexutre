package com.example.spawnertexture;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Точка входа клиентского мода.
 * Регистрирует загрузку текстур на событие CLIENT_STARTED —
 * после полной инициализации клиента (TextureManager уже готов).
 */
public class SpawnerTextureMod implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("spawnertexture");

    @Override
    public void onInitializeClient() {
        // При старте клиента
        ClientLifecycleEvents.CLIENT_STARTED.register(SpawnerTextureManager::init);

        // При смене/перезагрузке ресурспаков
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES)
                .registerReloadListener(new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public Identifier getFabricId() {
                        return Identifier.of("spawnertexture", "texture_reload");
                    }

                    @Override
                    public void reload(ResourceManager manager) {
                        SpawnerTextureManager.init(MinecraftClient.getInstance());
                    }
                });

        LOGGER.info("SpawnerTexture мод загружен.");
    }
}
