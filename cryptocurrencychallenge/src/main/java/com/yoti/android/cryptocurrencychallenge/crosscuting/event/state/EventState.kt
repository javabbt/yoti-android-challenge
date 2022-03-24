package com.yoti.android.cryptocurrencychallenge.crosscuting.event.state
import android.content.Context
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.appsolute.addictives.crosscutting.event.Event
import kotlinx.coroutines.flow.MutableSharedFlow

enum class State {
    Success,
    InProgress,
    Failure,
    NotConnected
}

class EventState(var state: State, val message: Any? = null) : Event<State>(state) {

    companion object {

        fun LiveData<EventState>.observeEventState(
            lifecycleOwner: LifecycleOwner,
            block: (State, Any?) -> Unit,
        ) {
            this.observe(lifecycleOwner) {
                if (!it.hasBeenHandled) {
                    block(it.peekContent(), it.message)
                    it.hasBeenHandled = true
                }
            }
        }

        @MainThread
        fun Context.showMessage(
            message: Any?,
        ) {
            when (message) {
                null -> return
                is String -> {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
                is Int -> {
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                }
            }
        }

        suspend infix fun MutableLiveData<EventState>.updateState(state: State) {
            this.postValue(EventState(state))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is EventState) return false
        if (other.peekContent() != this.peekContent()) return false
        if (other.hasBeenHandled != this.hasBeenHandled) return false
        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        var result = peekContent().hashCode()
        result *= 31 * hasBeenHandled.hashCode()
        result *= 31 * (message?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "com.yoti.android.cryptocurrencychallenge.crosscuting.event.state.EventState(message=$message)"
    }


}

