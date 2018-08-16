package com.example.ragabuza.baseragaapp.base.dialog

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.ragabuza.baseragaapp.R
import com.example.ragabuza.baseragaapp.base.*
import com.example.ragabuza.baseragaapp.base.dialog.subTypes.*


abstract class DialogModel(var myBuilder: Builder){
    companion object {
        fun error(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel {
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_error_dialog
            builder.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun info(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel {
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_info_dialog
            builder.closeButton = false
            builder.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun success(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel {
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            builder.icon = R.drawable.ic_success_dialog
            builder.closeButton = false
            builder.positive = Pair(Message("Ok"), {model.dismiss()})
            actions?.invoke(builder, model)
            return model
        }
        fun simple(actions: (SimpleDialogModel.Builder.(SimpleDialogModel) -> Unit)? = null): SimpleDialogModel {
            val builder = SimpleDialogModel.Builder()
            val model = SimpleDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
        fun progress(actions: (ProgressDialogModel.Builder.(ProgressDialogModel) -> Unit)? = null): ProgressDialogModel {
            val builder = ProgressDialogModel.Builder()
            val model = ProgressDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
        fun <T> list(actions: (ListDialogModel.Builder<T>.(ListDialogModel<T>) -> Unit)? = null): ListDialogModel<T> {
            val builder = ListDialogModel.Builder<T>()
            val model = ListDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
        fun <T> combo(actions: (ComboDialogModel.Builder<T>.(ComboDialogModel<T>) -> Unit)? = null): ComboDialogModel<T> {
            val builder = ComboDialogModel.Builder<T>()
            val model = ComboDialogModel(builder)
            actions?.invoke(builder, model)
            return model
        }
        fun <T> custom(actions: (CustomDialog.Builder<T>.(CustomDialog<T>) -> Unit)? = null): CustomDialog<T> {
            val builder = CustomDialog.Builder<T>()
            val model = CustomDialog(builder)
            actions?.invoke(builder, model)
            return model
        }
    }

    open class Builder{
        var title: Message? = null
        var closeButton: Boolean = true
        var dismissOnClickOutside: Boolean = true
        var onDismiss: UnitFunction? = null
        var closeButtonColor: Int? = null
        var titleColor: Int? = null
        var backgroundColor: Int? = null
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
        dialog?.setBasicInfos()
    }

    private fun RagaDialog.setBasicInfos(){
        if (closeButtonView != null) {
            if (closeButtonView!!.setVisible(myBuilder.closeButton.isNotNull())) {
                closeButtonView!!.setOnClickListener { dismiss() }
            }
            myBuilder.closeButtonColor.doIfNotNull {
                closeButtonView!!.setColorFilter(context.getColorCompat(it))
            }
        }

        if (myBuilder.title.isNotNull()) {
            titleView?.text = myBuilder.title?.getMessage(context)
        }
        myBuilder.titleColor.doIfNotNull {
            titleView?.setTextColor(context.getColorCompat(it))
        }

        myBuilder.backgroundColor.doIfNotNull {
            backgroungView?.setBackgroundColor(context.getColorCompat(it))
        }
    }

    var backgroungView: View? = null
    var closeButtonView: ImageButton? = null
    var titleView: TextView? = null

    abstract fun RagaDialog.setInfo()

}
