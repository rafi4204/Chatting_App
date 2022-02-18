package com.example.videocallingapp.model

sealed class Response{
    object Success : Response()
    object Failed : Response()
    object Empty : Response()
}

