package nessiesson.usefulmod

import com.mumfrey.liteloader.client.gui.GuiCheckbox
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel
import com.mumfrey.liteloader.modconfig.ConfigPanelHost
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties

class ConfigPanel : AbstractConfigPanel() {
	private val config = LiteModUsefulMod.config

	override fun addOptions(host: ConfigPanelHost) {
		val spacing = 16
		val settings = Config::class.declaredMemberProperties.sortedBy { it.name }
		for ((id, field) in settings.withIndex()) {
			this.addControl(GuiCheckbox(id, 0, spacing * id, field.name), {
				it.checked = !it.checked
				if (field is KMutableProperty<*>) {
					field.setter.call(config, it.checked)
				}
			}).checked = field.get(config) as Boolean
		}
	}

	override fun getPanelTitle(): String {
		return "UsefulConfiguration"
	}

	override fun onPanelHidden() {
		this.config.save()
	}
}
