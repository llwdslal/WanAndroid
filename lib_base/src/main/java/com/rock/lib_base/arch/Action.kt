package com.rock.lib_base.arch

interface IAction //UI 层向 State 发送的事件，默认为 UI+Data 事件
interface DataAction //单独的 Data 事件，同时实现 IAction 和 DataAction
interface UiAction

//事件消费者， ViewModel 和 ComposeVmState 实现此接口来处理事件
interface ActionConsumer<A:IAction>{
    fun onAction(action:A)
}