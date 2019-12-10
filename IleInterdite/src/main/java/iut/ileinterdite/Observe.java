/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iut.ileinterdite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;

/**
 *
 * @author turbetde
 */
public class Observe implements Subscription {
    private static class ObserveManager implements Flow.Publisher<Message> {
        public final ExecutorService executor = Executors.newFixedThreadPool(4);
    
        private List<Observe> observateurs = Collections.synchronizedList(new ArrayList<Observe>());
    
        @Override
        public void subscribe(Flow.Subscriber<? super Message> subscriber) {
        }
        
        public void addSubscriber(Observateur o, Observe observe) {
            observateurs.add(observe);
            o.onSubscribe(observe);
        }
        
        public void removeSubscriber(Observateur o, Observe observe) {
            synchronized (observateurs) {
                observateurs.remove(observe);
            }
        }
    }

    @Override
    public void request(long n) {
    }

    @Override
    public void cancel() {
    }
    
    private static final ObserveManager manager = new ObserveManager();
    private Observateur observateur;
    
    public void setObservateur(Observateur o) {
        if (o != null) {
            manager.addSubscriber(o, this);
        } else {
            manager.removeSubscriber(o, this);
        }
        
        this.observateur = o;
    }
            
    public void notifierObservateur(Message m) {
        if (observateur != null) {
            manager.executor.execute(() -> {
                observateur.onNext(m);                
            });
        }
    }
}
