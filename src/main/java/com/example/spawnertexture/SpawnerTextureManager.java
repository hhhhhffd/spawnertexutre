package com.example.spawnertexture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Менеджер текстур для спавнеров.
 * Проверяет наличие текстур через ResourceManager (охватывает jar мода и ресурспаки),
 * регистрирует найденные в TextureManager и кеширует их Identifier'ы.
 * Путь текстур в ресурспаке: assets/spawnertexture/textures/mob/<mob_id>.png
 */
public class SpawnerTextureManager {

    private static final Logger LOGGER = LoggerFactory.getLogger("spawnertexture");

    /** Кеш: ключ — mob id (например "zombie"), значение — зарегистрированный Identifier */
    private static final Map<String, Identifier> textureCache = new HashMap<>();

    private static final String MOD_ID = "spawnertexture";

    static final String[] KNOWN_MOBS = {
            "item",
            "zombie", "skeleton", "spider", "cave_spider", "creeper", "enderman",
            "blaze", "witch", "slime", "magma_cube", "silverfish", "endermite",
            "ghast", "zombified_piglin", "piglin", "piglin_brute", "hoglin",
            "zoglin", "strider", "wither_skeleton", "guardian", "elder_guardian",
            "shulker", "vindicator", "evoker", "vex", "pillager", "ravager",
            "drowned", "husk", "stray", "phantom", "turtle", "cod", "salmon",
            "tropical_fish", "pufferfish", "dolphin", "squid", "glow_squid",
            "bat", "bee", "fox", "ocelot", "cat", "wolf", "panda", "polar_bear",
            "llama", "trader_llama", "horse", "donkey", "mule", "skeleton_horse", "zombie_horse",
            "pig", "cow", "sheep", "chicken", "rabbit", "mooshroom", "parrot",
            "snow_golem", "iron_golem", "villager", "wandering_trader", "zombie_villager",
            "warden", "allay", "frog", "tadpole", "axolotl", "goat", "camel",
            "sniffer", "armadillo", "breeze", "bogged", "creaking"
    };

    /**
     * Инициализация: проверяет текстуры через ResourceManager
     * (jar мода + все подключённые ресурспаки) и кеширует найденные Identifier'ы.
     * Вызывается через CLIENT_STARTED — ResourceManager уже готов.
     */
    public static void init(MinecraftClient client) {
        textureCache.clear();
        int count = 0;

        for (String mobId : KNOWN_MOBS) {
            // Identifier соответствует пути assets/spawnertexture/textures/mob/<mob>.png
            // ResourceManager ищет этот путь во всех ресурспаках (включая jar мода)
            Identifier id = Identifier.of(MOD_ID, "textures/mob/" + mobId + ".png");

            if (client.getResourceManager().getResource(id).isPresent()) {
                client.getTextureManager().registerTexture(id, new ResourceTexture(id));
                textureCache.put(mobId, id);
                count++;
            }
        }

        LOGGER.info("SpawnerTexture: зарегистрировано {} текстур мобов.", count);
    }

    /**
     * Возвращает Identifier текстуры для указанного mob id, или пустой Optional.
     *
     * @param mobId id моба (например "zombie")
     */
    public static Optional<Identifier> getTexture(String mobId) {
        return Optional.ofNullable(textureCache.get(mobId));
    }
}
