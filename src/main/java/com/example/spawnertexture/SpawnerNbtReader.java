package com.example.spawnertexture;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

import java.util.Optional;

/**
 * Читает mob id из NBT данных предмета спавнера.
 * Использует Data Component API (1.21+).
 */
public class SpawnerNbtReader {

    /**
     * Извлекает mob id из ItemStack спавнера.
     * Проверяет два пути: SpawnData.entity.id и SpawnPotentials[0].data.entity.id
     *
     * @param stack предмет для проверки
     * @return Optional с mob id (например "zombie") или пустой Optional
     */
    public static Optional<String> getMobId(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return Optional.empty();
        if (!stack.isOf(Items.SPAWNER)) return Optional.empty();

        // Получаем компонент BlockEntityData
        NbtComponent blockEntityData = stack.get(DataComponentTypes.BLOCK_ENTITY_DATA);
        if (blockEntityData == null) return Optional.empty();

        NbtCompound nbt = blockEntityData.copyNbt();
        if (nbt.isEmpty()) return Optional.empty();

        // Путь 1: SpawnData -> entity -> id (стандартный)
        Optional<String> result = readFromSpawnData(nbt);
        if (result.isPresent()) return result;

        // Путь 2: SpawnPotentials[0] -> data -> entity -> id (плагины)
        return readFromSpawnPotentials(nbt);
    }

    /** Читает из SpawnData.entity.id */
    private static Optional<String> readFromSpawnData(NbtCompound nbt) {
        if (!nbt.contains("SpawnData")) return Optional.empty();
        NbtCompound spawnData = nbt.getCompound("SpawnData");
        if (!spawnData.contains("entity")) return Optional.empty();
        NbtCompound entity = spawnData.getCompound("entity");
        if (!entity.contains("id")) return Optional.empty();
        return extractMobId(entity.getString("id"));
    }

    /** Читает из SpawnPotentials[0].data.entity.id (запасной для серверных плагинов) */
    private static Optional<String> readFromSpawnPotentials(NbtCompound nbt) {
        if (!nbt.contains("SpawnPotentials")) return Optional.empty();
        NbtList potentials = nbt.getList("SpawnPotentials", 10);
        if (potentials.isEmpty()) return Optional.empty();
        NbtCompound first = potentials.getCompound(0);
        if (first.contains("data")) {
            NbtCompound data = first.getCompound("data");
            if (data.contains("entity")) {
                NbtCompound entity = data.getCompound("entity");
                if (entity.contains("id")) return extractMobId(entity.getString("id"));
            }
        }
        if (first.contains("Entity")) {
            NbtCompound entity = first.getCompound("Entity");
            if (entity.contains("id")) return extractMobId(entity.getString("id"));
        }
        return Optional.empty();
    }

    /** Извлекает короткий mob id из полного (minecraft:zombie -> zombie) */
    private static Optional<String> extractMobId(String fullId) {
        if (fullId == null || fullId.isEmpty()) return Optional.empty();
        String mobId = fullId.contains(":") ? fullId.substring(fullId.indexOf(':') + 1) : fullId;
        return Optional.of(mobId);
    }
}
