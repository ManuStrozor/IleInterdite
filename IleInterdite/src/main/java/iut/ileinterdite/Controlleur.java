/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iut.ileinterdite;

/**
 *
 * @author turbetde
 */
public class Controlleur implements Observateur {

    private IHM ihm;
    private Model model;
    
    public Controlleur() {
        ihm = new IHM();
        ihm.setObservateur(this);
        
        model = new Model();
        model.setObservateur(this);
    }
    
    @Override
    public void traiterMessage(Message msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
