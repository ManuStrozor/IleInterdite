/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iut.ileinterdite;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;

/**
 *
 * @author turbetde
 */
public interface Observateur extends Flow.Subscriber<Message> {
    public void traiterMessage(Message msg);
    
    @Override
    public default void onSubscribe(Flow.Subscription subscription) {
    }

    @Override
    public default void onNext(Message item) {
        traiterMessage(item);
    }

    @Override
    public default void onError(Throwable throwable) {
    }

    @Override
    public default void onComplete() {
    }
}
