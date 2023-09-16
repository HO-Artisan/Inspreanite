package ho.artisan.inspreanite.mixin.client.ftbquests;

import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.WidgetType;
import ho.artisan.inspreanite.Inspreanite;
import ho.artisan.inspreanite.api.utils.PushUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.krlite.equator.math.algebra.Curves;
import net.krlite.equator.math.geometry.flat.Box;
import net.krlite.equator.visual.animation.animated.AnimatedDouble;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(BaseScreen.class)
public class BaseScreenAnimator {
	@Unique
	private static final AnimatedDouble animation = new AnimatedDouble(0, 1, 175, Curves.LINEAR);

	@Redirect(method = "drawBackground", at = @At(value = "INVOKE",
			target = "Ldev/ftb/mods/ftblibrary/ui/Theme;drawGui(Lnet/minecraft/client/gui/DrawContext;IIIILdev/ftb/mods/ftblibrary/ui/WidgetType;)V"))
	private void drawBackground(Theme instance, DrawContext context, int x, int y, int w, int h, WidgetType type) {
		PushUtil.ANIMATE_BASE_SCREEN.pull(() -> PushUtil.ANIMATE_CHAPTER_PANEL.push(animation::replay));

		Box.fromCartesian(x, y, w, h).render(context,
				flat -> flat.new Rectangle(Inspreanite.Colors.IRN_CYAN.opacity(0.85 * Math.pow(animation.value(), 1 / 3.0)))
		);
	}
}
