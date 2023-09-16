package ho.artisan.inspreanite.mixin.client.ftbquests;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.ui.ContextMenu;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import ho.artisan.inspreanite.Inspreanite;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krlite.equator.math.algebra.Curves;
import net.krlite.equator.math.geometry.flat.Box;
import net.krlite.equator.visual.animation.animated.AnimatedDouble;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(ContextMenu.class)
public class ContextMenuAnimator {
	@Unique
	private static final AnimatedDouble animation = new AnimatedDouble(0, 1, 250, Curves.Back.OUT);

	@Inject(method = "<init>", at = @At("TAIL"), remap = false)
	private void init(Panel panel, List<ContextMenuItem> contextMenuItems, CallbackInfo ci) {
		animation.replay();
	}

	@Redirect(method = "draw", at = @At(value = "INVOKE", target = "Ldev/ftb/mods/ftblibrary/icon/Color4I;draw(Lnet/minecraft/client/gui/DrawContext;IIII)V"))
	private void draw(Color4I instance, DrawContext context, int x, int y, int w, int h) {}

	@Redirect(method = "drawBackground", at = @At(value = "INVOKE", target = "Ldev/ftb/mods/ftblibrary/ui/Theme;drawContextMenuBackground(Lnet/minecraft/client/gui/DrawContext;IIII)V"))
	private void drawBackground(Theme instance, DrawContext context, int x, int y, int w, int h) {
		Box.fromCartesian(x, y, w, h).scaleCenter(1 + 1.2 * (1 - animation.value()))
				.render(context,
						flat -> flat.new Rectangle(Inspreanite.Colors.IRN_DIM_CYAN.opacity(Math.min(1, 0.75 * Math.pow(animation.value(), 2))))
				);
	}
}
