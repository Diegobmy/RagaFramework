package com.example.ragabuza.baseragaapp.base.dialog

import android.content.Context
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.Message


abstract class DialogModel(var myBuilder: Any){
    companion object {
        fun error(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel{
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_error_dialog
            builder.listeners.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun info(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel{
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_info_dialog
            builder.closeButton = false
            builder.listeners.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun success(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel{
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_success_dialog
            builder.closeButton = false
            builder.listeners.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun new(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel{
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
        fun list(actions: (ListDialogModel.Builder.(ListDialogModel) -> Unit)? = null): ListDialogModel{
            val builder = ListDialogModel.Builder()
            val model = ListDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
        fun combo(actions: (ComboDialogModel.Builder.(ComboDialogModel) -> Unit)? = null): ComboDialogModel{
            val builder = ComboDialogModel.Builder()
            val model = ComboDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
    }

    var dialog: RagaDialog? = null
    fun show(context: Context) {
        if (dialog == null)
            dialog = RagaDialog(context, myBuilder)
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }

}
