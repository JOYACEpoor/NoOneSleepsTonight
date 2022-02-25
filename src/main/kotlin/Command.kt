package nya.xfy

import net.mamoe.mirai.console.command.*
import java.util.*

object Command : SimpleCommand(
    NoOneSleepsTonight, "nost",
    description = "NoOneSleepTonightManager"
) {
    @Handler // 标记这是指令处理器  // 函数名随意
    suspend fun MemberCommandSender.handle(message: String) { // 这两个参数会被作为指令参数要求
        when(message.lowercase(Locale.getDefault())){
            "on" -> Config.sleepSwitch =true.also { subject.sendMessage("on") }
            "off" -> Config.sleepSwitch =false.also { subject.sendMessage("off") }
            else -> subject.sendMessage("参数错误")
        }
    }
}