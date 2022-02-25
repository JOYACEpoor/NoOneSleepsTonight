package nya.xfy

import kotlinx.coroutines.*
import net.mamoe.mirai.console.command.*
import net.mamoe.mirai.console.plugin.jvm.*
import net.mamoe.mirai.event.*
import net.mamoe.mirai.message.data.*
import net.mamoe.mirai.utils.*
import nya.xfy.Config.sleepSwitch
import java.util.*

object NoOneSleepsTonight : KotlinPlugin(
    JvmPluginDescription(
        id = "nya.xfy.NoOneSleepsTonight",
        version = "1.0.0",
    )
) {
    override fun onEnable() {
        runBlocking {
            launch { logger.info { "Plugin loaded" } }
            launch { Config.reload() }
            launch { CommandManager.registerCommand(Command) }
        }
        this.globalEventChannel().subscribeGroupMessages(priority = EventPriority.HIGHEST) {
            finding(Regex("""[!！][sS][lL][eE][eE][pP]""")) {
                when (sleepSwitch) {
                    true -> {
                        val calendar = Calendar.getInstance()
                        when (calendar[Calendar.HOUR_OF_DAY] < 6) {
                            true -> calendar.set(Calendar.HOUR_OF_DAY, 6)
                            else -> calendar.apply {
                                this.add(Calendar.DATE, 1)
                                this.set(Calendar.HOUR_OF_DAY, 6)
                            }
                        }
                        sender.takeIf { group.botPermission > sender.permission }?.mute(((calendar.timeInMillis - Date().time) / 1000).toInt())
                    }
                    else -> {
                        delay(10000)
                        sender.takeIf { group.botPermission > sender.permission }?.mute(1)
                        subject.sendMessage(sender.at() + "你不准睡了")
                    }
                }
            }
        }
    }
}