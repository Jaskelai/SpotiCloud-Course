package com.github.kornilovmikhail.spoticloud.navigation.cicerone

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.Exit
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.ForwardWithResult
import com.github.kornilovmikhail.spoticloud.navigation.cicerone.command.SingleFragmentForwardCommand
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.*
import java.util.LinkedList

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

    private lateinit var localStackCopy: LinkedList<String?>

    override fun applyCommand(command: Command?) {
        when (command) {
            is Forward -> activityForward(command)
            is ForwardWithResult -> activityForwardWithResult(command)
            is Replace -> activityReplace(command)
            is BackTo -> backTo(command)
            is Back -> fragmentBack()
            is SingleFragmentForwardCommand -> singleFragmentForwardCommand(command)
            is Exit -> exit()
        }
    }

    override fun applyCommands(commands: Array<Command>) {
        fragmentManager?.executePendingTransactions()
        copyStackToLocal()
        for (command in commands) {
            applyCommand(command)
        }
    }

    private fun copyStackToLocal() {
        localStackCopy = LinkedList()

        val stackSize = fragmentManager?.backStackEntryCount ?: 0
        for (i in 0 until stackSize) {
            localStackCopy.add(fragmentManager?.getBackStackEntryAt(i)?.name)
        }
    }

    override fun fragmentForward(command: Forward) {
        val screen = command.screen as SupportAppScreen
        val fragment = createFragment(screen)

        val fragmentTransaction = fragmentManager?.beginTransaction()

        setupFragmentTransaction(
            command,
            fragmentManager?.findFragmentById(containerId),
            fragment,
            fragmentTransaction
        )

        fragmentTransaction
            ?.replace(containerId, fragment)
            ?.addToBackStack(screen.screenKey)
            ?.commit()
        localStackCopy.add(screen.screenKey)
    }

    override fun fragmentBack() {
        if (localStackCopy.size > 0) {
            fragmentManager?.popBackStack()
            localStackCopy.removeLast()
        } else {
            activityBack()
        }
    }

    override fun fragmentReplace(command: Replace) {
        val screen = command.screen as SupportAppScreen
        val fragment = createFragment(screen)
        if (localStackCopy.size > 0) {
            fragmentReplaceExisting(command, screen, fragment)
        } else {
            fragmentReplaceEmpty(command, screen, fragment)
        }
    }

    private fun fragmentReplaceExisting(command: Replace, screen: SupportAppScreen, fragment: Fragment) {
        fragmentManager?.popBackStack()
        localStackCopy.removeLast()
        val fragmentTransaction = fragmentManager?.beginTransaction()
        setupFragmentTransaction(
            command,
            fragmentManager?.findFragmentById(containerId),
            fragment,
            fragmentTransaction
        )
        fragmentTransaction
            ?.replace(containerId, fragment, screen.screenKey)
            ?.addToBackStack(screen.screenKey)
            ?.commit()
        localStackCopy.add(screen.screenKey)
    }

    private fun fragmentReplaceEmpty(command: Replace, screen: SupportAppScreen, fragment: Fragment) {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        setupFragmentTransaction(
            command,
            fragmentManager?.findFragmentById(containerId),
            fragment,
            fragmentTransaction
        )
        fragmentTransaction
            ?.replace(containerId, fragment, screen.screenKey)
            ?.commit()
    }

    override fun backTo(command: BackTo) {
        if (command.screen == null) {
            backToRoot()
        } else {
            val key = command.screen.screenKey
            val index = localStackCopy.indexOf(key)
            val size = localStackCopy.size

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy.removeLast()
                }
                fragmentManager?.popBackStack(key, 0)
            } else {
                backToUnexisting(command.screen as SupportAppScreen)
            }
        }
    }

    private fun backToRoot() {
        fragmentManager?.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStackCopy.clear()
    }

    private fun singleFragmentForwardCommand(command: SingleFragmentForwardCommand) {
        val screen = command.getScreen() as SupportAppScreen
        var fragment = fragmentManager?.findFragmentByTag(screen.screenKey)
        if (fragment == null) {
            fragment = createFragment(screen)
        }
        val fragmentTransaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        fragment?.let {
            fragmentTransaction
                ?.replace(containerId, fragment, screen.screenKey)
                ?.addToBackStack(screen.screenKey)
                ?.commit()
            localStackCopy.add(screen.screenKey)
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

    private fun exit() {
        backToRoot()
    }

    companion object {
        private var fragment: Fragment? = null

        fun setFragment(fragment: Fragment) {
            this.fragment = fragment
        }
    }
}
