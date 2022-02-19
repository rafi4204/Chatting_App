package com.example.videocallingapp.model

sealed class Response{
    object Success : Response()
    data class Failed(var errorMsg: String) : Response()
    object Empty : Response()
}

