package cn.newness.imip.framework.websocket.core.sender.local;

import cn.newness.imip.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import cn.newness.imip.framework.websocket.core.sender.WebSocketMessageSender;
import cn.newness.imip.framework.websocket.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 *
 * 注意：仅仅适合单机场景！！！
 *
 * @author 新奇源码
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
