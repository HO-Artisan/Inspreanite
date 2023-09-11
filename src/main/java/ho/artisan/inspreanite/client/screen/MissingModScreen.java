package ho.artisan.inspreanite.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import ho.artisan.inspreanite.api.utils.mods.IrnModDeps;
import ho.artisan.inspreanite.client.InspreaniteClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.krlite.equator.render.frame.FrameInfo;
import net.krlite.equator.visual.text.Paragraph;
import net.krlite.equator.visual.text.Section;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import ho.artisan.inspreanite.Inspreanite;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

@Environment(EnvType.CLIENT)
@SuppressWarnings("all")
public class MissingModScreen extends Screen {
    @Nullable
    private final ArrayList<IrnModDeps> missingMods;
    @Nullable
    protected MinecraftClient client = super.client;
    @Nullable
    private final Screen parent;
    private final boolean renderBackgroundTexture;

    public MissingModScreen(@Nullable ArrayList<IrnModDeps> missingMods) {
        this(missingMods, null, MinecraftClient.getInstance().world == null);
    }

    public MissingModScreen(@Nullable ArrayList<IrnModDeps> missingMods, @Nullable Screen parent) {
        this(missingMods, parent, MinecraftClient.getInstance().world == null);
    }

    public MissingModScreen(@Nullable ArrayList<IrnModDeps> missingMods, boolean renderBackgroundTexture) {
        this(missingMods, null, renderBackgroundTexture);
    }

    public MissingModScreen(@Nullable ArrayList<IrnModDeps> missingMods, @Nullable Screen parent, boolean renderBackgroundTexture) {
        super(
                IrnModDeps.getAllMissing().size() == 1
                        ? Inspreanite.genTranslatableText("screen", "missing_mod", "title")
                        : Text.translatable(
                        Inspreanite.genTranslationKey("screen", "missing_mod", "title_plural"),
                        IrnModDeps.getAllMissing().size()
                )
        );
        this.missingMods = missingMods;
        this.parent = parent;
        this.renderBackgroundTexture = renderBackgroundTexture;
    }

    public boolean isPauseScreen() {
        return renderBackgroundTexture;
    }

    public boolean shouldCloseOnEsc() {
        return this.parent != null;
    }

    public void onClose() {
        if (this.client != null && this.shouldCloseOnEsc())
            this.client.setScreen(parent);
    }

    protected void init() {
        if (missingMods != null && !missingMods.isEmpty()) {
            int widest = missingMods.stream().map(mod -> mod.getRawName().length())
                    .max(Comparator.naturalOrder()).orElse(0);
            String brackets = "[" + " ".repeat(widest + 17) + "]";
            AtomicInteger index = new AtomicInteger(0);

            missingMods.forEach(mod -> {
                Text text = Text.literal(mod.getRawName())
                        .formatted(mod.hasUrl() ? Formatting.RESET : Formatting.STRIKETHROUGH)
                        .formatted(mod.isRequired() ? Formatting.RED : Formatting.LIGHT_PURPLE);
                int y = 60 + (height - 90) * index.incrementAndGet() / ((int) missingMods.size() + 1);
                // Brackets
                modDownloadButton(mod, Text.literal(brackets).formatted(Formatting.DARK_PURPLE), y);
                // Names
                modDownloadButton(mod, text, y);
            });

            if (this.shouldCloseOnEsc()) {
                Text blank = Text.literal(" ".repeat(widest / 2 - 2));
                Text quit = Text.literal("[").append(blank).append("×").append(blank).append("]")
                        .formatted(Formatting.RED);
                Text skip = Text.literal("[").append(blank).append("→").append(blank).append("]")
                        .formatted(Formatting.WHITE);
                // Bracketed icons
                skipAndQuitButton(quit, skip, this.height - 30, this.textRenderer.getWidth(brackets));
            } else {
                // Brackets
                quitButton(Text.literal(brackets).formatted(Formatting.RED), this.height - 30);
                // Icon
                quitButton(Text.literal("×").formatted(Formatting.RED), this.height - 30);
            }
        } else {
            this.client.setScreen(parent);
        }
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        if (this.renderBackgroundTexture) this.renderBackground(drawContext);

        super.render(drawContext, mouseX, mouseY, delta);

        MutableText title = Text.literal(this.title.getString()).formatted(Formatting.BOLD);
        MutableText subtitle = Inspreanite.genTranslatableText("screen", "missing_mod", "subtitle" + (missingMods.size() == 1 ? "" : "_plural")).copy();
        MutableText description = Inspreanite.genTranslatableText("screen", "missing_mod", "description").copy();

        FrameInfo.scaled().translate(0, -0.7).render(drawContext,
                flat -> flat.new Text(
                        section -> section
                                .appendTitle(title)
                                .append(subtitle)
                                .append(description)
                ).horizontalAlignment(Paragraph.Alignment.CENTER).verticalAlignment(Section.Alignment.BOTTOM)
        );
    }

    public void renderBackground(DrawContext drawContext) {
        if (InspreaniteClient.cubeMapRenderer() != null) {
            drawContext.getMatrices().push();
            drawContext.getMatrices().loadIdentity();
            drawContext.getMatrices().translate(0.0F, 0.0F, -2000.0F);
            RenderSystem.applyModelViewMatrix();

            InspreaniteClient.cubeMapRenderer().render(MinecraftClient.getInstance().getLastFrameDuration(), 1);

            drawContext.getMatrices().pop();
            RenderSystem.applyModelViewMatrix();
        } else {
            super.renderBackground(
                    new DrawContext(MinecraftClient.getInstance(),
                    VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer())));
        }
    }

    private void modDownloadButton(IrnModDeps mod, Text text, int y) {
        this.addDrawableChild(
                new PressableTextWidget(
                        this.width / 2 - this.textRenderer.getWidth(text) / 2, y,
                        this.textRenderer.getWidth(text), 10, text, buttonWidget -> mod.openUrl(),
                        this.textRenderer
                )
        );
    }

    private void skipAndQuitButton(Text quit, Text skip, int y, int wideness) {
        // Quit
        this.addDrawableChild(
                new PressableTextWidget(
                        this.width / 2 - wideness / 2, y,
                        this.textRenderer.getWidth(quit), 10, quit, buttonWidget -> {
                    // Open mods folder
                    Util.getOperatingSystem()
                            .open(FabricLoader.getInstance().getGameDir().resolve("mods").toFile());
                    if (this.client != null) client.stop();
                }, this.textRenderer
                )
        );

        // Skip
        this.addDrawableChild(
                new PressableTextWidget(
                        this.width / 2 + wideness / 2 - this.textRenderer.getWidth(skip), y,
                        this.textRenderer.getWidth(skip), 10, skip, buttonWidget -> this.onClose(),
                        this.textRenderer
                )
        );
    }

    private void quitButton(Text quit, int y) {
        this.addDrawableChild(
                new PressableTextWidget(
                        this.width / 2 - this.textRenderer.getWidth(quit) / 2, y,
                        this.textRenderer.getWidth(quit), 10, quit, buttonWidget -> {
                    // Open mods folder
                    Util.getOperatingSystem()
                            .open(FabricLoader.getInstance().getGameDir().resolve("mods").toFile());
                    if (this.client != null) client.stop();
                }, this.textRenderer
                )
        );
    }
}
