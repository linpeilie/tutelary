package com.tutelary.remoting.netty.utils;

import com.tutelary.common.utils.NetUtils;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public class ChannelUtils {

    public static String getChannelIP(Channel channel) {
        InetSocketAddress address = ((InetSocketAddress) channel.remoteAddress());
        return NetUtils.toAddressString(address);
    }

}
