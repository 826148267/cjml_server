package edu.jnu.domain;

public class KeyPair {

    private PublicKey publickey;
    private PrivateKey privatekey;

    public KeyPair(PublicKey publickey, PrivateKey privatekey){
        this.publickey=publickey;
        this.privatekey=privatekey;
    }

    public PublicKey getPublickey() {
        return publickey;
    }

    public PrivateKey getPrivatekey() {
        return privatekey;
    }
}
