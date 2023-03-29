package com.rock.lib_compose.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.lang.IllegalArgumentException
import java.lang.StringBuilder


interface OutRoutes
abstract class Screen<T:OutRoutes>(private val path:String){
    //根路由，相当于分组标签
    abstract val root:String


    open val arguments:List<NamedNavArgument> = emptyList()

    val argumentsWithRequestCode:List<NamedNavArgument> = mutableListOf<NamedNavArgument>().apply {
        addAll(arguments)
        add(navArgument(name = NavRequestCodeKey) {
            type = NavType.StringType
            nullable = true
        })
    }


    open val deepLinks:List<NavDeepLink> = emptyList()

    lateinit var outRoutes:T

    //必填参数名
    private val requiredArgs:MutableList<String> = mutableListOf()
    //可选参数名+默认值 map
    private val optionalArgs:MutableMap<String,Any?> = mutableMapOf(NavRequestCodeKey to null)

    private val routePath by lazy {
        if (root.isEmpty()) path else "$root/${path}"
    }

    /**
     * 自动生成的 route ，配置 DSL 时使用
     * route = rootRoute/path/必填参数?可选参数
     */
    val route:String by lazy {
        val sb = StringBuilder(routePath)
        //解析参数时将 必选/可选参数 分别保存起来
        for (arg in argumentsWithRequestCode){
            if (arg.argument.isNullable || arg.argument.isDefaultValuePresent){
                //可选参数
                sb.append("?${arg.name}={${arg.name}}")
                optionalArgs[arg.name] = arg.argument.defaultValue
            }else{
                sb.append("/{${arg.name}}")
                requiredArgs.add(arg.name)
            }
        }
        sb.toString()
    }

    /**
     * 通用方法
     * 根据传入的 map 生成 navigate 方法所需 route
     * map 中必须包括所有的必填参数
     * @param args Map<String, Any> key:参数名,value:参数值
     * @return String 调用 navigate 方法所需 route
     */
    fun createRoute(args:Map<String,Any> = emptyMap()):String{
        val sb = StringBuilder(routePath)
        if (args.isEmpty() && requiredArgs.isNotEmpty()){
            throw IllegalArgumentException("param [args:Map<String,Any>] can't be empty")
        }else{
            for (requiredArg in requiredArgs){
                if (!args.containsKey(requiredArg)){
                    throw IllegalArgumentException("required argument $requiredArg can't find in param [args:Map<String,Any>]")
                }
                sb.append("/${args[requiredArg]}")
            }
            for (optionalArg in optionalArgs){
                if (args.containsKey(optionalArg.key)){ //参数中有可选参数
                    sb.append("?${optionalArg.key}=${args[optionalArg.key]}")
                }else if (optionalArg.value != null){ // 可选参数有默认值
                    sb.append("?${optionalArg.key}=${optionalArg.value}")
                }
            }
        }
        return sb.toString()
    }

    fun createForResultRoute(requestCode:String,args:Map<String,Any> = emptyMap()):String{
        val withRequestCode = mutableMapOf<String,Any>(NavRequestCodeKey to requestCode)
        withRequestCode.putAll(args)
        return createRoute(withRequestCode)
    }
}