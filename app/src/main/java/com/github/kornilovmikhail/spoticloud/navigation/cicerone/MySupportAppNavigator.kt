package com.github.kornilovmikhail.spoticloud.navigation.cicerone

import androidx.fragment.app.FragmentActivity
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.ForwardWithResult
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace
import ru.terrakok.cicerone.commands.Forward
import android.content.Intent
import ru.terrakok.cicerone.android.support.SupportAppScreen
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MySupportAppNavigator(
    private val activity: FragmentActivity?,
    private val fragmentManager: FragmentManager?,
    private val containerId: Int
) : SupportAppNavigator(activity, containerId) {

    constructor(activity: FragmentActivity?, containerId: Int) : this(
        activity,
        activity?.supportFragmentManager,
        containerId
    )

    override fun applyCommand(command: Command?) {
        when (command) {
            is Forward -> activityForward(command)
            is ForwardWithResult -> activityForwardWithResult(command)
            is Replace -> activityReplace(command)
            is BackTo -> backTo(command)
            is Back -> fragmentBack()
        }
    }

    private fun activityForwardWithResult(command: ForwardWithResult) {
        val screen = command.getScreen() as SupportAppScreen
        val requestCode = command.getRequestCode()
        val activityIntent = screen.getActivityIntent(activity)
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivityWithResult(screen, activityIntent, options, requestCode)
        }
    }

    private fun checkAndStartActivityWithResult(
        screen: SupportAppScreen,
        activityIntent: Intent,
        options: Bundle?,
        requestCode: Int
    ) {
        if (activityIntent.resolveActivity(activity?.packageManager) != null) {
            fragment?.startActivityForResult(activityIntent, requestCode, options)
        } else {
            unexistingActivity(screen, activityIntent)
        }
    }

    companion object {
        private var fragment: Fragment? = null

        fun setFragment(fragment: Fragment) {
            this.fragment = fragment
        }
    }
}
