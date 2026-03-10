package com.example.spawnertexture.mixin;

import com.example.spawnertexture.SpawnerNbtReader;
import com.example.spawnertexture.SpawnerTextureManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

/**
 * Миксин на HandledScreen.drawSlot — вызывается для каждого слота инвентаря.
 * @Redirect перехватывает конкретный вызов DrawContext.drawItem внутри drawSlot.
 * Если это спавнер с известным мобом — рисуем текстуру моба вместо иконки.
 */
@Mixin(HandledScreen.class)
public class ItemRendererMixin {

    @Redirect(
            method = "drawSlot(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/screen/slot/Slot;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawItem(Lnet/minecraft/item/ItemStack;III)V"
            )
    )
    private void redirectDrawItem(DrawContext context, ItemStack stack, int x, int y, int z) {
        Optional<String> mobIdOpt = SpawnerNbtReader.getMobId(stack);
        Optional<Identifier> textureOpt = mobIdOpt.flatMap(SpawnerTextureManager::getTexture);

        if (textureOpt.isPresent()) {
            // Спавнер с известным мобом — рисуем текстуру моба вместо иконки спавнера
            context.drawTexture(
                    RenderLayer::getGuiTextured,
                    textureOpt.get(),
                    x, y,
                    0.0f, 0.0f,
                    16, 16,
                    16, 16
            );
        } else {
            // Всё остальное — рисуем как обычно
            context.drawItem(stack, x, y);
        }
    }
}
