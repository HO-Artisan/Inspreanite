package ho.artisan.inspreanite.mixin.client.ftbquests;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestPanel;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.theme.property.ColorProperty;
import ho.artisan.inspreanite.Inspreanite;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(QuestPanel.class)
public class QuestPanelAnimator {
	@Redirect(
			method = "drawOffsetBackground",
			at = @At(
					value = "INVOKE",
					target = "Ldev/ftb/mods/ftbquests/quest/theme/property/ColorProperty;get(Ldev/ftb/mods/ftbquests/quest/QuestObjectBase;)Ljava/lang/Object;",
					ordinal = 0
			), remap = false
	)
	private Object questDependencyColor(ColorProperty colorProperty, QuestObjectBase questObjectBase) {
		return Color4I.rgb(Inspreanite.Colors.QUEST_DEPENDENCY.toColor().getRGB());
	}

	@Redirect(
			method = "drawOffsetBackground",
			at = @At(
					value = "INVOKE",
					target = "Ldev/ftb/mods/ftbquests/quest/theme/property/ColorProperty;get(Ldev/ftb/mods/ftbquests/quest/QuestObjectBase;)Ljava/lang/Object;",
					ordinal = 1
			), remap = false
	)
	private Object questDependentColor(ColorProperty colorProperty, QuestObjectBase questObjectBase) {
		return Color4I.rgb(Inspreanite.Colors.QUEST_DEPENDENCY.toColor().getRGB());
	}

	@Redirect(
			method = "drawOffsetBackground",
			at = @At(
					value = "INVOKE",
					target = "Ldev/ftb/mods/ftbquests/quest/theme/property/ColorProperty;get(Ldev/ftb/mods/ftbquests/quest/QuestObjectBase;)Ljava/lang/Object;",
					ordinal = 2
			), remap = false
	)
	private Object questCompletedColor(ColorProperty colorProperty, QuestObjectBase questObjectBase) {
		return Color4I.rgb(Inspreanite.Colors.QUEST_DEPENDENCY.toColor().getRGB());
	}
}
