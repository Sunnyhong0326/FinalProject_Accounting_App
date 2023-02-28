package com.codelab.basiclayouts.core.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.ws.GraphQLWsProtocol
import com.codelab.basiclayouts.core.BASE_URL
import com.codelab.basiclayouts.core.WEB_SOCKET_URL

private var instance: ApolloClient? = null

fun apolloClient(): ApolloClient {
    if (instance != null) {
        return instance!!
    }

    instance = ApolloClient.Builder()
        .serverUrl(BASE_URL)
        .webSocketServerUrl(WEB_SOCKET_URL)
        .wsProtocol(GraphQLWsProtocol.Factory())
        .build()

    return instance!!
}
