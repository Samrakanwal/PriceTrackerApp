package com.example.stocktracker.data.websocket

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.*

class PriceWebSocketClient {

    private val client = OkHttpClient()
    private var socket: WebSocket? = null

    private val _connectionState = MutableStateFlow(false)
    val connectionState: MutableStateFlow<Boolean> = _connectionState

    fun connect(): Flow<String> = callbackFlow {
        val request = Request.Builder()
            .url("wss://ws.postman-echo.com/raw")
            .build()

        socket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                _connectionState.value = true
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                trySend(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                _connectionState.value = false
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                _connectionState.value = false
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                _connectionState.value = false
                close(t)
            }
        })

        awaitClose {
            socket?.close(1000, "closed")
            _connectionState.value = false
        }
    }

    fun send(message: String) {
        socket?.send(message)
    }

    fun disconnect() {
        socket?.close(1000, "manual")
        _connectionState.value = false
    }
}