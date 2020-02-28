package nessiesson.usefulmod.mixins;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(BufferBuilder.class)
public abstract class MixinBufferBuilder {
	@Shadow
	private int vertexFormatIndex;

	@Shadow
	private VertexFormatElement vertexFormatElement;

	@Shadow
	private VertexFormat vertexFormat;

	/**
	 * @author nessie, originally by jellysquid3 in the Sodium mod
	 * @reason avoid the modulo
	 */
	@Overwrite
	private void nextVertexFormatIndex() {
		final List<VertexFormatElement> elements = this.vertexFormat.getElements();
		if (++this.vertexFormatIndex >= elements.size()) {
			this.vertexFormatIndex -= elements.size();
		}

		this.vertexFormatElement = elements.get(this.vertexFormatIndex);
		if (this.vertexFormatElement.getUsage() == VertexFormatElement.EnumUsage.PADDING) {
			this.nextVertexFormatIndex();
		}
	}
}
