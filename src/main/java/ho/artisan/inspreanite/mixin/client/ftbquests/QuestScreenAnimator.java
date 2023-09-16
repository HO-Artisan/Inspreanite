package ho.artisan.inspreanite.mixin.client.ftbquests;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import ho.artisan.inspreanite.Inspreanite;
import ho.artisan.inspreanite.api.utils.PushUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krlite.equator.math.geometry.flat.Box;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(QuestScreen.class)
public class QuestScreenAnimator {
	@Inject(method = "addWidgets", at = @At("TAIL"), remap = false)
	private void init(CallbackInfo ci) {
		PushUtil.ANIMATE_VIEW_QUEST_PANEL.push();
	}

	@Redirect(
			method = "drawBackground",
			at = @At(
					value = "INVOKE",
					target = "Ldev/ftb/mods/ftblibrary/icon/Color4I;draw(Lnet/minecraft/client/gui/DrawContext;IIII)V",
					ordinal = 0
			)
	)
	private void drawLeftBorder(Color4I instance, DrawContext graphics, int x, int y, int w, int h) {}

	@Redirect(
			method = "drawBackground",
			at = @At(
					value = "INVOKE",
					target = "Ldev/ftb/mods/ftblibrary/icon/Color4I;draw(Lnet/minecraft/client/gui/DrawContext;IIII)V",
					ordinal = 1
			)
	)
	private void drawLeftSide(Color4I color4I, DrawContext context, int x, int y, int w, int h) {
		Box.fromCartesian(x - 1, y - 1, w + 2, h + 2).render(context,
				flat -> flat.new Rectangle(Inspreanite.Colors.IRN_BLACK.opacity(0.127))
		);
	}

	@Redirect(
			method = "drawBackground",
			at = @At(
					value = "INVOKE",
					target = "Ldev/ftb/mods/ftblibrary/icon/Color4I;draw(Lnet/minecraft/client/gui/DrawContext;IIII)V",
					ordinal = 2
			)
	)
	private void drawRightBorder(Color4I instance, DrawContext context, int x, int y, int w, int h) {}

	@Redirect(
			method = "drawBackground",
			at = @At(
					value = "INVOKE",
					target = "Ldev/ftb/mods/ftblibrary/icon/Color4I;draw(Lnet/minecraft/client/gui/DrawContext;IIII)V",
					ordinal = 3
			)
	)
	private void drawRightSide(Color4I instance, DrawContext context, int x, int y, int w, int h) {
		Box.fromCartesian(x - 1, y - 1, w + 2, h + 2).render(context,
			flat -> flat.new Rectangle(Inspreanite.Colors.IRN_BLACK.opacity(0.127))
		);
	}
}
