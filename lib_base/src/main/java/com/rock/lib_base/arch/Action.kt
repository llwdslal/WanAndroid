package com.rock.lib_base.arch

interface IAction
interface DataAction
interface UiAction

interface ActionConsumer<A:IAction>{
    fun onAction(action:A)
}