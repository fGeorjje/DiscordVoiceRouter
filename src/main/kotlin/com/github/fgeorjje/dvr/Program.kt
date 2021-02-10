/*
 *     Discord Voice Router
 *     Copyright (C) 2021  Paul S.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.fgeorjje.dvr

import com.github.fgeorjje.dvr.command.CommandRouter
import com.github.fgeorjje.dvr.command.ConsoleHandler
import com.github.fgeorjje.dvr.command.ShutdownCommand
import com.github.fgeorjje.dvr.command.audioroute.AudioRouterAddRouteCommand
import com.github.fgeorjje.dvr.command.audioroute.AudioRouterRemoveRouteCommand
import com.github.fgeorjje.dvr.command.audioroute.AudioRouterRestartCommand
import com.github.fgeorjje.dvr.command.voice.VoiceChannelJoinCommand
import com.github.fgeorjje.dvr.command.voice.VoiceChannelLeaveCommand
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.util.*
import javax.security.auth.login.LoginException
import kotlin.concurrent.thread
import kotlin.system.exitProcess

object Program {
    @Throws(LoginException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) {
            System.err.println("Unable to start without token!")
            exitProcess(1)
        }

        val token = args[0]
        val audioRouter = AudioRouter()

        val jda = JDABuilder.createLight(token, EnumSet.of(GatewayIntent.GUILD_VOICE_STATES))
            .setActivity(Activity.watching("github.com/fGeorjje/Discord-Audio-Router"))
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .addEventListeners(ShutdownListener(audioRouter))
            .enableCache(CacheFlag.VOICE_STATE)
            .setMemberCachePolicy(MemberCachePolicy.VOICE)
            .build()
            .awaitReady()

        val consoleHandler = ConsoleHandler(
            jda, CommandRouter(
                mapOf(
                    Pair("join", VoiceChannelJoinCommand(jda, audioRouter)),
                    Pair("leave", VoiceChannelLeaveCommand(jda)),
                    Pair("route", AudioRouterAddRouteCommand(jda, audioRouter)),
                    Pair("unroute", AudioRouterRemoveRouteCommand(jda, audioRouter)),
                    Pair("restart", AudioRouterRestartCommand(audioRouter)),
                    Pair("shutdown", ShutdownCommand(jda))
                )
            )
        )

        thread(isDaemon = true) { consoleHandler.loopRead() }
    }
}