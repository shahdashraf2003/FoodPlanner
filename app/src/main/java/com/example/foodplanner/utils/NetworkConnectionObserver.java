package com.example.foodplanner.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

public class NetworkConnectionObserver {

    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    public interface NetworkListener {
        void onNetworkLost();
        void onNetworkAvailable();
    }

    public NetworkConnectionObserver(Context context, NetworkListener listener) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLost(Network network) {
                listener.onNetworkLost();
            }

            @Override
            public void onAvailable(Network network) {
                listener.onNetworkAvailable();
            }
        };
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    public void unregister() {
        if (connectivityManager != null && networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }
}
