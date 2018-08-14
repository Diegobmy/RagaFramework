package com.example.ragabuza.baseragaapp.base.dialog

import android.content.Context
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.Message
import com.example.ragabuza.baseragaapp.base.NoPresenterActivity


abstract class DialogModel(var myBuilder: Builder){
    companion object {
        fun error(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel{
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_error_dialog
            builder.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun info(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel{
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_info_dialog
            builder.closeButton = false
            builder.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun success(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel{
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_success_dialog
            builder.closeButton = false
            builder.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun simple(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel{
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
        fun progress(actions: (ProgressDialogModel.Builder.(ProgressDialogModel) -> Unit)? = null): ProgressDialogModel{
            val builder = ProgressDialogModel.Builder()
            val model = ProgressDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
        fun <T> list(actions: (ListDialogModel.Builder<T>.(ListDialogModel<T>) -> Unit)? = null): ListDialogModel<T>{
            val builder = ListDialogModel.Builder<T>()
            val model = ListDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
        fun <T> combo(actions: (ComboDialogModel.Builder<T>.(ComboDialogModel<T>) -> Unit)? = null): ComboDialogModel<T>{
            val builder = ComboDialogModel.Builder<T>()
            val model = ComboDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
    }

    open class Builder{
        var title: Message? = null
        var closeButton: Boolean = true
        var dismissOnClickOutside: Boolean = true
        var onDismiss: (() -> Unit)? = null
    }

    var dialog: RagaDialog? = null
    fun show(activity: NoPresenterActivity) {
        if (dialog == null)
            dialog = RagaDialog(activity, this)
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }

    fun refreshDialog(){
        dialog?.setInfo()
    }

    abstract fun RagaDialog.setInfo()

}
