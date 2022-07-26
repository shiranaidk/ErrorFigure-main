package errorfigure.utils.render;

import java.awt.Color;

import errorfigure.utils.Helper;
import errorfigure.utils.math.Vec2f;
import errorfigure.utils.math.Vec3f;
import errorfigure.utils.render.gl.GLClientState;
import errorfigure.utils.render.gl.GLColor;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.Minecraft;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.List;

import org.lwjgl.util.glu.GLU;
import pw.knx.feather.tessellate.Tessellation;

public class RenderUtils
{
    public static final Tessellation tessellator;
    private static final List<Integer> csBuffer;
    private static final Consumer<Integer> ENABLE_CLIENT_STATE;
    private static final Consumer<Integer> DISABLE_CLIENT_STATE;
    public static float delta;

    static {
        tessellator = Tessellation.createExpanding(4, 1.0f, 2.0f);
        csBuffer = new ArrayList<Integer>();
        ENABLE_CLIENT_STATE = GL11::glEnableClientState;
        DISABLE_CLIENT_STATE = GL11::glEnableClientState;
    }

    public RenderUtils() {
        super();
    }
    public static void enableGL2D() {
        GL11.glDisable((int) 2929);
        GL11.glEnable((int) 3042);
        GL11.glDisable((int) 3553);
        GL11.glBlendFunc((int) 770, (int) 771);
        GL11.glDepthMask((boolean) true);
        GL11.glEnable((int) 2848);
        GL11.glHint((int) 3154, (int) 4354);
        GL11.glHint((int) 3155, (int) 4354);
    }

    public static void disableGL2D() {
        GL11.glEnable((int) 3553);
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 2929);
        GL11.glDisable((int) 2848);
        GL11.glHint((int) 3154, (int) 4352);
        GL11.glHint((int) 3155, (int) 4352);
    }

    public static void drawImage(final ResourceLocation image, final int x, final int y, final int width,
                                 final int height) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (float) width, (float) height);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }

    public static int height() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight();
    }

    public static double interpolation(final double newPos, final double oldPos) {
        return oldPos + (newPos - oldPos) * Helper.mc.timer.renderPartialTicks;
    }

    public static void drawHead(AbstractClientPlayer target, int x, int y, int width, int height) {
        ResourceLocation skin = target.getLocationSkin();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 40.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
    }

    public static double getAnimationState(double animation, final double finalState, final double speed) {
        final float add = (float) (0.01 * speed);
        animation = ((animation < finalState) ? ((animation + add < finalState) ? (animation += add) : finalState)
                : ((animation - add > finalState) ? (animation -= add) : finalState));
        return animation;
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(x, y + height, 0.0).tex(u * f, (v + (float)vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y + height, 0.0).tex((u + (float)uWidth) * f, (v + (float)vHeight) * f1).endVertex();
        worldrenderer.pos(x + width, y, 0.0).tex((u + (float)uWidth) * f, v * f1).endVertex();
        worldrenderer.pos(x, y, 0.0).tex(u * f, v * f1).endVertex();
        tessellator.draw();
    }

    public static int getHexRGB(final int hex) {
        return 0xFF000000 | hex;
    }

    public static void drawCustomImage(int x, int y, int width, int height, ResourceLocation image) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Minecraft.getMinecraft().getTextureManager().bindTexture(image);
        Gui.drawModalRectWithCustomSizedTexture((int)x, (int)y, (float)0.0f, (float)0.0f, (int)width, (int)height, (float)width, (float)height);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawRect(float x1, float y1, float x2, float y2, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GLColor.color(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        Gui.drawRect(0, 0, 0, 0, 0);
    }

    public static void drawRect(double x1, double y1, double x2, double y2, int color) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GLColor.color(color);
        GL11.glBegin(7);
        GL11.glVertex2d(x2, y1);
        GL11.glVertex2d(x1, y1);
        GL11.glVertex2d(x1, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glPopMatrix();
        Gui.drawRect(0, 0, 0, 0, 0);
    }

    public static void drawRoundRect(double xPosition, double yPosition, double endX, double endY, int radius,
                                     int color) {
        double width = endX - xPosition;
        double height = endY - yPosition;
        Gui.drawRect(xPosition + radius, yPosition + radius, xPosition + width - radius, yPosition + height - radius,
                color);
        Gui.drawRect(xPosition, yPosition + radius, xPosition + radius, yPosition + height - radius, color);
        Gui.drawRect(xPosition + width - radius, yPosition + radius, xPosition + width, yPosition + height - radius,
                color);
        Gui.drawRect(xPosition + radius, yPosition, xPosition + width - radius, yPosition + radius, color);
        Gui.drawRect(xPosition + radius, yPosition + height - radius, xPosition + width - radius, yPosition + height,
                color);
        Gui.drawFilledCircle(xPosition + radius, yPosition + radius, radius, color, 1);
        Gui.drawFilledCircle(xPosition + radius, yPosition + height - radius, radius, color, 2);
        Gui.drawFilledCircle(xPosition + width - radius, yPosition + radius, radius, color, 3);
        Gui.drawFilledCircle(xPosition + width - radius, yPosition + height - radius, radius, color, 4);
    }

    public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor) {
        enableGL2D();
        GL11.glShadeModel((int) 7425);
        GL11.glBegin((int) 7);
        RenderHelper.glColor(topColor);
        GL11.glVertex2f((float) x, (float) y1);
        GL11.glVertex2f((float) x1, (float) y1);
        RenderHelper.glColor(bottomColor);
        GL11.glVertex2f((float) x1, (float) y);
        GL11.glVertex2f((float) x, (float) y);
        GL11.glEnd();
        GL11.glShadeModel((int) 7424);
        disableGL2D();
    }

    public static void drawVLine(float x, float y, float x1, int y1) {
        if (x1 < y) {
            float var5 = y;
            y = x1;
            x1 = var5;
        }
        drawRect(x, y + 1.0f, x + 1.0f, x1, y1);
    }

    public static void drawHLine(float x, float y, float x1, int y1) {
        if (y < x) {
            float var5 = x;
            x = y;
            y = var5;
        }
        drawRect(x, x1, y + 1.0f, x1 + 1.0f, y1);
    }

    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1, final int col1, final int col2) {
        Gui.drawRect(x, y, x2, y2, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
    }

    public static void pre() {
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
    }

    public static void post() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glColor3d(1.0, 1.0, 1.0);
    }

    public static void startDrawing() {
        GL11.glEnable(3042);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        Helper.mc.entityRenderer.setupCameraTransform(Helper.mc.timer.renderPartialTicks, 0);
    }

    public static void stopDrawing() {
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
    }

    public static Color blend(final Color color1, final Color color2, final double ratio) {
        final float r = (float)ratio;
        final float ir = 1.0f - r;
        final float[] rgb1 = new float[3];
        final float[] rgb2 = new float[3];
        color1.getColorComponents(rgb1);
        color2.getColorComponents(rgb2);
        final Color color3 = new Color(rgb1[0] * r + rgb2[0] * ir, rgb1[1] * r + rgb2[1] * ir, rgb1[2] * r + rgb2[2] * ir);
        return color3;
    }

    public static void drawLine(final Vec2f start, final Vec2f end, final float width) {
        drawLine(start.getX(), start.getY(), end.getX(), end.getY(), width);
    }

    public static void drawLine(final Vec3f start, final Vec3f end, final float width) {
        drawLine((float)start.getX(), (float)start.getY(), (float)start.getZ(), (float)end.getX(), (float)end.getY(), (float)end.getZ(), width);
    }

    public static void drawLine(final float x, final float y, final float x1, final float y1, final float width) {
        drawLine(x, y, 0.0f, x1, y1, 0.0f, width);
    }

    public static void drawLine(final float x, final float y, final float z, final float x1, final float y1, final float z1, final float width) {
        GL11.glLineWidth(width);
        setupRender(true);
        setupClientState(GLClientState.VERTEX, true);
        RenderUtils.tessellator.addVertex(x, y, z).addVertex(x1, y1, z1).draw(3);
        setupClientState(GLClientState.VERTEX, false);
        setupRender(false);
    }

    public static void setupClientState(final GLClientState state, final boolean enabled) {
        RenderUtils.csBuffer.clear();
        if (state.ordinal() > 0) {
            RenderUtils.csBuffer.add(state.getCap());
        }
        RenderUtils.csBuffer.add(32884);
        RenderUtils.csBuffer.forEach(enabled ? RenderUtils.ENABLE_CLIENT_STATE : RenderUtils.DISABLE_CLIENT_STATE);
    }

    public static void setupRender(final boolean start) {
        if (start) {
            GlStateManager.enableBlend();
            GL11.glEnable(2848);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            GlStateManager.blendFunc(770, 771);
            GL11.glHint(3154, 4354);
        }
        else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            GL11.glDisable(2848);
            GlStateManager.enableDepth();
        }
        GlStateManager.depthMask(!start);
    }

    public static double[] convertTo2D(final double x, final double y, final double z) {
        final FloatBuffer screenCoords = BufferUtils.createFloatBuffer(3);
        final IntBuffer viewport = BufferUtils.createIntBuffer(16);
        final FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
        final FloatBuffer projection = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(2982, modelView);
        GL11.glGetFloat(2983, projection);
        GL11.glGetInteger(2978, viewport);
        final boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport,
                screenCoords);
        return (double[]) (result
                ? new double[] { screenCoords.get(0), Display.getHeight() - screenCoords.get(1), screenCoords.get(2) }
                : null);
    }
}
