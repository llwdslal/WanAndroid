package com.rock.lib_base.ktx

import android.text.Html

fun String.fromHtml():String = Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()